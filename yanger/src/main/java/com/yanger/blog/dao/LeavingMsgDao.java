package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.LeavingMsg;
import com.yanger.mybatis.core.MybatisBaseDao;

/**
 *
 * 表leaving_msg对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 *
 */
@Mapper
public interface LeavingMsgDao extends MybatisBaseDao<LeavingMsg, Integer> {

}