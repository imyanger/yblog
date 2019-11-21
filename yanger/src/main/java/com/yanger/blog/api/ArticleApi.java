package com.yanger.blog.api;

import java.io.File;
import java.io.IOException;

import com.yanger.blog.service.KindService;
import com.yanger.common.annotation.Token;
import com.yanger.common.util.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.yanger.blog.service.ArticleService;
import com.yanger.blog.vo.ArticleVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.common.annotation.Operate;
import com.yanger.common.config.ServerConfig;
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

	@Autowired
	private KindService kindService;
	
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
			kindService.exeDealSummary();
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
			} catch (IOException e) {
				api.error("保存文件报错");
				log.error("保存文件报错", e);
			}
		}
		return api;
	}

	/**
	 * @description 更新文章状态
	 * @author yanger
	 * @date 2019/11/21
	 * @param id
	 * @param state
	 * @return com.yanger.common.vo.ApiResponse<java.lang.String>
	 */
	@PutMapping("state/{id}/{state}")
	@ApiOperation(value = "更新文章状态state", notes = "")
	public ApiResponse<String> state(@PathVariable("id") int id, @PathVariable("state") String state) {
		ApiResponse<String> api = new ApiResponse<>();
		try {
			articleService.updateState(id, state);
		} catch (Exception e) {
			api.error("更新文章状态异常");
			log.error("更新文章状态异常", e);
		}
		return api;
	}

	/**
	 * @description 根据id删除文章
	 * @author YangHao
	 * @date 2018年11月29日-下午10:02:38
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id删除文章", notes = "")
	@DeleteMapping("/{id}")
	public ApiResponse<String> delArticle(@PathVariable Integer id) {
		ApiResponse<String> api = new ApiResponse<>();
		try {
			articleService.delArticle(id);
			kindService.exeDealSummary();
		} catch (Exception e) {
			api.error("删除文章失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 根据id查询文章
	 * @author YangHao
	 * @date 2018年11月29日-下午10:02:38
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询文章", notes = "")
	@GetMapping("/{id}")
	public ApiResponse<ArticleVo> getArticle(@PathVariable Integer id) {
		ApiResponse<ArticleVo> api = new ApiResponse<>();
		try {
			ArticleVo articleVo = articleService.getArticle(id);
			api.setData(articleVo);
		} catch (Exception e) {
			api.error("加载文章数据失败");
			e.printStackTrace();
		}
		return api;
	}

}
