package com.yanger.blog.po;

import java.io.Serializable;
import java.util.Date;

import com.yanger.mybatis.annotations.Table;
import com.yanger.mybatis.annotations.Column;
import lombok.Data;

/**
 *
 * 表leaving_msg的PO对象，通过com.yanger.generator包代码工具自动生成<br/>
 * 对应表名：leaving_msg
 *
 */
@Data
@Table(name="leaving_msg")
public class LeavingMsg implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 对应字段：msg_id,备注：主键id */
	@Column(name="msg_id",description="主键id")
	private Integer msgId;

	/** 对应字段：type,备注：所属类型 */
	@Column(name="type",description="所属类型")
	private String type;

	/** 对应字段：user_id,备注：留言用户id */
	@Column(name="user_id",description="留言用户id")
	private Integer userId;

	/** 对应字段：user_nick_name,备注：用户昵称 */
	@Column(name="user_nick_name",description="用户昵称")
	private String userNickName;

	/** 对应字段：user_img_path,备注：头像图片路径 */
	@Column(name="user_img_path",description="头像图片路径")
	private String userImgPath;

	/** 对应字段：article_id,备注：留言文章id */
	@Column(name="article_id",description="留言文章id")
	private Integer articleId;

	/** 对应字段：art_img_path,备注：留言文章图片路径 */
	@Column(name="art_img_path",description="留言文章图片路径")
	private String artImgPath;

	/** 对应字段：article_title,备注：留言文章标题 */
	@Column(name="article_title",description="留言文章标题")
	private String articleTitle;

	/** 对应字段：content,备注：留言内容 */
	@Column(name="content",description="留言内容")
	private String content;

	/** 对应字段：upper_id,备注：留言父级id */
	@Column(name="upper_id",description="留言父级id")
	private Integer upperId;

	/** 对应字段：msg_order,备注：留言顺序（作为子级） */
	@Column(name="msg_order",description="留言顺序（作为子级）")
	private Integer msgOrder;

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
