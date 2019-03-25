package com.jt.sso.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@TableName("tb_user")
public class User {
	@TableId(type=IdType.AUTO)	//主键自增
	private Long id;	
	private String username;
	private String password;
	private String phone;
	private String email;
	private Date created;
	private Date updated;
	 
}
