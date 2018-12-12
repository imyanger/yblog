package com.yanger.generator.support;

import lombok.Data;

/**
 * @description 文件信息
 * @author 杨号
 * @date 2018年9月14日
 */
@Data
public class GenFileInfo {

	private String name;

	private String packageName;

	private String path;

	public GenFileInfo(String name, String packageName, String path) {
		super();
		this.name = name;
		this.packageName = packageName;
		this.path = path;
	}

}
