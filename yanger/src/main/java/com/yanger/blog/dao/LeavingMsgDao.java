package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.LeavingMsg;
import com.yanger.generator.dao.MybatisBaseDao;

/**
 * 表leaving_msg对应Dao接口<br/>
 */
@Mapper
public interface LeavingMsgDao extends MybatisBaseDao<LeavingMsg, Integer> {

}