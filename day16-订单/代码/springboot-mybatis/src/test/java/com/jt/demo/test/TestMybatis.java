package com.jt.demo.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.demo.mapper.UserMapper;
import com.jt.demo.pojo.User;

import redis.clients.jedis.JedisCluster;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMybatis {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void testFindAll() {
		List<User> userList = 
				userMapper.findAll();
		System.out.println(userList);
	}
	
	//测试save方法
	@Test
	public void saveUser() {
		User user = new User();
		user.setName("诸葛亮")
			.setAge(19)
			.setSex("男");
		userMapper.insert(user);
		System.out.println("用户入库成功!!!");
	}
	
	//测试修改方法
	/**
	 * entity:数据修改的值之后封装为对象
	 * updateWrapper 条件构造器
	 * 				  修改where条件的构造方式
	 * 
	 * update user set age = 25 where name="诸葛亮";
	 * 要求:将诸葛亮年龄改为25岁
	 */
	@Test
	public void updateUser() {
		User user = new User();
		user.setAge(25);
		UpdateWrapper<User> updateWrapper = 
				new UpdateWrapper<>();
		updateWrapper.eq("name","诸葛亮");
		userMapper.update(user, updateWrapper);
	}
	
	//要求:将年龄超过8000岁名字改为老不死的!!
	//update user set name="XXXX" where age > 8000;
	//大于 gt  小于lt 
	@Test
	public void updateUser2() {
		User user = new User();
		user.setName("老不死的!!!");
		UpdateWrapper<User> updateWrapper = 
				new UpdateWrapper<>();
		updateWrapper.gt("age",8000);
		userMapper.update(user, updateWrapper);
		System.out.println("修改完成");
	}
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Test
	public void testSet() {
		jedisCluster.set("1811", "springBoot整合成功!!!");
		System.out.println(jedisCluster.get("1811"));
	}
	
	
	
	
	
}
