package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;

//定义类名时,考虑spring容器中是否有该key.如果没有才使用
@RestController
@RequestMapping("/web/item")
public class WebItemController {
	
	@Autowired
	private ItemService itemService;
	
	//http://manage.jt.com/web/item/findItemById/562379
	//定义查询的方法路径
	@RequestMapping("/findItemById/{itemId}")
	public Item findItemById(@PathVariable Long itemId) {
		
		return itemService.findItemById(itemId);
	}
	
	@RequestMapping("/findItemDescById/{itemId}")
	public ItemDesc findItemDescById(@PathVariable Long itemId) {
		
		return itemService.findItemDescById(itemId);
	}
}
