package com.jt.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
@Accessors(chain=true)
@Data
@TableName("user")	//如果表名与对象名称一致可以省略
public class User {
	//表示定义主键  并且主键自增
	@TableId(type=IdType.AUTO)	
	private Integer id;
	private String name;
	private Integer age;
	private String sex;
}
