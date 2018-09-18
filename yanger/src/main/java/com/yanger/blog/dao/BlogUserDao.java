package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.BlogUser;
import com.yanger.mybatis.core.MybatisBaseDao;

/**
 *
 * 表blog_user对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 *
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
	public BlogUser findLoginUser(String code, String pwd);
	
	/**
	 * <p>Description: 根据用户名/邮箱/手机号查找用户 </p>  
	 * @author YangHao  
	 * @date 2018年9月19日-上午12:29:59
	 * @param code
	 * @param pwd
	 * @return
	 */
	public BlogUser findUserByCode(String code);

}