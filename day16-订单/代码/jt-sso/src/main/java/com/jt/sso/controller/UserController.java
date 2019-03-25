package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;
import com.jt.sso.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	//实现用户信息校验 /user/check/admin181234/1?
	@RequestMapping("/check/{param}/{type}")
	public JSONPObject findCheckUser(
			@PathVariable String param,
			@PathVariable Integer type,
			String callback) {
		boolean flag = userService.findCheckUser(param,type);
		SysResult result = SysResult.oK(flag);
		return new JSONPObject(callback, result);
	}
	
	//实现用户入库操作
	@RequestMapping("/register")
	public SysResult saveUser(User user) {

		userService.saveUser(user);
		return SysResult.oK();
	}
	
	//实现用户登陆操作
	@RequestMapping("/login")
	public SysResult findUserByUP(User user) {
		try {
			String token = userService.findUserByUP(user);
			if(!StringUtils.isEmpty(token)) {
				return SysResult.oK(token);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户登陆失败");
	}
	
	//实现用户信息回显 JSONP
	@RequestMapping("/query/{token}")
	public JSONPObject findUserByToken(@PathVariable String token,String callback) {
		String userJSON = jedisCluster.get(token);
		//判断数据是否存在.
		if(StringUtils.isEmpty(userJSON)) {
			//表示当前用户登录不完整.
			return new JSONPObject(callback,SysResult.build(201,"用户查询有误"));
		}else {
			//必须返回用户JSON数据
			return new JSONPObject(callback,SysResult.oK(userJSON));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
