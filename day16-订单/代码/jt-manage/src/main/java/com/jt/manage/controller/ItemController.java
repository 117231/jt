package com.jt.manage.controller;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;
import com.jt.manage.vo.EasyUITable;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	//http://localhost:8091/item/query?page=1&rows=30
	@RequestMapping("/query")
	@ResponseBody	//返回数据转化为Json
	public EasyUITable findItemList(int page,int rows) {
		
		return itemService.findItemListByPage(page,rows);
	}
	
	/**
	 * 1.Object对象类型:返回值编辑就是UTF-8 MVC支持
	 * 2.如果返回值类型为String类型,
	 * 则MVC采用ISO-8859-1格式转码.
	 * @param itemCatId
	 * @return
	 * public abstract class AbstractJackson2HttpMessageConverter extends AbstractHttpMessageConverter<Object>
		implements GenericHttpMessageConverter<Object> {
		public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
		}
	
		public class StringHttpMessageConverter extends AbstractHttpMessageConverter<String> {
		public static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");
	 */
	//根据商品分类Id查询名称
	@RequestMapping(value="/cat/queryItemCatNameById",
					produces="text/html;charset=utf-8")
	@ResponseBody
	public String findItemCatNameById(Long itemCatId) {
		
		return itemService.findItemCatNameById(itemCatId);
	}
	
	//实现商品新增
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc) {
		try {
			itemService.saveItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户新增失败,联系管理员");
	}
	
	/**
	 * String[] argsId = ids.split(",");
		for (String string : argsId) {
			Long longId = Long.parseLong(string);
		}
	 * @param ids
	 * @return
	 */
	//商品下架处理  传参ids=100,200,300
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instock(Long[] ids) {
		try {
			int status = 2; //表示商品下架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"下架失败");
	}
	
	
	//商品上架 status=1
	//http://localhost:8091/item/reshelf
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelf(Long[] ids) {
		try {
			int status = 1; //表示商品下架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"上架失败");
	}
	
	//1.请求是什么?  2.需要什么返回值?
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc) {
		try {
			itemService.updateItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"更新失败");
	}
	
	//商品删除
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult delItem(Long[] ids) {
		try {
			itemService.deleteItem(ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"删除失败");
	}
	
	//实现商品详情查询
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDescById(@PathVariable Long itemId) {
		try {
			ItemDesc itemDesc = 
					itemService.findItemDescById(itemId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品详情查询失败");
	}
}
