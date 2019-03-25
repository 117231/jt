package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication	
//标识该项目是SpringBoot项目
//启动时自动加载配置(tomcat服务器)
//主启动类所在的位置就是包扫描的路径
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
