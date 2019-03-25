package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.User;

//定义Mapper接口
public interface ItemMapper extends SysMapper<Item>{
	/**
	 * 如果操作数据库的sql比较简单,可以通过注解
	 * 形式进行编辑
	 *  @Insert
		@Update
	    @Delete
	    大写: ctrl + shift + x
	    小写: ctrl + shift + y
	 */
	@Select("select count(*) from tb_item")
	int findItemCount();
	
	/**
	 * Mybatis中传值要求
	 * 	规定:mybatis中不允许多值传参
	 *  如果需要多值传参,则将多值转化单值
	 *  1.使用对象进行封装  insert update
	 *  2.使用集合封装   list array Map<k,v>
	 * @param start
	 * @param rows
	 * @return
	 */
	List<Item> findItemListByPage(@Param("start")int start,@Param("rows")int rows);
	
	@Select("select name from tb_item_cat where id= #{itemCatId}")
	String findItemCatNameById(Long itemCatId);
	
	void updateStatus(@Param("ids")Long[] ids,@Param("status") int status);
}
