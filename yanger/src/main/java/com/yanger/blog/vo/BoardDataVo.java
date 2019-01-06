package com.yanger.blog.vo;

import java.io.Serializable;

import com.yanger.mybatis.util.ResultPage;

import lombok.Data;

@Data
public class BoardDataVo implements Serializable {

	private static final long serialVersionUID = 1L;

	//学习笔记及其分页信息
	private ResultPage<LeavingMsgVo> msgPage;
	
}
