package com.yanger.mybatis.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* <p>Title: Column.java</p>  
* <p>Description: 字段列注解 </p>  
* @author 杨号  
* @date 2018年9月14日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface Column {
	
	String name();
	
	String type() default "";
	
	String description() default "";
	
}
