package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/index")
	public String index() {
		
		return "index";
	}
	
	//实现通用页面跳转  url:'/page/item-add
	/**
	 * url:/page/item-add
	 * 页面:item-add
	 * 猜想:如果能够获取url中的请求路径,则可以
	 * 直接跳转页面.代码简洁
	 * RestFul风格
	 * 1.参数应该拼接到url中  使用"/"分割
	 * 2.服务端接收数据时,应该采用 {}动态的获取数据
	 * 3.接收数据时采用注解@PathVariable进行
	 * 数据的转化		
	 * @return
	 */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		
		return moduleName;
	}
	
	
	
	
	
	
	
	
	
}
