package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.Article;
import com.yanger.blog.vo.ArticleVo;
import com.yanger.generator.dao.MybatisBaseDao;
import com.yanger.mybatis.paginator.Page;
import com.yanger.mybatis.paginator.PageParam;

/**
 * 表article对应Dao接口
 */
@Mapper
public interface ArticleDao extends MybatisBaseDao<Article, Integer> {
	
	/**
	 * @description 根据vo进行查询
	 * @author YangHao  
	 * @time 2018年12月19日-下午11:30:19
	 * @param pageParam
	 * @param entity
	 * @return
	 */
	Page<Article> selectPageByVo(PageParam pageParam, ArticleVo entity);
	
}