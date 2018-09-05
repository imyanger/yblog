package com.yanger.generator.schema;

import lombok.Data;

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
