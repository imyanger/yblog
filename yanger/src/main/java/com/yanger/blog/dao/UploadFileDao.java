package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.UploadFile;
import com.yanger.mybatis.core.MybatisBaseDao;

/**
 *
 * 表upload_file对应的基于MyBatis实现的Dao接口<br/>
 * 在其中添加自定义方法
 *
 */
@Mapper
public interface UploadFileDao extends MybatisBaseDao<UploadFile, Integer> {

}