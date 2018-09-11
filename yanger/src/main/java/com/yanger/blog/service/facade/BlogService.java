package com.yanger.blog.service.facade;

import com.yanger.blog.vo.EssayDataVo;
import com.yanger.blog.vo.HomeDataVo;
import com.yanger.blog.vo.StudyDataVo;

public interface BlogService {

	/**
	 * <p>Description: 获取博客首页数据</p>  
	 * @author YangHao  
	 * @date 2018年9月3日-下午11:06:07
	 * @return
	 * @throws Exception
	 */
	HomeDataVo getHomeData() throws Exception;

	/**
	 * <p>Description: 获取学习笔记页面数据</p>  
	 * @author YangHao  
	 * @date 2018年9月3日-下午11:06:07
	 * @return
	 * @throws Exception
	 */
	StudyDataVo getStudyData() throws Exception;

	/**
	 * <p>Description: 获取心情随笔页面数据</p>  
	 * @author YangHao  
	 * @date 2018年9月3日-下午11:06:07
	 * @return
	 * @throws Exception
	 */
	EssayDataVo getEssayData() throws Exception;

}
