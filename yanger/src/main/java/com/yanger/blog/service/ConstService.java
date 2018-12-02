package com.yanger.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanger.blog.dao.ConstDao;
import com.yanger.blog.po.Const;
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

	/**
	 * <p>Description: 获取文章类型和分类 </p>  
	 * @author YangHao  
	 * @date 2018年11月29日-下午10:02:38
	 * @param articleVo
	 * @return
	 */
	public List<ConstVo> addArticleTypes()  throws Exception {
		List<ConstVo> typeVos = new ArrayList<>(0);
		//获取模块
		List<Const> modules = constDao.findAllByDepict("文章类型");
		for (Const module : modules) {
			//类型
			List<Const> types = constDao.findAllByDepict(module.getVal());
			if(types.isEmpty()){ //没有子类型，则添加模块
				ConstVo moduleVo = new ConstVo();
				BeanUtils.copyProperties(moduleVo, module);
				typeVos.add(moduleVo);
			}else {
				for (Const type : types) {
					ConstVo typeVo = new ConstVo();
					BeanUtils.copyProperties(typeVo, type);
					//分类
					List<Const> classifys = constDao.findAllByDepict(type.getVal());
					List<ConstVo> classifyVos = new ArrayList<>(0);
					for (Const classify : classifys) {
						ConstVo classifyVo = new ConstVo();
						BeanUtils.copyProperties(classifyVo, classify);
						classifyVos.add(classifyVo);
					}
					typeVo.setChildren(classifyVos);
					typeVos.add(typeVo);
				}
			}
		}
		return typeVos;
	}
	
}
