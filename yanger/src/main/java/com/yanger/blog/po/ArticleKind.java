package com.yanger.blog.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 表article_kind的PO对象，通过com.yanger.generator包代码工具自动生成
 * 对应表名：article_kind
 */
@Data
public class ArticleKind implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 对应字段：art_kinds_id,备注：主键id */
	private Integer artKindsId;

	/** 对应字段：module,备注：模块 */
	private String module;

	/** 对应字段：type,备注：所属类型 */
	private String type;

	/** 对应字段：classify,备注：分类（具体） */
	private String classify;

	/** 对应字段：sum,备注：文章数量 */
	private Integer sum;

	/** 对应字段：status,备注：数据状态 */
	private String status;

	/** 对应字段：insert_time,备注：创建时间 */
	private Date insertTime;

	/** 对应字段：update_time,备注：更新时间 */
	private Date updateTime;

}
