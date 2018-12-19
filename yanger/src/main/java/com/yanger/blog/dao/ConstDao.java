package com.yanger.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.Const;
import com.yanger.blog.vo.ConstVo;
import com.yanger.generator.dao.MybatisBaseDao;
import com.yanger.mybatis.paginator.Page;
import com.yanger.mybatis.paginator.PageParam;

/**
 * 表const对应Dao接口
 */
@Mapper
public interface ConstDao extends MybatisBaseDao<Const, Integer> {

	/**
	 * @description 根据描述获取常量
	 * @author YangHao  
	 * @time 2018年12月12日-下午10:10:33
	 * @param depict
	 * @return
	 */
	List<Const> findAllByDepict(String depict);
	
	/**
	 * @description 根据vo进行查询
	 * @author YangHao  
	 * @time 2018年12月19日-下午11:30:19
	 * @param pageParam
	 * @param entity
	 * @return
	 */
	Page<Const> selectPageByVo(PageParam pageParam, ConstVo entity);
	
}