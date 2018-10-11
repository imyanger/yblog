package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.OuterLink;
import com.yanger.generator.dao.MybatisBaseDao;

/**
 * 表outer_link对应Dao接口<br/>
 */
@Mapper
public interface OuterLinkDao extends MybatisBaseDao<OuterLink, Integer> {

}