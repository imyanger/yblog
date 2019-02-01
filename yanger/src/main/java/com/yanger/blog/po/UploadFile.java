package com.yanger.blog.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 表upload_file的PO对象，通过com.yanger.generator包代码工具自动生成
 * 对应表名：upload_file
 */
@Data
public class UploadFile implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 对应字段：path_id,备注：主键id */
	private Integer pathId;

	/** 对应字段：type,备注：所属类型 */
	private String type;

	/** 对应字段：classify,备注：分类（具体） */
	private String classify;

	/** 对应字段：file_name,备注：图片名 */
	private String fileName;

	/** 对应字段：suffix,备注：图片后缀 */
	private String suffix;

	/** 对应字段：img_path,备注：图片路径 */
	private String imgPath;

	/** 对应字段：sequence,备注：顺序 */
	private Integer sequence;

	/** 对应字段：user_id,备注：创建人（用户）id */
	private Integer userId;

	/** 对应字段：status,备注：数据状态 */
	private String status;

	/** 对应字段：insert_time,备注：创建时间 */
	private Date insertTime;

	/** 对应字段：update_time,备注：更新时间 */
	private Date updateTime;

}
