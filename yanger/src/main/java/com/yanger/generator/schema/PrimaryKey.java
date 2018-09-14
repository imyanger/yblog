package com.yanger.generator.schema;

import lombok.Data;

/**
* <p>Title: PrimaryKey.java</p>  
* <p>Description: 主键 </p>  
* @author 杨号  
* @date 2018年9月14日
 */
@Data
public class PrimaryKey {
	
	private String pkName;
	
	private int keySeq;
	
	private String columnName;
	
}
