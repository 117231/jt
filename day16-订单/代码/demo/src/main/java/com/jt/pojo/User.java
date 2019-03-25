package com.jt.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component	//将对象交给spring容器管理
@ConfigurationProperties(prefix="user")
//必须添加set/get方法
public class User {
	//@Value("${user.id}")
	private Integer id;
	//@Value("${user.username}")
	private String username;
	//@Value("${user.age}")
	private Integer age;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", age=" + age + "]";
	}
	
}
