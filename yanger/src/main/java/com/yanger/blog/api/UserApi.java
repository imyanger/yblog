package com.yanger.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanger.blog.service.UserService;
import com.yanger.blog.vo.BlogUserVo;
import com.yanger.blog.vo.ConstVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.common.vo.ApiResponse;
import com.yanger.mybatis.util.ResultPage;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("user")
public class UserApi {
	
	@Autowired
	private UserService userService;
	
	/**
	 * @description 查询用户分页数据
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "查询常量表分页数据", notes = "")
	@PostMapping("/list")
	public ApiResponse<ResultPage<BlogUserVo>> constList(@RequestBody PageQueryVo pageQueryVo) {
		ApiResponse<ResultPage<BlogUserVo>> api = new ApiResponse<>();
		try {
			ResultPage<BlogUserVo> page = userService.getPageData(pageQueryVo);
			api.setData(page);
		} catch (Exception e) {
			api.error("记载用户分页数据失败");
			e.printStackTrace();
		}
		return api;
	}

}
