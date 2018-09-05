package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.Contacts;
import com.yanger.mybatis.core.MybatisBaseDao;

/**
 *
 * 表contacts对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 *
 */
@Mapper
public interface ContactsDao extends MybatisBaseDao<Contacts, Integer> {

}