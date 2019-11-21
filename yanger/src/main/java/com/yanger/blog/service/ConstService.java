package com.yanger.blog.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yanger.blog.cache.CacheRunner;
import com.yanger.blog.po.ArticleType;
import com.yanger.blog.util.BlogConstant;
import com.yanger.common.util.PageUtils;
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

	@Autowired
	private CacheRunner cacheRunner;
	
	/**
	 * @description 
	 * @author 杨号  
	 * @date 2019年11月19日-上午11:05:17  
	 * @param type
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public List<ConstVo> getConstList(String type) throws IllegalAccessException, InvocationTargetException {
		List<Const> consts = constDao.findAllByType(type);
		List<ConstVo> constVos = new ArrayList<>(consts.size());
		for (Const constPo : consts) {
			ConstVo constVo = new ConstVo();
			BeanUtils.copyProperties(constVo, constPo);
			constVos.add(constVo);
		}
		return constVos;
	}

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
	 * @param constVo
	 * @throws Exception
	 */
	public void saveOrUpdateConst(ConstVo constVo) throws Exception {
		Const entity = new Const();
		BeanUtils.copyProperties(entity, constVo);
		if (entity.getId() != null) {
			constDao.updateById(entity);
		} else {
			constDao.insert(entity);
		}
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
	 * @description 获取文章分类数据
	 * @author yanger
	 * @date 2019/11/19
	 * @param pageQueryVo
	 * @return com.yanger.mybatis.util.ResultPage<com.yanger.blog.po.ArticleType>
	 */
	public ResultPage<ArticleType> getArtTypePage(PageQueryVo pageQueryVo) {
		int pageNo = pageQueryVo.getPageNo();
		int size = pageQueryVo.getPageSize();
		PageParam pageParam = ParamUtils.getDescPageParam(pageNo, size > 0 ? size : 10, "update_time");
		ConstVo entry = new ConstVo();
		List<Const> consts = constDao.findAllByType(BlogConstant.ARTICLE_MODULE_UPPER_CODE);
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<>();
 		consts.forEach(v -> {
			sb.append("'" + v.getCode() + "'," );
			map.put(v.getCode(), v.getVal());
		});
 		// 分页组件针对vo中list的foreach有bug，暂拼接sql解决
		entry.setUpperCodes(sb.toString().substring(0, sb.length() - 1 ));
		Page <ArticleType> articleTypes = constDao.selectArtTypePage(pageParam, entry);
        articleTypes.forEach(at -> {
            at.setModule(map.get(at.getUpperCode()));
        });
		return new PageUtils <ArticleType>().convert(articleTypes);
	}

	/**
	 * @description 保存文章分类
	 * @author YangHao  
	 * @time 2019年11月19日-下午11:06:42
	 * @param constVos
	 * @throws Exception 
	 */
	public void saveArtClassifyConst(List<ConstVo> constVos) throws Exception {
		String type = "";
		for (ConstVo constVo : constVos) {
			if(StringUtils.isNoneBlank(constVo.getUpperCode())){
				type = constVo.getUpperCode();
				break;
			}
		}
		// 先根据type删除旧的
		constDao.delByType(type);
		for (ConstVo constVo : constVos) {
			constVo.setId(null);
			saveOrUpdateConst(constVo);
		}
	}

	/**
	 * @description 获取文章分级数据
	 * @author yanger
	 * @date 2019/11/20
	 * @param
	 * @return java.util.List<com.yanger.blog.vo.ConstVo>
	 */
	public List<ConstVo> getMtcs() {
		Map <String, Const> constCache = cacheRunner.getConstCache();
		Map<String, List<ConstVo>> upperCodeMap = new HashMap <>();
		constCache.entrySet().forEach(entry -> {
			List<ConstVo> constVos;
			String upperCode = entry.getValue().getUpperCode();
			if(upperCodeMap.containsKey(upperCode)) {
				constVos = upperCodeMap.get(upperCode);
			} else {
				constVos = new ArrayList <>();
			}
			try {
				ConstVo constVo = new ConstVo();
				BeanUtils.copyProperties(constVo, entry.getValue());
				constVos.add(constVo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			upperCodeMap.put(upperCode, constVos);
		});

		List<ConstVo> modules = upperCodeMap.get(BlogConstant.ARTICLE_MODULE_UPPER_CODE);
		if(modules != null){
            modules.forEach(module -> {
                List<ConstVo> types = upperCodeMap.get(module.getCode());
                module.setChildren(types);
                if(types != null) {
                    types.forEach(type -> {
                        List<ConstVo> classifys = upperCodeMap.get(type.getCode());
                        type.setChildren(classifys);
                    });
                }
            });
        }
		return modules;
	}
}
