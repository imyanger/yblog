package com.yanger.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanger.blog.service.ArticleService;
import com.yanger.blog.vo.ArticleVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.common.vo.ApiResponse;
import com.yanger.mybatis.util.ResultPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
//@Token
@RestController
@RequestMapping("/art")
public class ArticleApi {
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * @description 查询文章分页数据  
	 * @author YangHao  
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "查询文章分页数据", notes = "")
	@PostMapping("/list")
	public ApiResponse<ResultPage<ArticleVo>> articleList(@RequestBody PageQueryVo pageQueryVo){
		ApiResponse<ResultPage<ArticleVo>> api = new ApiResponse<>();
		try {
			ResultPage<ArticleVo> page = articleService.getPageData(pageQueryVo);
			api.setData(page);
		} catch (Exception e) {
			api.error("加载文章分页数据失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 新增文章  
	 * @author YangHao  
	 * @date 2018年11月29日-下午10:02:38
	 * @param articleVo
	 * @return
	 */
	@ApiOperation(value = "新增文章", notes = "")
	@PutMapping("/add")
	public ApiResponse<String> articleAdd(@RequestBody ArticleVo articleVo){
		ApiResponse<String> api = new ApiResponse<>();
		try {
			articleService.addArticle(articleVo);
		} catch (Exception e) {
			api.error("新增文章处理失败");
			e.printStackTrace();
		}
		return api;
	}
	
}
