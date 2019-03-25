package com.jt.sso.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;
import com.jt.sso.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean findCheckUser(String param, Integer type) {
		String column = null;
		switch (type) {
		case 1:
			column = "username";
			break;
		case 2:
			column = "phone";
			break;
		case 3:
			column = "email";
			break;
		}
		QueryWrapper<User> queryWraapper = new QueryWrapper<>();
		queryWraapper.eq(column, param);
		int count = userMapper.selectCount(queryWraapper);
		//回数据true用户已存在，false用户不存在，可以
		return count==0?false:true ;
	}

	//SpringBoot默认开启事务开关
	@Transactional	
	@Override
	public void saveUser(User user) {
		//补齐数据
		user.setEmail(user.getPhone())	//暂时写死
			.setCreated(new Date())
			.setUpdated(user.getCreated());
		userMapper.insert(user);
	}
	
	/**
	 * 1.先查询数据库,判断用户是否正确
	 */
	@Override
	public String findUserByUP(User user) {
		String token = null;
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username",user.getUsername())
					.eq("password",user.getPassword());
		User userDB = userMapper.selectOne(queryWrapper);
		
		//判断用户数据是否存在
		if(userDB != null) {
			String tokenString = "JT_TICKET_" + System.currentTimeMillis() + user.getUsername();
			token = DigestUtils.md5DigestAsHex(tokenString.getBytes());
			//为了安全将密码擦除
			userDB.setPassword("你猜猜!!!");
			String userJSON = ObjectMapperUtil.toJSON(userDB);
			//将用户信息保存到redis中
			jedisCluster.set(token, userJSON);
		}
		return token;
	}
}
