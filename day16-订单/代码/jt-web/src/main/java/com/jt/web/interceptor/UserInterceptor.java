package com.jt.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.common.util.ObjectMapperUtil;
import com.jt.web.pojo.User;
import com.jt.web.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

public class UserInterceptor implements HandlerInterceptor{

	@Autowired
	private JedisCluster jedisCluster;
	//调用Controller中的方法执行之前!!
	/**
	 * 1.当用户发起请求时,首先进行拦截,判断用户信息是否登录
	 * 2.如果用户没有登陆.则重定向到登陆页面
	 * 3.如果用户已经登陆.则放行
	 * 
	 * 问题:
	 * 	1.如何判断用户是否登录???
	 *  首先获取Cookie信息,之后获取token,检查redis
	 *  如果数据都存在说明,用户已经登陆.
	 *  如果有一项为null,则表明用户未登录.
	 *  
	 *  重点注意:
	 *  	拦截器每次都会拦截用户/cart等敏感操作.
	 *  	每次获取user数据保存域对象中.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.获取用户Cookie信息
		Cookie[] cookies = request.getCookies();
		if(cookies !=null) {
			String token = null;
			for (Cookie cookie : cookies) {
				if("JT_TICKET".equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}

			if(!StringUtils.isEmpty(token)) {
				//2.判断redis是否存在该记录
				String userJSON = jedisCluster.get(token);
				if(!StringUtils.isEmpty(userJSON)) {
					
					//获取用户信息
					User user = ObjectMapperUtil.toObject(userJSON, User.class);
					request.setAttribute("JT_USER",user);
					//使用threadLocal封装数据.
					UserThreadLocal.set(user);
					return true;	//放行请求
				}
			}
		}
		//必须重定向到用户登陆页面.
		response.sendRedirect("/user/login.html");
		return false;//false表示拦截   true表示放行
	}

	//controller方法执行结束后执行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	//视图渲染完成之后执行 关闭链接/关闭流(大文件写入写出BIO
	//NIO 非阻塞式IO    )
	//删除某些对象防止内存泄漏
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		UserThreadLocal.remove();
		//方法执行完成后,删除对象
	}

}
