package com.jt.order.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	//有线程安全性问题吗? 没有
	private static ObjectMapper mapper = new ObjectMapper();
	public static String toJSON(Object object) {
		String json = null;
		try {
			json = mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return json;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T toObject(String json,Class<T> targetClass) {
		T t = null;	//定义泛型对象
		try {
			t = mapper.readValue(json,targetClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return t;		
	}
}
