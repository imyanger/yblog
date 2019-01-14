package com.yanger.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanger.blog.service.TodoService;
import com.yanger.blog.vo.TodoVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.common.vo.ApiResponse;
import com.yanger.mybatis.util.ResultPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
// @Token
@RestController
@RequestMapping("/todo")
public class TodoApi {

	@Autowired
	private TodoService todoService;
	
	/**
	 * @description 查询待办任务表分页数据
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "查询待办任务表分页数据", notes = "")
	@PostMapping("/list")
	public ApiResponse<ResultPage<TodoVo>> articleList(@RequestBody PageQueryVo pageQueryVo) {
		ApiResponse<ResultPage<TodoVo>> api = new ApiResponse<>();
		try {
			ResultPage<TodoVo> page = todoService.getPageData(pageQueryVo);
			api.setData(page);
		} catch (Exception e) {
			api.error("加载待办任务表分页失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 新增待办任务
	 * @author YangHao
	 * @date 2018年11月29日-下午10:02:38
	 * @param articleVo
	 * @return
	 */
	@ApiOperation(value = "新增待办任务", notes = "")
	@PutMapping("/add")
	public ApiResponse<String> articleAdd(@RequestBody TodoVo todoVo) {
		ApiResponse<String> api = new ApiResponse<>();
		try {
			todoService.addTodo(todoVo);
		} catch (Exception e) {
			api.error("新增待办任务处理失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 根据id删除待办任务
	 * @author YangHao
	 * @date 2018年11月29日-下午10:02:38
	 * @param articleVo
	 * @return
	 */
	@ApiOperation(value = "根据id删除待办任务", notes = "")
	@DeleteMapping("/{id}")
	public ApiResponse<String> delTodo(@PathVariable Integer id) {
		ApiResponse<String> api = new ApiResponse<>();
		try {
			todoService.delTodo(id);
		} catch (Exception e) {
			api.error("删除待办任务失败");
			e.printStackTrace();
		}
		return api;
	}

}
