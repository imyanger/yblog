package com.yanger.blog.po;

import java.io.Serializable;
import java.util.Date;

import com.yanger.mybatis.annotations.Table;
import com.yanger.mybatis.annotations.Column;
import lombok.Data;

/**
 *
 * 表operate_log的PO对象，通过com.yanger.generator包代码工具自动生成<br/>
 * 对应表名：operate_log
 *
 */
@Data
@Table(name="operate_log")
public class OperateLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 对应字段：log_id,备注：主键id */
	@Column(name="log_id",description="主键id")
	private Integer logId;

	/** 对应字段：operate_type,备注：操作类型 */
	@Column(name="operate_type",description="操作类型")
	private String operateType;

	/** 对应字段：operate_desc,备注：操作描述 */
	@Column(name="operate_desc",description="操作描述")
	private String operateDesc;

	/** 对应字段：operator_id,备注：操作人id */
	@Column(name="operator_id",description="操作人id")
	private Integer operatorId;

	/** 对应字段：operator_name,备注：操作人名称 */
	@Column(name="operator_name",description="操作人名称")
	private String operatorName;

	/** 对应字段：update_params,备注：更新参数 */
	@Column(name="update_params",description="更新参数")
	private String updateParams;

	/** 对应字段：request_url,备注：请求地址 */
	@Column(name="request_url",description="请求地址")
	private String requestUrl;

	/** 对应字段：table_name,备注：表名 */
	@Column(name="table_name",description="表名")
	private String tableName;

	/** 对应字段：status,备注：数据状态 */
	@Column(name="status",description="数据状态")
	private String status;

	/** 对应字段：insert_time,备注：创建时间 */
	@Column(name="insert_time",description="创建时间")
	private Date insertTime;

	/** 对应字段：update_time,备注：更新时间 */
	@Column(name="update_time",description="更新时间")
	private Date updateTime;


}
