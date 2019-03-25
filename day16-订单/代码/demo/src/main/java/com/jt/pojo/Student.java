package com.jt.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data	//get/set/无参构造/toString
@Accessors(chain=true) //链接加载
public class Student {
	private Integer id;
	private String name;
	private String sex;
}
