package com.yanger.blog.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 表todo的VO对象,通过com.yanger.generator包代码工具自动生成
 * 对应表名：todo
 */
@Data
public class TodoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 对应字段：id,备注：主键 */
	private Integer id;

	/** 对应字段：type,备注：类型 */
	private String type;

	/** 对应字段：title,备注：标题 */
	private String title;

	/** 对应字段：depict,备注：描述 */
	private String depict;

	/** 对应字段：status,备注：状态 */
	private String status;

	/** 对应字段：insert_time,备注：插入时间 */
	private Date insertTime;

	/** 对应字段：update_time,备注：更新时间 */
	private Date updateTime;
	
	/** 查询条件 */
	private String queryValue;


}
