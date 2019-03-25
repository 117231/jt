package com.jt.manage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jt.manage.pojo.User;
import com.jt.manage.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//查询user信息进行页面展现jsp
	/**
	 * page 只对当前jsp生效     小
	 * request 对用户的一次请求生效
	 * session 对当前用户会话生效
	 * context 服务器作用域     大
	 * @return
	 */
	@RequestMapping("/findAll")
	public String findList(Model model) {
		/*List<User> userList = new ArrayList<>();
		User user1 = new User();
		user1.setId(100);
		user1.setName("1811班");
		user1.setAge(3);
		user1.setSex("纯爷们");
		userList.add(user1);*/
		//将数据保存到request域中
		
		List<User> userList = 
				userService.findList();
		model.addAttribute("userList", userList);
		return "userList";
	}
	
}
