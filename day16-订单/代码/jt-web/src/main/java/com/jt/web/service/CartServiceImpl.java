package com.jt.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.service.HttpClientService;
import com.jt.common.util.ObjectMapperUtil;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private HttpClientService httpClient;
	
	@Override
	public List<Cart> findCartByUserId(Long userId) {
		String url = "http://cart.jt.com/cart/query/"+userId;
		String result = httpClient.doGet(url);
		SysResult sysResult = 
		ObjectMapperUtil.toObject(result, SysResult.class);
		return (List<Cart>) sysResult.getData();
	}

	@Override
	public void saveCart(Cart cart) {
		String url = "http://cart.jt.com/cart/save";
		String cartJSON = ObjectMapperUtil.toJSON(cart);
		Map<String,String> params = new HashMap<String, String>();
		params.put("cartJSON", cartJSON);
		httpClient.doPost(url, params);
	}

	@Override
	public void deleteCartById(Long userId, Long itemId) {
		String url = "http://cart.jt.com/cart/delete/"+userId+"/"+itemId;
		httpClient.doGet(url);
	}
	
	
	
	
	
	
	
	
	

}
