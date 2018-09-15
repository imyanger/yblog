package com.yanger.blog.service.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanger.blog.dao.ArticleDao;
import com.yanger.blog.dao.ArticleKindDao;
import com.yanger.blog.dao.LeavingMsgDao;
import com.yanger.blog.dao.OuterLinkDao;
import com.yanger.blog.po.Article;
import com.yanger.blog.po.ArticleKind;
import com.yanger.blog.po.LeavingMsg;
import com.yanger.blog.po.OuterLink;
import com.yanger.blog.service.facade.BlogService;
import com.yanger.blog.vo.ArticleKindVo;
import com.yanger.blog.vo.ArticleVo;
import com.yanger.blog.vo.EssayDataVo;
import com.yanger.blog.vo.HomeDataVo;
import com.yanger.blog.vo.LeavingMsgVo;
import com.yanger.blog.vo.OuterLinkVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.blog.vo.StudyDataVo;
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
	
	@Autowired
	private ArticleKindDao articleKindDao;
	
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
		List<ArticleVo> studys = this.findArticlesByModule(3, ConstantUtils.ARTICLE_MODULE_STUDY);
		homeData.setStudys(studys);
		//获取心情随笔
		List<ArticleVo> essays = this.findArticlesByModule(2, ConstantUtils.ARTICLE_MODULE_STUDY);
		homeData.setEssays(essays);
		//获取最新留言
		List<LeavingMsgVo> msgs = this.findMsgs(PageParam.NO_PAGE, 10);
		homeData.setMsgs(msgs);
		//获取外连接
		List<OuterLinkVo> shipLinks = this.findShipLinks(8, ConstantUtils.LINK_TYPE_SHIP);
		homeData.setShipLinks(shipLinks);
		return homeData;
	}
	
	/**
	 * <p>Description: 获取不同模块最新的文章，自定义降序 </p>  
	 * @author YangHao  
	 * @date 2018年9月4日-上午12:21:07
	 * @param size
	 * @param type
	 * @return
	 */
	private List<ArticleVo> findArticles(int page, int size, String module, String order) throws Exception {
		PageParam pageParam = ParamUtils.getDescPageParam(page, size, order);
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
	 * <p>Description: 获取不同模块最新的文章，根据update_time排序 </p>  
	 * @author YangHao  
	 * @date 2018年9月4日-上午12:21:07
	 * @param size
	 * @param type
	 * @return
	 */
	private List<ArticleVo> findArticlesByModule(int size, String module) throws Exception {
		return this.findArticles(PageParam.NO_PAGE, size, module, "update_time");
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

	/**
	 * <p>Description: 获取学习笔记页面数据</p>  
	 * @author YangHao  
	 * @date 2018年9月3日-下午11:06:07
	 * @return
	 * @throws Exception
	 */
	@Override
	public StudyDataVo getStudyData() throws Exception {
		StudyDataVo studyDataVo = new StudyDataVo();
		//获得读书笔记
		ResultPage<ArticleVo> studyPage = this.findArticlePageByModule(1, 6, ConstantUtils.ARTICLE_MODULE_STUDY);
		studyDataVo.setStudyPage(studyPage);
		//获取热门文章
		List<ArticleVo> hots = this.findArticles(PageParam.NO_PAGE, 10, ConstantUtils.ARTICLE_MODULE_STUDY, "likes");
		studyDataVo.setHots(hots);
		//获取文章分类
		List<ArticleKindVo> kinds = this.findArticleKinds(ConstantUtils.ARTICLE_MODULE_STUDY);
		studyDataVo.setKinds(kinds);
		return studyDataVo;
	}
	
	/**
	 * <p>Description: 获取不同模块最新的文章，根据update_time排序 </p>  
	 * @author YangHao  
	 * @date 2018年9月4日-上午12:21:07
	 * @param size
	 * @param type
	 * @return
	 */
	private ResultPage<ArticleVo> findArticlePageByModule(int page, int size, String module) throws Exception {
		PageParam pageParam = ParamUtils.getDescPageParam(page, size, "update_time");
		Article entry = new Article();
		entry.setModule(module);
		//有效
		entry.setStatus(ConstantUtils.STATUS_VALID);
		Page<Article> studysPage = articleDao.selectPage(pageParam, entry);
		//分页数据
		return Pages.convert(pageParam, studysPage, ArticleVo.class);
	}
	
	/**
	 * <p>Description: 获取文章分类数据 </p>  
	 * @author YangHao  
	 * @date 2018年9月4日-上午12:21:07
	 * @param size
	 * @param type
	 * @return
	 */
	private List<ArticleKindVo> findArticleKinds(String module) throws Exception {
		//重组新的层级分类
		List<ArticleKindVo> kinds = new ArrayList<>();
		List<ArticleKind> ArticleKindsList = articleKindDao.findAll(module);
		Map<String, ArticleKindVo> map = new HashedMap<>(0);
		//按层级组织
		for (ArticleKind articleKinds : ArticleKindsList) {
			ArticleKindVo articleKindsVo = new ArticleKindVo();
			BeanUtils.copyProperties(articleKindsVo, articleKinds);
			ArticleKindVo outer = null;
			List<ArticleKindVo> inners = null;
			//文章的type大类型
			String type = articleKinds.getType();
			if(map.containsKey(type)) {
				outer = map.get(type);
				inners = outer.getChildren();
			}else {
				outer = new ArticleKindVo();
				BeanUtils.copyProperties(outer, articleKinds);
				inners = new ArrayList<>();
				//新创建的对象放入集合中
				kinds.add(outer);
				map.put(type, outer);
			}
			inners.add(articleKindsVo);
			outer.setChildren(inners);
		}
		//分页数据
		return kinds;
	}

	/**
	 * <p>Description: 获取心情随笔页面数据</p>  
	 * @author YangHao  
	 * @date 2018年9月3日-下午11:06:07
	 * @return
	 * @throws Exception
	 */
	@Override
	public EssayDataVo getEssayData() throws Exception {
		EssayDataVo essayDataVo = new EssayDataVo();
		//获得读书笔记
		ResultPage<ArticleVo> essayPage = this.findArticlePageByModule(1, 5, ConstantUtils.ARTICLE_MODULE_ESSAY);
		essayDataVo.setEssayPage(essayPage);
		//获取热门文章
		List<ArticleVo> hots = this.findArticles(PageParam.NO_PAGE, 6, ConstantUtils.ARTICLE_MODULE_ESSAY, "views");
		essayDataVo.setHots(hots);
		//获取文章分类
		List<ArticleKindVo> kinds = this.findArticleKinds(ConstantUtils.ARTICLE_MODULE_ESSAY);
		essayDataVo.setKinds(kinds);
		return essayDataVo;
	}

	/**
	 * <p>Description: 查询文章分页数据   默认更新时间排序</p>  
	 * @author YangHao  
	 * @date 2018年9月12日-下午11:13:03
	 * @param pageQueryVo
	 * @return
	 */
	@Override
	public ResultPage<ArticleVo> getPageData(PageQueryVo pageQueryVo) throws Exception {
		int page = pageQueryVo.getPageNo();
		int size = 6;
		if(ConstantUtils.ARTICLE_MODULE_ESSAY.equals(pageQueryVo.getModule())){
			size = 5;
		}
		PageParam pageParam = ParamUtils.getDescPageParam(page, size, "update_time");
		Article entry = new Article();
		entry.setModule(pageQueryVo.getModule());
		//有效
		entry.setStatus(ConstantUtils.STATUS_VALID);
		//搜索条件
		if(StringUtils.isNotBlank(pageQueryVo.getQueryValue())){
			//关键字的模糊匹配
			entry.setQueryValue(pageQueryVo.getQueryValue());
		}
		//类型的匹配
		if(StringUtils.isNotBlank(pageQueryVo.getClassify())){
			entry.setType(pageQueryVo.getType());
			entry.setClassify(pageQueryVo.getClassify());
		}
		Page<Article> studysPage = articleDao.selectPage(pageParam, entry);
		//分页数据
		return Pages.convert(pageParam, studysPage, ArticleVo.class);
	}
	
	
}
