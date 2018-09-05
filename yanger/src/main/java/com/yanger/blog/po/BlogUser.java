package com.yanger.blog.po;

import java.io.Serializable;
import java.util.Date;

import com.yanger.mybatis.annotations.Table;
import com.yanger.mybatis.annotations.Column;
import lombok.Data;

/**
 *
 * 表blog_user的PO对象，通过com.yanger.generator包代码工具自动生成<br/>
 * 对应表名：blog_user
 *
 */
@Data
@Table(name="blog_user")
public class BlogUser implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 对应字段：user_id,备注：用户id */
	@Column(name="user_id",description="用户id")
	private Integer userId;

	/** 对应字段：user_code,备注：用户账号 */
	@Column(name="user_code",description="用户账号")
	private String userCode;

	/** 对应字段：password,备注：用户密码 */
	@Column(name="password",description="用户密码")
	private String password;

	/** 对应字段：user_nick_name,备注：用户昵称 */
	@Column(name="user_nick_name",description="用户昵称")
	private String userNickName;

	/** 对应字段：gender,备注：性别 */
	@Column(name="gender",description="性别")
	private String gender;

	/** 对应字段：user_img_path,备注：头像图片路径 */
	@Column(name="user_img_path",description="头像图片路径")
	private String userImgPath;

	/** 对应字段：summary,备注：用户简介 */
	@Column(name="summary",description="用户简介")
	private String summary;

	/** 对应字段：age,备注：年龄 */
	@Column(name="age",description="年龄")
	private Integer age;

	/** 对应字段：phone_number,备注：电话 */
	@Column(name="phone_number",description="电话")
	private String phoneNumber;

	/** 对应字段：moblie,备注：手机号 */
	@Column(name="moblie",description="手机号")
	private String moblie;

	/** 对应字段：email,备注：邮箱 */
	@Column(name="email",description="邮箱")
	private String email;

	/** 对应字段：address,备注：地址 */
	@Column(name="address",description="地址")
	private String address;

	/** 对应字段：graduate_school,备注：毕业院校 */
	@Column(name="graduate_school",description="毕业院校")
	private String graduateSchool;

	/** 对应字段：education,备注：学历 */
	@Column(name="education",description="学历")
	private String education;

	/** 对应字段：birtd,备注：出生日期 */
	@Column(name="birtd",description="出生日期")
	private Date birtd;

	/** 对应字段：user_real_name,备注：真实姓名 */
	@Column(name="user_real_name",description="真实姓名")
	private String userRealName;

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
