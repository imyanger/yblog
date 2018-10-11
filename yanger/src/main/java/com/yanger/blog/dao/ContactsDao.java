package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.Contacts;
import com.yanger.generator.dao.MybatisBaseDao;

/**
 * 表contacts对应Dao接口<br/>
 */
@Mapper
public interface ContactsDao extends MybatisBaseDao<Contacts, Integer> {

}