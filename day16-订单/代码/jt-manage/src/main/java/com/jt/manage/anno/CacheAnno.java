package com.jt.manage.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//反射???? 不加参数??? 方法名称 find
public @interface CacheAnno {
	String key();			//定义key值
	int index();			//参数定义位置
	Class targetClass();	//定义目标类型
	CACHE_TYPE cacheType() default CACHE_TYPE.FIND;
	
	//定义泛型类型 
	enum CACHE_TYPE{
		FIND,				//定义查找
		UPDATE				//定义更新
	}
}
