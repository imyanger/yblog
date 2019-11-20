package com.yanger.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yanger.blog.po.ArticleType;
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
	 * @param type
	 * @return
	 */
	List<Const> findAllByType(@Param("type") String type);
	
	/**
	 * @description 根据vo进行查询
	 * @author YangHao  
	 * @time 2018年12月19日-下午11:30:19
	 * @param pageParam
	 * @param entity
	 * @return
	 */
	Page<Const> selectPageByVo(PageParam pageParam, ConstVo entity);
	
	/**
	 * 
	 * @description 
	 * @author 杨号  
	 * @date 2019年11月19日-下午1:54:41  
	 * @param pageParam
	 * @param entity
	 * @return
	 */
	Page<ArticleType> selectArtTypePage(PageParam pageParam, ConstVo entity);

	/**
	 * @description 查询子集个数
	 * @author yanger
	 * @date 2019/11/19
	 * @param upperCode
	 * @return int
	 */
	int getCountByUpperCode(@Param("upperCode") String upperCode);
	
	
	/**
	 * @description 根据type删除常量
	 * @author YangHao  
	 * @time 2019年11月19日-下午11:16:45
	 * @param type
	 */
	void delByType(@Param("type") String type);

	/**
	 * @description 获取所有常量
	 * @author yanger
	 * @date 2019/11/20
	 * @param
	 * @return java.util.List<com.yanger.blog.po.Const>
	 */
	List<Const> findAll();
	
}