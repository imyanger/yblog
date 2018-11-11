package com.yanger.blog.vo;

import java.io.Serializable;
import java.util.List;

import com.yanger.mybatis.util.ResultPage;

import lombok.Data;

@Data
public class ViewDataVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//展示文章
	private ArticleVo article;
	
	//留言信息
	private ResultPage<LeavingMsgVo> msgPage;
	
	//推荐阅读
	private List<ArticleVo> hots;

}
