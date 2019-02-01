package com.yanger.blog.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 表contact的VO对象,通过com.yanger.generator包代码工具自动生成
 * 对应表名：contact
 */
@Data
public class ContactVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 对应字段：contact_id,备注：主键id */
	private Integer contactId;

	/** 对应字段：type,备注：类型 */
	private String type;

	/** 对应字段：name,备注：组名 */
	private String name;

	/** 对应字段：depict,备注：描述 */
	private String depict;

	/** 对应字段：words,备注：口令 */
	private String words;

	/** 对应字段：user_id,备注：成员(用户)id */
	private Integer userId;

	/** 对应字段：img_path,备注：组描述图片 */
	private String imgPath;

	/** 对应字段：status,备注：数据状态 */
	private String status;

	/** 对应字段：insert_time,备注：创建时间 */
	private Date insertTime;

	/** 对应字段：update_time,备注：更新时间 */
	private Date updateTime;


}
