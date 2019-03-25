package com.jt.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.User;
import com.jt.web.service.CartService;
import com.jt.web.util.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	//根据userId查询购物车数据
	@RequestMapping("/show.html")
	public String findCartByUserId(Model model) {
		//User user = (User) request.getAttribute("JT_USER");
		//Long userId = user.getId();
		Long userId = UserThreadLocal.get().getId();
		List<Cart> cartList = cartService.findCartByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}

	//购物车新增
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart,HttpServletRequest request) {
		//User user = (User) request.getAttribute("JT_USER");
		//Long userId = user.getId();
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId,HttpServletRequest request) {
		//User user = (User) request.getAttribute("JT_USER");
		//long userId = user.getId();
		Long userId = UserThreadLocal.get().getId();
		cartService.deleteCartById(userId,itemId);
		//重定向到列表页面
		return "redirect:/cart/show.html";
	}
	
	//模拟商品的修改
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(Cart cart) {
		System.out.println("修改商品数量成功"+cart.getItemId());
		return SysResult.oK();
	}
	
	
	
}
