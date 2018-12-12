package com.yanger.generator.domain;

import com.yanger.generator.support.GenType;

import lombok.Data;

@Data
public class Config {

	private String basePackage;

	private boolean keepPoPrefix;

	private String currentFunc;

	private boolean defaultCache;

	private String saveDir;

	private String[] ignoreTablePrefixs;

	private GenType[] genTypes;

}