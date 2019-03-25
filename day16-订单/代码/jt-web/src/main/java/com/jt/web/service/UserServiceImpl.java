package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.jt.common.service.HttpClientService;
import com.jt.common.util.ObjectMapperUtil;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private HttpClientService httpClient;

	@Override
	public void saveUser(User user) {
		String url = "http://sso.jt.com/user/register";
		Map<String,String> params = new HashMap<String, String>();
		params.put("username", user.getUsername());
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		params.put("password",md5Pass);
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		httpClient.doPost(url, params);
	}

	/**
	 * 该方法表示jt-web从jt-sso中获取token数据,
	 * 同时判断用户信息是否正确
	 */
	@Override
	public String findUserByUP(User user) {
		String token = null;
		String url = "http://sso.jt.com/user/login";
		Map<String,String> params = new HashMap<>();
		params.put("username", user.getUsername());
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		params.put("password", md5Pass);
		//jt-sso返回sysResultJSON数据
		String result = httpClient.doPost(url, params);
		SysResult sysResult = 
				ObjectMapperUtil.toObject(result, SysResult.class);
		if(sysResult.getStatus() == 200) {
			//表示后台正确
			token = (String) sysResult.getData();
		}
		
		return token;
	}
}
