package com.yanger.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.ArticleKind;
import com.yanger.generator.dao.MybatisBaseDao;

/**
 * 表article_kind对应Dao接口<br/>
 */
@Mapper
public interface ArticleKindDao extends MybatisBaseDao<ArticleKind, Integer> {

	/**
	 * @description 产寻所有文章分类
	 * @author YangHao
	 * @date 2018年9月10日-下午11:46:30
	 * @return
	 */
	List<ArticleKind> findAll(String module);

}