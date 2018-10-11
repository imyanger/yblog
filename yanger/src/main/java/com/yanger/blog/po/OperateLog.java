package com.yanger.blog.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 表operate_log的PO对象，通过com.yanger.generator包代码工具自动生成
 * 对应表名：operate_log
 */
@Data
public class OperateLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 对应字段：log_id,备注：主键id */
	private Integer logId;

	/** 对应字段：operate_type,备注：操作类型 */
	private String operateType;

	/** 对应字段：operate_desc,备注：操作描述 */
	private String operateDesc;

	/** 对应字段：operator_id,备注：操作人id */
	private Integer operatorId;

	/** 对应字段：operator_name,备注：操作人名称 */
	private String operatorName;

	/** 对应字段：update_params,备注：更新参数 */
	private String updateParams;

	/** 对应字段：request_url,备注：请求地址 */
	private String requestUrl;

	/** 对应字段：table_name,备注：表名 */
	private String tableName;

	/** 对应字段：status,备注：数据状态 */
	private String status;

	/** 对应字段：insert_time,备注：创建时间 */
	private Date insertTime;

	/** 对应字段：update_time,备注：更新时间 */
	private Date updateTime;

}
