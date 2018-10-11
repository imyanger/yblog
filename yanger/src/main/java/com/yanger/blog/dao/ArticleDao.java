package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.Article;
import com.yanger.generator.dao.MybatisBaseDao;

/**
 * 表article对应Dao接口
 */
@Mapper
public interface ArticleDao extends MybatisBaseDao<Article, Integer> {
	

}