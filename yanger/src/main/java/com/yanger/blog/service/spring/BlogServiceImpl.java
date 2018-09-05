package com.yanger.blog.service.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanger.blog.dao.ArticleDao;
import com.yanger.blog.dao.LeavingMsgDao;
import com.yanger.blog.dao.OuterLinkDao;
import com.yanger.blog.po.Article;
import com.yanger.blog.po.LeavingMsg;
import com.yanger.blog.po.OuterLink;
import com.yanger.blog.service.facade.BlogService;
import com.yanger.blog.vo.ArticleVo;
import com.yanger.blog.vo.HomeDataVo;
import com.yanger.blog.vo.LeavingMsgVo;
import com.yanger.blog.vo.OuterLinkVo;
import com.yanger.common.util.ConstantUtils;
import com.yanger.common.util.ParamUtils;
import com.yanger.mybatis.core.Page;
import com.yanger.mybatis.core.PageParam;
import com.yanger.mybatis.util.Pages;
import com.yanger.mybatis.util.ResultPage;

@Service(value = "blogService")
public class BlogServiceImpl implements BlogService{

	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private LeavingMsgDao leavingMsgDao;
	
	@Autowired
	private OuterLinkDao outerLinkDao;
	
	/**
	 * <p>Description: 获取博客首页数据</p>  
	 * @author YangHao  
	 * @date 2018年9月3日-下午11:06:07
	 * @return
	 * @throws Exception
	 */
	@Override
	public HomeDataVo getHomeData() throws Exception {
		HomeDataVo homeData = new HomeDataVo();
		//获得读书笔记
		List<ArticleVo> studys = this.findArticlesByModule(PageParam.NO_PAGE, 3, ConstantUtils.ARTICLE_MODULE_STUDY);
		homeData.setStudys(studys);
		//获取心情随笔
		List<ArticleVo> essays = this.findArticlesByModule(PageParam.NO_PAGE, 2, ConstantUtils.ARTICLE_MODULE_STUDY);
		homeData.setEssays(essays);
		//获取最新留言
		List<LeavingMsgVo> msgs = this.findMsgs(PageParam.NO_PAGE, 10);
		homeData.setMsgs(msgs);
		//获取外连接
		List<OuterLinkVo> shipLinks = this.findShipLinks(6, ConstantUtils.LINK_TYPE_SHIP);
		homeData.setShipLinks(shipLinks);
		return homeData;
	}

	/**
	 * <p>Description: 获取不同模块最新的文章，根据update_time排序 </p>  
	 * @author YangHao  
	 * @date 2018年9月4日-上午12:21:07
	 * @param size
	 * @param type
	 * @return
	 */
	private List<ArticleVo> findArticlesByModule(int page, int size, String module) throws Exception {
		PageParam pageParam = ParamUtils.getDescPageParam(size, "update_time");
		Article entry = new Article();
		entry.setModule(module);
		//有效
		entry.setStatus(ConstantUtils.STATUS_VALID);
		Page<Article> studysPage = articleDao.selectPage(pageParam, entry);
		//分页数据
		ResultPage<ArticleVo> studysResult = Pages.convert(pageParam, studysPage, ArticleVo.class);
		return studysResult.getData();
	}
	
	/**
	 * <p>Description: 获取最新的留言，根据update_time排序 </p>  
	 * @author YangHao  
	 * @date 2018年9月4日-上午12:21:07
	 * @param size
	 * @param type
	 * @return
	 */
	private List<LeavingMsgVo> findMsgs(int page, int size) throws Exception {
		PageParam pageParam = ParamUtils.getDescPageParam(page, size, "update_time");
		LeavingMsg entry = new LeavingMsg();
		entry.setStatus(ConstantUtils.STATUS_VALID);
		//留言类型
		entry.setType(ConstantUtils.MSG_TYPE_BOARD);
		Page<LeavingMsg> msgsPage = leavingMsgDao.selectPage(pageParam, entry);
		ResultPage<LeavingMsgVo> msgsResult = Pages.convert(pageParam, msgsPage, LeavingMsgVo.class);
		return msgsResult.getData();
	}
	
	/**
	 * <p>Description: 根据类型查询连接，根据order排序 </p>  
	 * @author YangHao  
	 * @date 2018年9月4日-下午10:21:49
	 * @param i
	 * @param string
	 * @return
	 * @throws Exception
	 */
	private List<OuterLinkVo> findShipLinks(int size, String type) throws Exception {
		PageParam pageParam = ParamUtils.getAscPageParam(size, "sequence");
		OuterLink entry = new OuterLink();
		entry.setType(type);
		entry.setStatus(ConstantUtils.STATUS_VALID);
		Page<OuterLink> shipLinksPage = outerLinkDao.selectPage(pageParam, entry);
		ResultPage<OuterLinkVo> shipLinksResult = Pages.convert(pageParam, shipLinksPage, OuterLinkVo.class);
		return shipLinksResult.getData();
	}
	
}
