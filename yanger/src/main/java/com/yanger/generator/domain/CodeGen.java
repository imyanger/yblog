package com.yanger.generator.domain;

import lombok.Data;

/**
* <p>Title: CodeGen.java</p>  
* <p>Description: </p>  
* @author 杨号  
* @date 2018年9月14日
 */
@Data
public class CodeGen {
	
	private Config config;
	
	private Db db;
	
	private Module[] modules;
	
}
