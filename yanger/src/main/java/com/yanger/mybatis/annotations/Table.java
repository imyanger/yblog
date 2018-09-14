package com.yanger.mybatis.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
* <p>Title: Table.java</p>  
* <p>Description: 字段表注解 </p>  
* @author 杨号  
* @date 2018年9月14日
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	
	String name();
	
	String description() default "";
	
}
