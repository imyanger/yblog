package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.Contact;
import com.yanger.generator.dao.MybatisBaseDao;

/**
 * 表Contact对应Dao接口<br/>
 */
@Mapper
public interface ContactDao extends MybatisBaseDao<Contact, Integer> {

}