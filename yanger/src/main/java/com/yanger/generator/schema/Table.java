package com.yanger.generator.schema;

import java.util.List;

import lombok.Data;

/**
 * @description 表属性
 * @author 杨号
 * @date 2018年9月14日
 */
@Data
public class Table {

	private String name;

	private String comment;

	private List<Column> columns;

	private List<PrimaryKey> primaryKeys;

}
