package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.Todo;
import com.yanger.blog.vo.TodoVo;
import com.yanger.generator.dao.MybatisBaseDao;
import com.yanger.mybatis.paginator.Page;
import com.yanger.mybatis.paginator.PageParam;

/**
 * 表todo对应Dao接口
 */
@Mapper
public interface TodoDao extends MybatisBaseDao<Todo, Integer> {
	
	/**
	 * @description 根据vo进行查询
	 * @author YangHao  
	 * @time 2018年1月9日-下午11:30:19
	 * @param pageParam
	 * @param entity
	 * @return
	 */
	Page<Todo> selectPageByVo(PageParam pageParam, TodoVo entity);
	

}