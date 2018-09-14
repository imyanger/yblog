package com.yanger.generator.schema;

import lombok.Data;

/**
* <p>Title: Column.java</p>  
* <p>Description: 列属性 </p>  
* @author 杨号  
* @date 2018年9月14日
 */
@Data
public class Column {
	
	private String name;
	
	private String type;
	
	private int size;
	
	private String defaultValue;
	
	private String comment;
	
	private boolean nullable;
	
	private boolean autoIncrement; 

}
