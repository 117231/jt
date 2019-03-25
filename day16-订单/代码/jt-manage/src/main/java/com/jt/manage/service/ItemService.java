package com.jt.manage.service;

import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.vo.EasyUITable;

public interface ItemService {

	EasyUITable findItemListByPage(int page, int rows);

	String findItemCatNameById(Long itemCatId);

	void saveItem(Item item, String desc);

	void updateStatus(Long[] ids, int status);

	void updateItem(Item item, String desc);

	void deleteItem(Long[] ids);

	ItemDesc findItemDescById(Long itemId);

	Item findItemById(Long itemId);

}
