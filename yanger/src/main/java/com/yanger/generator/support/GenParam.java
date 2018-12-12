package com.yanger.generator.support;

import lombok.Data;

/**
 * @description 用于代码生成的参数类
 * @author 杨号
 * @date 2018年9月14日
 */
@Data
public class GenParam {

	/** 基础包名 */
	private String module;

	/** 包含的表名 */
	private String[] tables;

	/**
	 * @description 便捷构造方式
	 * 
	 * @param module-模块
	 * @param tables-包含的表名
	 */
	public GenParam(String module, String[] tables) {
		super();
		this.tables = tables;
		this.module = module;
	}

}