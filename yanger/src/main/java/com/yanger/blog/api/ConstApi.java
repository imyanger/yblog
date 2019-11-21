package com.yanger.blog.api;

import java.util.List;

import com.yanger.blog.cache.CacheRunner;
import com.yanger.blog.po.ArticleType;
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
import com.yanger.common.annotation.Token;
import com.yanger.blog.util.BlogConstant;
import com.yanger.common.vo.ApiResponse;
import com.yanger.mybatis.util.ResultPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@Token
@RestController
@RequestMapping("/const")
public class ConstApi {

	@Autowired
	private ConstService constService;

	@Autowired
	private CacheRunner cacheRunner;
	
	/**
	 * @description 获取文章模块数据
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "获取文章模块数据失败", notes = "")
	@GetMapping("art/module")
	public ApiResponse<List<ConstVo>> artModule() {
		ApiResponse<List<ConstVo>> api = new ApiResponse<>();
		try {
			List<ConstVo> constVos = constService.getConstList(BlogConstant.ARTICLE_MODULE_UPPER_CODE);
			api.setData(constVos);
		} catch (Exception e) {
			api.error("获取文章模块数据失败");
			e.printStackTrace();
		}
		return api;
	}

    /**
     * @description 获取文章模块、类型、分类数据
     * @author YangHao
     * @date 2018年9月6日-下午11:07:41
     * @return
     */
    @ApiOperation(value = "获取文章模块、类型、分类数据", notes = "")
    @GetMapping("art/mtcs")
    public ApiResponse<List<ConstVo>> mtcs() {
        ApiResponse<List<ConstVo>> api = new ApiResponse<>();
        try {
            List<ConstVo> constVos = constService.getMtcs();
            api.setData(constVos);
        } catch (Exception e) {
            api.error("获取文章模块、类型、分类数据");
            e.printStackTrace();
        }
        return api;
    }

	/**
	 * @description 根据upperCode获取常量
	 * @author yanger
	 * @date 2019/11/19
	 * @param type
	 * @return com.yanger.common.vo.ApiResponse<java.util.List<com.yanger.blog.vo.ConstVo>>
	 */
	@ApiOperation(value = "获取文章模块数据失败", notes = "")
	@GetMapping("list/{type}")
	public ApiResponse<List<ConstVo>> constsByType(@PathVariable("type") String type) {
		ApiResponse<List<ConstVo>> api = new ApiResponse<>();
		try {
			List<ConstVo> constVos = constService.getConstList(type);
			api.setData(constVos);
		} catch (Exception e) {
			api.error("获取文章模块数据失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 查询常量表分页数据
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "查询常量表分页数据", notes = "")
	@PostMapping("/list")
	public ApiResponse<ResultPage<ConstVo>> constList(@RequestBody PageQueryVo pageQueryVo) {
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
	 * @description 查询文章类型分页数据
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "查询文章类型分页数据", notes = "")
	@PostMapping("/art/list")
	public ApiResponse<ResultPage<ArticleType>> articleList(@RequestBody PageQueryVo pageQueryVo) {
		ApiResponse<ResultPage<ArticleType>> api = new ApiResponse<>();
		try {
			ResultPage<ArticleType> page = constService.getArtTypePage(pageQueryVo);
			api.setData(page);
		} catch (Exception e) {
			api.error("查询文章类型分页数据失败");
			e.printStackTrace();
		}
		return api;
	}


	/**
	 * @description 新增常量
	 * @author YangHao
	 * @date 2018年11月29日-下午10:02:38
	 * @param constVo
	 * @return
	 */
	@ApiOperation(value = "新增常量", notes = "")
	@PutMapping("/add")
	public ApiResponse<String> addConst(@RequestBody ConstVo constVo) {
		ApiResponse<String> api = new ApiResponse<>();
		try {
			constService.saveOrUpdateConst(constVo);
			cacheRunner.reloadConstCache();
		} catch (Exception e) {
			api.error("新增常量处理失败");
			e.printStackTrace();
		}
		return api;
	}
	
	/**
	 * @description 新增常量
	 * @author YangHao
	 * @date 2018年11月29日-下午10:02:38
	 * @param constVos
	 * @return
	 */
	@ApiOperation(value = "批量新增新增常量", notes = "")
	@PutMapping("/adds")
	public ApiResponse<String> addConsts(@RequestBody List<ConstVo> constVos) {
		ApiResponse<String> api = new ApiResponse<>();
		try {
			constService.saveArtClassifyConst(constVos);
			cacheRunner.reloadConstCache();
		} catch (Exception e) {
			api.error("批量新增新增常量失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 根据id删除常量
	 * @author YangHao
	 * @date 2018年11月29日-下午10:02:38
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id删除常量", notes = "")
	@DeleteMapping("/{id}")
	public ApiResponse<String> delConst(@PathVariable Integer id) {
		ApiResponse<String> api = new ApiResponse<>();
		try {
			constService.delConst(id);
			cacheRunner.reloadConstCache();
		} catch (Exception e) {
			api.error("删除常量失败");
			e.printStackTrace();
		}
		return api;
	}

}
