package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yanger.blog.po.BlogUser;
import com.yanger.blog.vo.BlogUserVo;
import com.yanger.generator.dao.MybatisBaseDao;
import com.yanger.mybatis.paginator.Page;
import com.yanger.mybatis.paginator.PageParam;

/**
 * 表blog_user对应Dao接口<br/>
 */
@Mapper
public interface BlogUserDao extends MybatisBaseDao<BlogUser, Integer> {

	/**
	 * @description 根据用户名/邮箱/手机号和密码查找用户
	 * @author YangHao
	 * @date 2018年9月19日-上午12:29:59
	 * @param code
	 * @param pwd
	 * @return
	 */
	public BlogUser findLoginUser(@Param("code") String code, @Param("pwd") String pwd, @Param("type") String type);

	/**
	 * @description 根据用户名/邮箱/手机号查找用户
	 * @author YangHao
	 * @date 2018年9月19日-上午12:29:59
	 * @param code
	 * @param pwd
	 * @return
	 */
	public BlogUser findUserByCode(@Param("code") String code);

	/**
	 * @description 根据vo进行查询
	 * @author YangHao  
	 * @time 2018年12月19日-下午11:30:19
	 * @param pageParam
	 * @param entity
	 * @return
	 */
	public Page<BlogUser> selectPageByVo(PageParam pageParam, BlogUserVo entry);

}