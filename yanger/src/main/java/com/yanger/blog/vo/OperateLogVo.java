package com.yanger.blog.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 表operate_log的VO对象,通过com.yanger.generator包代码工具自动生成
 * 对应表名：operate_log
 */
@Data
public class OperateLogVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 对应字段：log_id,备注：主键id */
	private Integer logId;

	/** 对应字段：operator_id,备注：操作人(用户)id */
	private Integer operatorId;

	/** 对应字段：operate_ip,备注：操作机器Ip */
	private String operateIp;

	/** 对应字段：operate_type,备注：操作类型 */
	private String operateType;

	/** 对应字段：operate_desc,备注：操作描述 */
	private String operateDesc;

	/** 对应字段：request_params,备注：请求参数 */
	private String requestParams;

	/** 对应字段：request_url,备注：请求地址 */
	private String requestUrl;

	/** 对应字段：status,备注：数据状态 */
	private String status;

	/** 对应字段：insert_time,备注：创建时间 */
	private Date insertTime;

	/** 对应字段：update_time,备注：更新时间 */
	private Date updateTime;


}
