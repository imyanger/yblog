package com.yanger.blog.service.facade;

import com.yanger.blog.vo.HomeDataVo;

public interface BlogService {

	/**
	 * <p>Description: 获取博客首页数据</p>  
	 * @author YangHao  
	 * @date 2018年9月3日-下午11:06:07
	 * @return
	 * @throws Exception
	 */
	HomeDataVo getHomeData() throws Exception;

}
