package com.yanger.generator.core;

import lombok.Data;

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
