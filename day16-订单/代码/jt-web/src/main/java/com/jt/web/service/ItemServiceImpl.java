package com.jt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.service.HttpClientService;
import com.jt.common.util.ObjectMapperUtil;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private HttpClientService httpClient;
	
	@Override
	public Item findItemById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemById/"+itemId;
		String result = httpClient.doGet(url);
		//System.out.println("输出返回值结果:"+result);
		return ObjectMapperUtil.toObject(result, Item.class);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemDescById/"+itemId;
		String result = httpClient.doGet(url);
		return ObjectMapperUtil.toObject(result, ItemDesc.class);
	}
}
