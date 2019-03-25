package com.jt.manage.factory;

import java.util.Calendar;

import org.springframework.beans.factory.FactoryBean;

public class SpringFactory implements FactoryBean<Calendar>{

	@Override
	public Calendar getObject() throws Exception {
		//自己实现获取对象的方法
		return Calendar.getInstance();
	}

	@Override
	public Class<?> getObjectType() {
		//指定class类型
		return Calendar.class;
	}

	@Override
	public boolean isSingleton() {
		//是否为单例
		return true;
	}

}
