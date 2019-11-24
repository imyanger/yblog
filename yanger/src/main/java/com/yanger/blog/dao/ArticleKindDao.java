package com.yanger.blog.dao;

import java.util.List;

import com.yanger.blog.po.DateSum;
import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.ArticleKind;
import com.yanger.generator.dao.MybatisBaseDao;
import org.apache.ibatis.annotations.Param;

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

	/**
	 * @description 从article查询汇总数据
	 * @author yanger
	 * @date 2019/11/21
	 * @param
	 * @return java.util.List<com.yanger.blog.po.ArticleKind>
	 */
	List<ArticleKind> selectSummary();

	/**
	 * @description 处理统计数据
	 * @author yanger
	 * @date 2019/11/21
	 * @param
	 * @return void
	 */
	void dealSummaryData();

	void deleteAll();

	/**
	 * @description 按年月日期统计
	 * @author yanger
	 * @date 2019/11/21
	 * @param
	 * @return java.util.List<com.yanger.blog.po.DateSum>
	 */
	List<DateSum> dateSum(@Param("type") String type);

}