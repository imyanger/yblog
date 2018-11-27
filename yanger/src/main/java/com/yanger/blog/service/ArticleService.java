package com.yanger.blog.service;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanger.blog.dao.ArticleDao;
import com.yanger.blog.po.Article;
import com.yanger.blog.vo.ArticleVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.common.util.ConstantUtils;
import com.yanger.common.util.ParamUtils;
import com.yanger.mybatis.paginator.Page;
import com.yanger.mybatis.paginator.PageParam;
import com.yanger.mybatis.util.Pages;
import com.yanger.mybatis.util.ResultPage;

@Service(value = "articleService")
@Transactional
public class ArticleService {
	
	private static final Integer DEFAULT_SIZE = 10;
	
	@Autowired
	ArticleDao articleDao;

	/**
	 * <p>Description: 分页查询文章 </p>  
	 * @author YangHao  
	 * @date 2018年11月27日-下午10:58:25
	 * @param pageQueryVo
	 * @return
	 * @throws Exception
	 */
	public ResultPage<ArticleVo> getPageData(PageQueryVo pageQueryVo) throws Exception {
		int page = pageQueryVo.getPageNo();
		int size = pageQueryVo.getPageSize();
		PageParam pageParam = ParamUtils.getDescPageParam(page, size > 0 ? size : DEFAULT_SIZE, "update_time");
		Article entry = new Article();
		//搜索条件
		if(StringUtils.isNotBlank(pageQueryVo.getQueryValue())){
			//关键字的模糊匹配
			entry.setQueryValue(pageQueryVo.getQueryValue());
		}
		//类型的匹配
		if(StringUtils.isNotBlank(pageQueryVo.getType())){
			entry.setType(pageQueryVo.getType());
		}
		if(StringUtils.isNotBlank(pageQueryVo.getClassify())){
			entry.setClassify(pageQueryVo.getClassify());
		}
		Page<Article> studysPage = articleDao.selectPage(pageParam, entry);
		//分页数据
		return Pages.convert(pageParam, studysPage, ArticleVo.class);
	}

	/**
	 * <p>Description: 新增文章 </p>  
	 * @author YangHao  
	 * @date 2018年11月27日-下午11:00:21
	 * @param articleVo
	 * @throws Exception
	 */
	public void addArticle(ArticleVo articleVo) throws Exception {
		Article entity = new Article();
		BeanUtils.copyProperties(entity, articleVo);
		articleDao.insert(entity);
	}

}
