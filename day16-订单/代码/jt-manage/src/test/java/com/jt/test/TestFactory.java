package com.jt.test;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFactory {
	
	@Test
	public void test01() {
		//获取spring容器
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("/spring/facotry.xml");
		Calendar calendar = 
		(Calendar) context.getBean("calendar3");
		System.out.println("获取当前的格林威治时间:"+calendar.getTime());
	}
	
	
	
	
	
	
	
}
