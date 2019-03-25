package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import com.jt.web.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	//接收用户请求,跳转商品列表页面 /items/562379.html
	@RequestMapping("/{itemId}")
	public String findItemById
				(@PathVariable Long itemId,Model model) {
		Item item = itemService.findItemById(itemId);
		ItemDesc itemDesc = itemService.findItemDescById(itemId);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc",itemDesc);
		return "item"; //返回页面数据信息
	}
	
	
	
	
	
	
	
	
	
	
}
