package com.yanger.blog.service;

import java.util.Date;

import com.yanger.blog.util.ConstUtils;
import com.yanger.common.config.ServerConfig;
import com.yanger.common.util.CommonConstant;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanger.blog.dao.ArticleDao;
import com.yanger.blog.po.Article;
import com.yanger.blog.vo.ArticleVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.blog.util.BlogConstant;
import com.yanger.common.util.ParamUtils;
import com.yanger.mybatis.paginator.Page;
import com.yanger.mybatis.paginator.PageParam;
import com.yanger.mybatis.util.Pages;
import com.yanger.mybatis.util.ResultPage;

@Service(value = "articleService")
@Transactional
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private ConstUtils constUtils;

	@Autowired
	private ServerConfig serverConfig;

	/**
	 * @description 分页查询文章
	 * @author YangHao
	 * @date 2018年11月27日-下午10:58:25
	 * @param pageQueryVo
	 * @return
	 * @throws Exception
	 */
	public ResultPage<ArticleVo> getPageData(PageQueryVo pageQueryVo) throws Exception {
		int pageNo = pageQueryVo.getPageNo();
		int size = pageQueryVo.getPageSize();
		PageParam pageParam = ParamUtils.getDescPageParam(pageNo, size > 0 ? size : 10, "update_time");
		ArticleVo entry = new ArticleVo();
		// 文章状态
		if(StringUtils.isNotBlank(pageQueryVo.getArtState())) {
			entry.setArtState(pageQueryVo.getArtState());
		}
		// 搜索条件
		if (StringUtils.isNotBlank(pageQueryVo.getQueryValue())) {
			// 关键字的模糊匹配
			entry.setQueryValue(pageQueryVo.getQueryValue());
		}
		// 类型的匹配
		if (StringUtils.isNotBlank(pageQueryVo.getType())) {
			entry.setType(pageQueryVo.getType());
		}
		if (StringUtils.isNotBlank(pageQueryVo.getClassify())) {
			entry.setClassify(pageQueryVo.getClassify());
		}
		Page<Article> page = articleDao.selectPageByVo(pageParam, entry);
		// 分页数据
		ResultPage <ArticleVo> resultPage = Pages.convert(pageParam, page, ArticleVo.class);
		resultPage.getData().forEach(a -> {
			constUtils.setVal(a);
			a.setServerPath(serverConfig.getUrl());
		});
		return resultPage;
	}

	/**
	 * @description 新增文章
	 * @author YangHao
	 * @date 2018年11月27日-下午11:00:21
	 * @param articleVo
	 * @throws Exception
	 */
	public void addArticle(ArticleVo articleVo) throws Exception {
		Article entity = null;
		if(articleVo.getArticleId() != null) {
			entity = articleDao.selectById(articleVo.getArticleId());
		} else {
			entity = new Article();
		}
		BeanUtils.copyProperties(entity, articleVo);
		//路径去掉根路径
		entity.setArtImgPath(entity.getArtImgPath().replace(CommonConstant.FILE_PATH, ""));
		// 作者
		entity.setAuthor("yanger");
		// 有效状态
		entity.setStatus(BlogConstant.STATUS_VALID);
		entity.setLikes(0);
		entity.setViews(0);
		entity.setCommons(0);
		// 插入时间
		entity.setInsertTime(new Date());
		if(articleVo.getArticleId() != null) {
			articleDao.updateById(entity);
		} else {
			articleDao.insert(entity);
		}
	}

	/**
	 * @description 更新文章状态
	 * @author yanger
	 * @date 2019/11/21
	 * @param id
	 * @param state
	 * @return void
	 */
	public void updateState(int id, String state) {
		articleDao.updateState(id, state);
	}

	/**
	 * @description 根据id删除文章
	 * @author yanger
	 * @date 2019/11/21
	 * @param id
	 * @return void
	 */
	public void delArticle(Integer id) {
		articleDao.deleteById(id);
	}

	public ArticleVo getArticle(int id) throws Exception {
		ArticleVo articleVo = new ArticleVo();
		Article article = articleDao.selectById(id);
		BeanUtils.copyProperties(articleVo, article);
		constUtils.setVal(articleVo);
		articleVo.setServerPath(serverConfig.getUrl());
		return articleVo;
	}

}
