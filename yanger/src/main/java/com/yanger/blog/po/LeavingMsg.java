package com.yanger.blog.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 表leaving_msg的PO对象，通过com.yanger.generator包代码工具自动生成
 * 对应表名：leaving_msg
 */
@Data
public class LeavingMsg implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 对应字段：msg_id,备注：主键id */
	private Integer msgId;

	/** 对应字段：type,备注：所属类型 */
	private String type;

	/** 对应字段：user_id,备注：留言用户id */
	private Integer userId;

	/** 对应字段：article_id,备注：留言文章id */
	private Integer articleId;

	/** 对应字段：content,备注：留言内容 */
	private String content;

	/** 对应字段：upper_id,备注：留言父级id */
	private Integer upperId;

	/** 对应字段：msg_order,备注：留言顺序（作为子级） */
	private Integer msgOrder;

	/** 对应字段：status,备注：数据状态 */
	private String status;

	/** 对应字段：insert_time,备注：创建时间 */
	private Date insertTime;

	/** 对应字段：update_time,备注：更新时间 */
	private Date updateTime;

}
