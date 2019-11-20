package com.yanger.blog.api;

import java.io.File;
import java.io.IOException;

import com.yanger.common.annotation.Token;
import com.yanger.common.util.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yanger.blog.service.ArticleService;
import com.yanger.blog.vo.ArticleVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.common.annotation.Operate;
import com.yanger.common.config.ServerConfig;
import com.yanger.blog.util.BolgConstant;
import com.yanger.common.vo.ApiResponse;
import com.yanger.mybatis.util.ResultPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api
@Token
@Slf4j
@RestController
@RequestMapping("/art")
public class ArticleApi {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ServerConfig serverConfig;
	
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
	@Operate("新增文章")
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
	
	/**
	 * @description 文件上传接口
	 * @author YangHao
	 * @time 2018年12月12日-下午10:17:53
	 * @param file
	 * @return
	 */
	@RequestMapping("file")
	@ApiOperation(value = "图片上传接口", notes = "")
	@ResponseBody
	public ApiResponse<String> fileUpload(@RequestParam("fileData") MultipartFile file) {
		ApiResponse<String> api = new ApiResponse<>();
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			// 文件名带上时间戳
			fileName = System.currentTimeMillis() + fileName;
			int size = (int) file.getSize();
			// 文件根路径
			String path = CommonConstant.FILE_PATH + "/md";
			File dest = new File(path + "/" + fileName);
			log.info("上传文件：{}---路径：{}--->大小：{}", fileName, path, size);
			// 判断文件父目录是否存在
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdir();
			}
			try {
				// 保存文件
				file.transferTo(dest);
				String sysUrl = serverConfig.getUrl();
				StringBuilder imgPath = new StringBuilder(sysUrl)
						.append("/file/getImg?path=")
						.append(path.replace(CommonConstant.FILE_PATH, ""))
						.append("/")
						.append(fileName);
				api.setData(imgPath.toString());
			} catch (IllegalStateException e) {
				api.error("保存文件报错");
				log.error("保存文件报错", e);
				e.printStackTrace();
			} catch (IOException e) {
				api.error("保存文件报错");
				log.error("保存文件报错", e);
				e.printStackTrace();
			}
		}
		return api;
	}
	
}
