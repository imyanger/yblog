package com.yanger.blog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.yanger.blog.po.LeavingMsg;
import com.yanger.blog.vo.LeavingMsgVo;
import com.yanger.generator.dao.MybatisBaseDao;
import com.yanger.mybatis.paginator.Page;
import com.yanger.mybatis.paginator.PageParam;

/**
 * 表leaving_msg对应Dao接口<br/>
 */
@Mapper
public interface LeavingMsgDao extends MybatisBaseDao<LeavingMsg, Integer> {
	
	/**
	 * @description 根据LeavingMsg查询出LeavingMsgVo分页数据
	 * @author YangHao  
	 * @time 2018年12月12日-下午10:10:33
	 * @param depict
	 * @return
	 */
	Page<LeavingMsgVo> selectPageForVo(PageParam pageParam, LeavingMsg entity);

}