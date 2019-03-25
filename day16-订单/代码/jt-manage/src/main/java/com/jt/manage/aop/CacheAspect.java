package com.jt.manage.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.common.util.ObjectMapperUtil;
import com.jt.manage.anno.CacheAnno;
import com.jt.manage.anno.CacheAnno.CACHE_TYPE;

import redis.clients.jedis.Jedis;
@Component		//交给spring容器管理
@Aspect			//标识切面
public class CacheAspect {
	
	//当执行时才注入,后期将该对象换为集群对象
	@Autowired(required=false)
	private Jedis jedis;
	
	/**
	 * 控制用户是否查询数据库  目标方法
	 * @param joinPoint
	 * @param cacheAnno
	 * @return
	 */
	
	//利用环绕通知 拦截所有的缓存注解
	@Around("@annotation(cacheAnno)")
	public Object around(ProceedingJoinPoint joinPoint,CacheAnno cacheAnno) {
		
		return getObject(joinPoint,cacheAnno);
	}
	
	public Object getObject(ProceedingJoinPoint joinPoint, CacheAnno cacheAnno) {
		//获取参数
		CACHE_TYPE cacheType = cacheAnno.cacheType();
		String key = cacheAnno.key();
		int index = cacheAnno.index();
		Class<?> targetClass = cacheAnno.targetClass();
		//根据位置获取参数
		Long id = (Long) joinPoint.getArgs()[index];
		//拼接参数 ITEM_CAT_0
		String redisKey = key + id;
		Object object = null;
		switch (cacheType) {
			case FIND:		//表示查询缓存
				object = findObject(joinPoint,redisKey,targetClass);
				break;
			case UPDATE:	//表示更新缓存
				object = updateObject(joinPoint,redisKey);
				break;
			}
		
		return object;
	}

	private Object findObject(ProceedingJoinPoint joinPoint, String key, Class<?> targetClass) {
		//检查缓存中是否有数据
		String result = jedis.get(key);
		Object object = null;
		try {
			if(StringUtils.isEmpty(result)) {
				//表示缓存中没有数据,则查询数据库
				object = joinPoint.proceed();
				String json = ObjectMapperUtil.toJSON(object);
				jedis.set(key, json);
				System.out.println("AOP查询真实数据库!!");
			}else {
				//表示缓存中有数据,查询缓存
				object = ObjectMapperUtil.toObject(result, targetClass);
				System.out.println("AOP查询缓存!!");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return object;
	}
	
	private Object updateObject(ProceedingJoinPoint joinPoint, String redisKey) {
		//更新缓存,删除即可
		Object object = null;
		try {
			jedis.del(redisKey);
			object = joinPoint.proceed();
			System.out.println("AOP缓存删除");
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return object;
	}
}
