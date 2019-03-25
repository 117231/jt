package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.vo.EasyUITable;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	/**
	 * sql中分页语法:
	 * 	查询前20条 
		SELECT * FROM tb_item LIMIT 0,20;
		/*第2页 
		SELECT * FROM tb_item LIMIT 20,20;
		/*第3页
		SELECT * FROM tb_item LIMIT 40,20;
		/*第N页
		SELECT * FROM tb_item LIMIT (n-1)*20,20
	 */
	@Override
	public EasyUITable findItemListByPage(int page, int rows) {
		//int total = itemMapper.findItemCount();
		//如果查询时不需要添加where条件则写null
		int total = itemMapper.selectCount(null);
		//定义起始位置
		int start = (page - 1) * rows;
		//分页以后的记录
		List<Item> itemList = 
				itemMapper.findItemListByPage(start,rows);
		EasyUITable table = new EasyUITable();
		table.setTotal(total);
		table.setRows(itemList);
		return table;
	}

	@Override
	public String findItemCatNameById(Long itemCatId) {
		
		return itemMapper.findItemCatNameById(itemCatId);
	}
	
	
	/**
	 * 查询主键自增后的ID值,先入库后查询在一个事务中
	 * INSERT INTO USER VALUE(NULL,"霸波尔奔",4000,"男");
     * SELECT LAST_INSERT_ID(); 是通用Mapper提供的.
	 */
	@Override
	public void saveItem(Item item,String desc) {
		 item.setStatus(1); //表示正常
		 item.setCreated(new Date());
		 item.setUpdated(item.getCreated());
		 itemMapper.insert(item);
		 //入门商品详情信息
		 //动态回显新增ID.
		 ItemDesc itemDesc = new ItemDesc();
		 itemDesc.setItemId(item.getId());
		 itemDesc.setItemDesc(desc);
		 itemDesc.setCreated(item.getCreated());
		 itemDesc.setUpdated(item.getCreated());
		 itemDescMapper.insert(itemDesc);
	}
	
	//批量操作
	@Override
	public void updateStatus(Long[] ids, int status) {
		
		itemMapper.updateStatus(ids,status);
	}

	@Override
	public void updateItem(Item item,String desc) {
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}

	/*如果有主外键关联.则先删除外键信息,之后
	 * 删除主键信息*/
	@Override
	public void deleteItem(Long[] ids) {
		
		itemDescMapper.deleteByIDS(ids);
		itemMapper.deleteByIDS(ids);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		
		return itemDescMapper.selectByPrimaryKey(itemId);
	}
	
	//根据id获取item对象
	@Override
	public Item findItemById(Long itemId) {
		
		return itemMapper.selectByPrimaryKey(itemId);
	}
}
