package com.jt.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.common.service.RedisService;
import com.jt.common.util.ObjectMapperUtil;
import com.jt.manage.anno.CacheAnno;
import com.jt.manage.anno.CacheAnno.CACHE_TYPE;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.vo.EasyUITree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	//从spring容器中获取对象
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	//tb_item_cat 
	public List<ItemCat> findItemCatByParentId(long parentId){
		
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		return itemCatMapper.select(itemCat);
	}
	
	@Override
	//@CacheAnno(cacheType=CACHE_TYPE.UPDATE,index=0,key="ITEM_CAT_",targetClass=ArrayList.class)
	public List<EasyUITree> findItemCatList(long parentId) {
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();
		List<ItemCat> itemCatList =  
				findItemCatByParentId(parentId);
		for (ItemCat itemCat : itemCatList) {
			EasyUITree uiTree = new EasyUITree();
			uiTree.setId(itemCat.getId());
			uiTree.setText(itemCat.getName());
			//一二级使用closed  三级使用open 
			String state = itemCat.getIsParent()?"closed":"open";
			uiTree.setState(state);
			treeList.add(uiTree);
		}
		System.out.println("查询数据库");
		return treeList;
	}

	/**
	 * 1.生成key
	 * 2.查询redis缓存
	 * 		null: 则调用业务层方法获取数据,
	 * 			  利用工具类封装为JSON,保存到缓存中
	 * 		!null:
	 * 			  将缓存转化为对象返回
	 *  改代码耦合性高.
	 *  AOP:要求采用AOP形式实现redis缓存操作
	 *  通知:环绕
	 *  自定义注解:
	 *  切入点表达式??
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EasyUITree> findCacheItemCat(Long parentId) {
		String key = "ITEM_CAT_" + parentId; 
		String json = jedisCluster.get(key);
		List<EasyUITree> treeList = new ArrayList<>();
		if(StringUtils.isEmpty(json)) {
			treeList = findItemCatList(parentId);
			String result = 
					ObjectMapperUtil.toJSON(treeList);
			jedisCluster.set(key, result);
			//System.out.println("查询数据库!!!!!");
		}else {
			treeList = ObjectMapperUtil.toObject(json,ArrayList.class);
			//System.out.println("查询缓存!!!!!!!");
		}
		
		return treeList;
	}
}
