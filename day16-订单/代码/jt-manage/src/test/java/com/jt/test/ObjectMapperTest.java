package com.jt.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.User;

public class ObjectMapperTest {
	
	@Test
	public void objectToJSON() throws IOException {
		User user = new User();
		user.setId(100);
		user.setName("测试");
		user.setAge(10000);
		user.setSex("男");
		ObjectMapper objectMapper = new ObjectMapper();
		String json = 
		objectMapper.writeValueAsString(user);
		System.out.println(json);
		
		//将json串转化为对象
		User user2 =
		objectMapper.readValue(json, User.class);
		//get方法获取属性值. set方法为属性赋值.
		System.out.println(user2.toString());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void listToJSON() throws IOException {
		List<User> userList = new ArrayList<User>();
		User user1 = new User();
		user1.setId(1);
		user1.setName("杯子");
		user1.setAge(19);
		user1.setSex("男");
		userList.add(user1);
		ObjectMapper objectMapper = new ObjectMapper();
		String result = 
				objectMapper.writeValueAsString(userList);
		System.out.println(result);
		//将JSON转化为List集合
		List<User> uList = 
		objectMapper.readValue(result,ArrayList.class);
		System.out.println(uList);
	}
}
