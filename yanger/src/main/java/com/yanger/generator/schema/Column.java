package com.yanger.generator.schema;

import lombok.Data;

/**
 * @description 列属性
 * @author 杨号
 * @date 2018年9月14日
 */
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
