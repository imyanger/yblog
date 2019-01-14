package com.yanger.blog.service;

import java.lang.reflect.InvocationTargetException;
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
	private ConstDao constDao;

	/**
	 * @description 获取常量表分页数据
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
		ConstVo entry = new ConstVo();
		// 搜索条件
		if (StringUtils.isNotBlank(pageQueryVo.getQueryValue())) {
			// 描述的模糊匹配
			entry.setQueryValue(pageQueryVo.getQueryValue());
		}
		Page<Const> page = constDao.selectPageByVo(pageParam, entry);
		// 分页数据
		return Pages.convert(pageParam, page, ConstVo.class);
	}

	/**
	 * @description 新增文章
	 * @author YangHao
	 * @date 2018年11月27日-下午11:00:21
	 * @param articleVo
	 * @throws Exception
	 */
	public void addConst(ConstVo constVo) throws Exception {
		Const entity = new Const();
		BeanUtils.copyProperties(entity, constVo);
		if (entity.getId() != null) {
			constDao.updateById(entity);
		} else {
			constDao.insert(entity);
		}
	}

	/**
	 * @description 获取文章类型和分类
	 * @author YangHao
	 * @date 2018年11月29日-下午10:02:38
	 * @param articleVo
	 * @return
	 */
	public List<ConstVo> getArticleTypes() throws Exception {
		List<ConstVo> typeVos = new ArrayList<>(0);
		// 获取模块
		List<Const> modules = constDao.findAllByDepict("文章类型");
		for (Const module : modules) {
			// 类型
			List<Const> types = constDao.findAllByDepict(module.getVal());
			if (types.isEmpty()) { // 没有子类型，则添加模块
				ConstVo moduleVo = new ConstVo();
				BeanUtils.copyProperties(moduleVo, module);
				typeVos.add(moduleVo);
			} else {
				for (Const type : types) {
					ConstVo typeVo = new ConstVo();
					BeanUtils.copyProperties(typeVo, type);
					// 分类
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

	/**
	 * @description 删除常量
	 * @author YangHao
	 * @time 2018年12月12日-下午9:56:37
	 * @param id
	 * @throws Exception
	 */
	public void delConst(Integer id) throws Exception {
		constDao.deleteById(id);
	}

	/**
	 * @description 获取全部文章类型
	 * @author YangHao
	 * @time 2018年12月12日-下午10:03:37
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public List<ConstVo> getAllArticleTypes(String depict) throws IllegalAccessException, InvocationTargetException {
		List<ConstVo> moduleVos = new ArrayList<>(0);
		// 获取模块
		List<Const> modules = constDao.findAllByDepict(depict);
		for (Const module : modules) {
			ConstVo moduleVo = new ConstVo();
			BeanUtils.copyProperties(moduleVo, module);
			// 类型
			List<Const> types = constDao.findAllByDepict(module.getVal());
			List<ConstVo> typeVos = new ArrayList<>(0);
			for (Const type : types) {
				ConstVo typeVo = new ConstVo();
				BeanUtils.copyProperties(typeVo, type);
				// 分类
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
			moduleVo.setChildren(typeVos);
			moduleVos.add(moduleVo);
		}
		return moduleVos;
	}

}
