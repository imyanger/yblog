package com.yanger.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yanger.blog.service.facade.BlogService;
import com.yanger.blog.vo.HomeDataVo;
import com.yanger.common.vo.ApiResponse;

@RestController
@RequestMapping("/blog")
public class BlogApi {
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping(value = "/t")
	public ApiResponse<String> test(){
		System.out.println("1112");
		return new ApiResponse<>("23");
	}
	
	/**
	 * <p>Description: 博客首页数据初始化 </p>  
	 * @author YangHao  
	 * @date 2018年9月3日-下午10:51:50
	 * @return
	 */
	@RequestMapping(value = "/homeInit", method = RequestMethod.GET)
	public ApiResponse<HomeDataVo> homeInit(){
		ApiResponse<HomeDataVo> api = new ApiResponse<>();
		try {
			HomeDataVo homeData = blogService.getHomeData();
			api.setData(homeData);
		} catch (Exception e) {
			api.error("加载博客首页数据失败");
			e.printStackTrace();
		}
		return api;
		
	}
	
	public ApiResponse<HomeDataVo> homeInit12(){
		return null;
			
		}
	
	public ApiResponse<HomeDataVo> homeInit13(){
		return null;
		
	}
	
	public ApiResponse<HomeDataVo> homeInit15(){
		return null;
		
	}
	
	public ApiResponse<HomeDataVo> homeInit6(){
		return null;
		
	}

}
