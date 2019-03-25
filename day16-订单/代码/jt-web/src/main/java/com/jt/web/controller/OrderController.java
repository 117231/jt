package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Order;
import com.jt.web.service.CartService;
import com.jt.web.service.OrderService;
import com.jt.web.util.UserThreadLocal;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	//实现订单页面跳转
	@RequestMapping("/create")
	public String create(Model model) {
		long userId = 
				UserThreadLocal.get().getId();
		List<Cart> cartList = 
				cartService.findCartByUserId(userId);
		model.addAttribute("carts", cartList);
		//跳转订单确认  转发操作
		return "order-cart";
	}
	
	//实现订单新增  jt-web不是jt-order
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order) {
		long userId = UserThreadLocal.get().getId();
		order.setUserId(userId);
		String orderId = 
				orderService.saveOrder(order);
		if(!StringUtils.isEmpty(orderId))
			return SysResult.oK(orderId);
		else
			return SysResult.build(201,"订单新增失败");
	}
	
	
	//实现订单的查询
	@RequestMapping("/success")
	public String findOrderById(String id,Model model) {
		//根据orderId获取订单信息
		Order order = orderService.findOrderById(id);
		model.addAttribute("order", order);
		return "success";
	}
	
	
	
	
}
