package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	///user/login.html,/user/register.html
	@RequestMapping("/{moduleName}")
	public String index(@PathVariable String moduleName) {
	
		return moduleName;
	}
	
	//实现前台的用户新增
	@RequestMapping("/doRegister")
	@ResponseBody	//返回json串
	public SysResult saveUser(User user) {

		userService.saveUser(user);
		return SysResult.oK();
	}
	
	
	/**
	 * cookie.setMaxAge(值);
	 * 值>0  	表示存活时间 单位秒
	 * 值=0  	立即删除cookie
	 * 值=-1  会话结束时删除cookie
	 * cookie.setPath("/"); 
	 *  /表示cookie的权限问题  表示根
	 *  /user/aa/bb  只有位于/user/aa/bb下的页面才能获取cookie信息
	 *  
	 * @param user
	 * @param response
	 * @return
	 */
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult findUserByUP(User user,HttpServletResponse response) {
		try {
			String token = userService.findUserByUP(user);
			if(!StringUtils.isEmpty(token)) {
				//如果token有数据,则保存到cookie中
				Cookie cookie = new Cookie("JT_TICKET", token);
				cookie.setMaxAge(7*24*3600);//设定Cookie存活时间
				cookie.setPath("/");
				response.addCookie(cookie);
				return SysResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户名或密码错误");
	}
	
	//实现用户登出操作
	//1.先获取Cookie,之后获取token数据!
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		String token = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		
		//先删除redis
		jedisCluster.del(token);
		
		//删除Cookie
		Cookie cookie = new Cookie("JT_TICKET","");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		//重定向到系统首页
		return "redirect:/index.html";
	}
}
