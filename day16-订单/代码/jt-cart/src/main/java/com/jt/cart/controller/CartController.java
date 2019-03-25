package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	//根据userId查询购物车记录
	@RequestMapping("/query/{userId}")
	public SysResult findCartByUserId(@PathVariable Long userId) {
		
		List<Cart> cartList = cartService.findCartByUserId(userId);
		return SysResult.oK(cartList);
	}
	
	//实现购物车新增
	@RequestMapping("/save")
	public SysResult saveCart(String cartJSON) {
		Cart cart = ObjectMapperUtil.toObject(cartJSON, Cart.class);
		cartService.saveCart(cart);
		return SysResult.oK();
	}
	
	//实现购物车删除操作
	@RequestMapping("/delete/{userId}/{itemId}")
	public SysResult deleteCartById(Cart cart) {
		
		cartService.deleteCartById(cart);
		return SysResult.oK();
	}
	
	
	
	
	
}
