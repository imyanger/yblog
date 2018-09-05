package com.yanger.generator.domain;

import lombok.Data;

@Data
public class CodeGen {
	private Config config;
	private Db db;
	private Module[] modules;
}
