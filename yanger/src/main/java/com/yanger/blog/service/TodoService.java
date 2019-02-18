package com.yanger.blog.service;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanger.blog.dao.TodoDao;
import com.yanger.blog.po.Todo;
import com.yanger.blog.vo.TodoVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.common.util.ParamUtils;
import com.yanger.mybatis.paginator.Page;
import com.yanger.mybatis.paginator.PageParam;
import com.yanger.mybatis.util.Pages;
import com.yanger.mybatis.util.ResultPage;

@Service
@Transactional
public class TodoService {
	
	@Autowired
	private TodoDao todoDao;

	/**
	 * @description 获取待办任务表分页数据
	 * @author YangHao
	 * @date 2018年11月29日-下午9:44:45
	 * @param pageQueryVo
	 * @return
	 * @throws Exception
	 */
	public ResultPage<TodoVo> getPageData(PageQueryVo pageQueryVo) throws Exception {
		int pageNo = pageQueryVo.getPageNo();
		int size = pageQueryVo.getPageSize();
		PageParam pageParam = ParamUtils.getDescPageParam(pageNo, size > 0 ? size : 10, "update_time");
		TodoVo entry = new TodoVo();
		// 搜索条件
		if (StringUtils.isNotBlank(pageQueryVo.getQueryValue())) {
			// 描述的模糊匹配
			entry.setQueryValue(pageQueryVo.getQueryValue());
		}
		Page<Todo> page = todoDao.selectPageByVo(pageParam, entry);
		// 分页数据
		return Pages.convert(pageParam, page, TodoVo.class);
	}
	
	/**
	 * @description 新增文章
	 * @author YangHao
	 * @date 2018年11月27日-下午11:00:21
	 * @param articleVo
	 * @throws Exception
	 */
	public void addTodo(TodoVo todoVo) throws Exception {
		Todo entity = new Todo();
		BeanUtils.copyProperties(entity, todoVo);
		if (entity.getId() != null) {
			todoDao.updateById(entity);
		} else {
			todoDao.insert(entity);
		}
	}
	
	/**
	 * @description 删除待办任务
	 * @author YangHao
	 * @time 2018年12月12日-下午9:56:37
	 * @param id
	 * @throws Exception
	 */
	public void delTodo(Integer id) throws Exception {
		todoDao.deleteById(id);
	}

}
