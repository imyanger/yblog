package com.yanger.blog.po;

import java.io.Serializable;
import java.util.Date;

import com.yanger.mybatis.annotations.Table;
import com.yanger.mybatis.annotations.Column;
import lombok.Data;

/**
 *
 * 表outer_link的PO对象，通过com.yanger.generator包代码工具自动生成<br/>
 * 对应表名：outer_link
 *
 */
@Data
@Table(name="outer_link")
public class OuterLink implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 对应字段：link_id,备注：主键id */
	@Column(name="link_id",description="主键id")
	private Integer linkId;

	/** 对应字段：type,备注：所属类型 */
	@Column(name="type",description="所属类型")
	private String type;

	/** 对应字段：depict,备注：描述 */
	@Column(name="depict",description="描述")
	private String depict;

	/** 对应字段：link,备注：链接 */
	@Column(name="link",description="链接")
	private String link;

	/** 对应字段：sequence,备注：顺序 */
	@Column(name="sequence",description="顺序")
	private Integer sequence;

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
