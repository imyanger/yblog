package com.yanger.blog.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class PageQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int pageNo;
	
	private int pageSize;

	//搜索条件
	private String queryValue;
	
	//所属类型
	private String type;
	
	//具体类型
	private String classify;
	
	//文章模块
	private String module;

	//文章id
	private Integer articleId;

	// 文章状态
	private String artState;

	// 文章时间
	private String artDate;
	
}
