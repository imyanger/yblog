package com.yanger.blog.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ArticleType extends Const{

	private static final long serialVersionUID = 1L;

	/** 子集数量 */
	private int num;

	/** 所属模块 */
	private String module;

}
