package com.yanger.blog.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yanger.mybatis.util.ResultPage;

import lombok.Data;

@Data
public class StudyDataVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//学习笔记及其分页信息
	ResultPage<ArticleVo> studyPage;
	
	//热门文章
	List<ArticleVo> hots = new ArrayList<>(0);
	
	//文章分类
	List<ArticleKindVo> kinds;
	
}
