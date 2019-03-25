package com.jt.cart.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("tb_cart")
public class Cart {
	private Long id;
	private Long userId;
	private Long itemId;	//通过userId和itemId确定购物行为
	private String itemTitle;
	private String itemImage;
	private Long itemPrice;
	private Integer num;
	private Date created;
	private Date updated;
}
