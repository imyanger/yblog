package com.yanger.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.ArticleKinds;
import com.yanger.mybatis.core.MybatisBaseDao;

/**
 *
 * 表article_kinds对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 *
 */
@Mapper
public interface ArticleKindsDao extends MybatisBaseDao<ArticleKinds, Integer> {
	
	/**
	 * <p>Description: 查询所有文章分类数据  </p>  
	 * @author YangHao  
	 * @date 2018年9月7日-下午10:37:40
	 * @return
	 */
	List<ArticleKinds> findAll();

}