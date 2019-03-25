package com.jt.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.manage.mapper.UserMapper;
import com.jt.manage.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	//spring容器创建代理对象
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> findList() {
		
		return userMapper.findList();
	}

}
