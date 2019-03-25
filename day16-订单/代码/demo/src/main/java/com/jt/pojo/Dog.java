package com.jt.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
//让Spring容器加载指定的配置文件
@PropertySource(value="classpath:/properties/dog.properties")
@Component
public class Dog {
	@Value("${dog.name}")
	private String name;
	@Value("${dog.type}")
	private String type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
