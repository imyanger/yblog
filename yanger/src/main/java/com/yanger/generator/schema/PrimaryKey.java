package com.yanger.generator.schema;

import lombok.Data;

@Data
public class PrimaryKey {
	private String pkName;
	private int keySeq;
	private String columnName;
}
