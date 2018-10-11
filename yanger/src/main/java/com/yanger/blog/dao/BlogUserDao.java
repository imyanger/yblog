package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yanger.blog.po.BlogUser;
import com.yanger.generator.dao.MybatisBaseDao;

/**
 * 表blog_user对应Dao接口<br/>
 */
@Mapper
public interface BlogUserDao extends MybatisBaseDao<BlogUser, Integer> {
	
	/**
	 * <p>Description: 根据用户名/邮箱/手机号和密码查找用户 </p>  
	 * @author YangHao  
	 * @date 2018年9月19日-上午12:29:59
	 * @param code
	 * @param pwd
	 * @return
	 */
	public BlogUser findLoginUser(@Param("code") String code, @Param("pwd") String pwd);
	
	/**
	 * <p>Description: 根据用户名/邮箱/手机号查找用户 </p>  
	 * @author YangHao  
	 * @date 2018年9月19日-上午12:29:59
	 * @param code
	 * @param pwd
	 * @return
	 */
	public BlogUser findUserByCode(@Param("code") String code);

}