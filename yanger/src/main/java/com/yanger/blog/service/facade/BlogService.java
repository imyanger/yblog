package com.yanger.blog.service.facade;

import com.yanger.blog.vo.ArticleVo;
import com.yanger.blog.vo.BlogUserVo;
import com.yanger.blog.vo.EssayDataVo;
import com.yanger.blog.vo.HomeDataVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.blog.vo.StudyDataVo;
import com.yanger.mybatis.util.ResultPage;

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

	/**
	 * <p>Description: 查询文章分页数据   默认更新时间排序  </p>  
	 * @author YangHao  
	 * @date 2018年9月12日-下午11:13:03
	 * @param pageQueryVo
	 * @return
	 */
	ResultPage<ArticleVo> getPageData(PageQueryVo pageQueryVo) throws Exception;

	/**
	 * <p>Description: 用户注册，插入用户信息 </p>  
	 * @author YangHao  
	 * @date 2018年9月18日-上午12:18:31
	 * @param blogUserVo
	 * @throws Exception
	 */
	void userRegister(BlogUserVo blogUserVo) throws Exception;

}
