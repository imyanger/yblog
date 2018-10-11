package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.UploadFile;
import com.yanger.generator.dao.MybatisBaseDao;

/**
 * 表upload_file对应Dao接口<br/>
 */
@Mapper
public interface UploadFileDao extends MybatisBaseDao<UploadFile, Integer> {

}