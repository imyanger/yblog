package com.yanger.blog.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanger.blog.service.ConstService;
import com.yanger.blog.vo.ConstVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.common.vo.ApiResponse;
import com.yanger.mybatis.util.ResultPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
// @Token
@RestController
@RequestMapping("/const")
public class ConstApi {

	@Autowired
	private ConstService constService;

	/**
	 * @description 查询常量表分页数据
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "查询常量表分页数据", notes = "")
	@PostMapping("/list")
	public ApiResponse<ResultPage<ConstVo>> articleList(@RequestBody PageQueryVo pageQueryVo) {
		ApiResponse<ResultPage<ConstVo>> api = new ApiResponse<>();
		try {
			ResultPage<ConstVo> page = constService.getPageData(pageQueryVo);
			api.setData(page);
		} catch (Exception e) {
			api.error("加载常量表分页失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 新增常量
	 * @author YangHao
	 * @date 2018年11月29日-下午10:02:38
	 * @param articleVo
	 * @return
	 */
	@ApiOperation(value = "新增常量", notes = "")
	@PutMapping("/add")
	public ApiResponse<String> articleAdd(@RequestBody ConstVo constVo) {
		ApiResponse<String> api = new ApiResponse<>();
		try {
			constService.addConst(constVo);
		} catch (Exception e) {
			api.error("新增常量处理失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 获取文章类型和分类，不包含模块选项
	 * @author YangHao
	 * @date 2018年11月29日-下午10:02:38
	 * @param articleVo
	 * @return
	 */
	@ApiOperation(value = "获取文章类型和分类", notes = "")
	@GetMapping("/artTypes")
	public ApiResponse<List<ConstVo>> artTypes() {
		ApiResponse<List<ConstVo>> api = new ApiResponse<>();
		try {
			List<ConstVo> types = constService.getArticleTypes();
			api.setData(types);
		} catch (Exception e) {
			api.error("获取文章类型和分类");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 获取文章类型和分类，包含模块选项
	 * @author YangHao
	 * @date 2018年11月29日-下午10:02:38
	 * @param articleVo
	 * @return
	 */
	@ApiOperation(value = "获取文章类型和分类", notes = "")
	@GetMapping("/allTypes")
	public ApiResponse<List<ConstVo>> allTypes() {
		ApiResponse<List<ConstVo>> api = new ApiResponse<>();
		try {
			List<ConstVo> types = constService.getAllArticleTypes("文章类型");
			api.setData(types);
		} catch (Exception e) {
			api.error("获取文章类型和分类");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 根据id删除常量
	 * @author YangHao
	 * @date 2018年11月29日-下午10:02:38
	 * @param articleVo
	 * @return
	 */
	@ApiOperation(value = "根据id删除常量", notes = "")
	@DeleteMapping("/{id}")
	public ApiResponse<String> delConst(@PathVariable Integer id) {
		ApiResponse<String> api = new ApiResponse<>();
		try {
			constService.delConst(id);
		} catch (Exception e) {
			api.error("删除常量失败");
			e.printStackTrace();
		}
		return api;
	}

}
