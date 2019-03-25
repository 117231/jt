package com.jt.manage.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.common.util.ObjectMapperUtil;
import com.jt.manage.pojo.User;

@RestController
public class JSONPController {

	@RequestMapping("/web/testJSONP")
	public JSONPObject getData(String callback) {
		User user = new User();
		user.setId(10086);
		user.setName("JSONP真简单!!!!");
		return new JSONPObject(callback, user);
	}
}	
	
	//获取json数据   callback(json)
	/*@RequestMapping("/web/testJSONP")
	public String getData(String callback) {
		User user = new User();
		user.setId(10086);
		user.setName("JSONP真简单!!!!");
		String result = callback+"("+ObjectMapperUtil.toJSON(user)+")";
		return result;
	}*/

