package com.yanger.generator.schema;

import lombok.Data;

/**
 * @description 主键
 * @author 杨号
 * @date 2018年9月14日
 */
@Data
public class PrimaryKey {

	private String pkName;

	private int keySeq;

	private String columnName;

}
