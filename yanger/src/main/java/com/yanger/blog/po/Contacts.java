package com.yanger.blog.po;

import java.io.Serializable;
import java.util.Date;

import com.yanger.mybatis.annotations.Table;
import com.yanger.mybatis.annotations.Column;
import lombok.Data;

/**
 *
 * 表contacts的PO对象，通过com.yanger.generator包代码工具自动生成<br/>
 * 对应表名：contacts
 *
 */
@Data
@Table(name="contacts")
public class Contacts implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 对应字段：contact_id,备注：主键id */
	@Column(name="contact_id",description="主键id")
	private Integer contactId;

	/** 对应字段：type,备注：类型 */
	@Column(name="type",description="类型")
	private String type;

	/** 对应字段：name,备注：组名 */
	@Column(name="name",description="组名")
	private String name;

	/** 对应字段：depict,备注：描述 */
	@Column(name="depict",description="描述")
	private String depict;

	/** 对应字段：words,备注：口令 */
	@Column(name="words",description="口令")
	private String words;

	/** 对应字段：user_id,备注：成员id */
	@Column(name="user_id",description="成员id")
	private Integer userId;

	/** 对应字段：user_nick_name,备注：成员昵称 */
	@Column(name="user_nick_name",description="成员昵称")
	private String userNickName;

	/** 对应字段：user_real_name,备注：真实姓名 */
	@Column(name="user_real_name",description="真实姓名")
	private String userRealName;

	/** 对应字段：address,备注：地址 */
	@Column(name="address",description="地址")
	private String address;

	/** 对应字段：moblie,备注：联系方式 */
	@Column(name="moblie",description="联系方式")
	private String moblie;

	/** 对应字段：user_img_path,备注：用户头像路径 */
	@Column(name="user_img_path",description="用户头像路径")
	private String userImgPath;

	/** 对应字段：img_path,备注：组描述图片 */
	@Column(name="img_path",description="组描述图片")
	private String imgPath;

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
