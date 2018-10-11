package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.OperateLog;
import com.yanger.generator.dao.MybatisBaseDao;

/**
 * 表operate_log对应Dao接口<br/>
 */
@Mapper
public interface OperateLogDao extends MybatisBaseDao<OperateLog, Integer> {

}