package com.yanger.blog.service;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanger.blog.dao.ConstDao;
import com.yanger.blog.po.Article;
import com.yanger.blog.po.Const;
import com.yanger.blog.vo.ArticleVo;
import com.yanger.blog.vo.ConstVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.common.util.ParamUtils;
import com.yanger.mybatis.paginator.Page;
import com.yanger.mybatis.paginator.PageParam;
import com.yanger.mybatis.util.Pages;
import com.yanger.mybatis.util.ResultPage;

@Service
@Transactional
public class ConstService {
	
	@Autowired
	ConstDao constDao;

	/**
	 * <p>Description: 获取常量表分页数据 </p>  
	 * @author YangHao  
	 * @date 2018年11月29日-下午9:44:45
	 * @param pageQueryVo
	 * @return
	 * @throws Exception
	 */
	public ResultPage<ConstVo> getPageData(PageQueryVo pageQueryVo) throws Exception {
		int pageNo = pageQueryVo.getPageNo();
		int size = pageQueryVo.getPageSize();
		PageParam pageParam = ParamUtils.getDescPageParam(pageNo, size > 0 ? size : 10, "update_time");
		Const entry = new Const();
		//搜索条件
		if(StringUtils.isNotBlank(pageQueryVo.getQueryValue())){
			//描述的模糊匹配
			entry.setDepict(pageQueryVo.getQueryValue());
		}
		Page<Const> page = constDao.selectPage(pageParam, entry);
		//分页数据
		return Pages.convert(pageParam, page, ConstVo.class);
	}

	/**
	 * <p>Description: 新增文章 </p>  
	 * @author YangHao  
	 * @date 2018年11月27日-下午11:00:21
	 * @param articleVo
	 * @throws Exception
	 */
	public void addArticle(ConstVo constVo) throws Exception {
		Const entity = new Const();
		BeanUtils.copyProperties(entity, constVo);
		constDao.insert(entity);
	}
	
}
