package com.yanger.generator.schema;

import java.util.List;

import lombok.Data;

@Data
public class Table {
	private String name;
	private String comment;
	private List<Column> columns;
	private List<PrimaryKey> primaryKeys;
}
