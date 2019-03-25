package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.service.HttpClientService;
import com.jt.common.util.ObjectMapperUtil;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Order;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private HttpClientService httpClient;

	@Override
	public String saveOrder(Order order) {
		String url = "http://order.jt.com/order/create";
		String orderJSON = ObjectMapperUtil.toJSON(order);
		Map<String,String> params = new HashMap<String, String>();
		params.put("orderJSON", orderJSON);
		String result = httpClient.doPost(url, params);
		SysResult sysResult 
			= ObjectMapperUtil.toObject(result, SysResult.class);
		String orderId = null;
		if(sysResult.getStatus() == 200) {
			
			orderId = (String) sysResult.getData();
		}
		return orderId;
	}
	
	//通过httpClient获取orderJSON数据
	@Override
	public Order findOrderById(String id) {
		String url = "http://order.jt.com/order/query/"+id;
		String orderJSON = httpClient.doGet(url);
		return ObjectMapperUtil.toObject(orderJSON, Order.class);
	}
}
