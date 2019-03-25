package com.jt.manage.vo;

import java.util.List;

import com.jt.manage.pojo.Item;

public class EasyUITable {
	private Integer total;
	private List<Item> rows;
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<Item> getRows() {
		return rows;
	}
	public void setRows(List<Item> rows) {
		this.rows = rows;
	}
	
}
