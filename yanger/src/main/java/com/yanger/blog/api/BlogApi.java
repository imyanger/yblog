package com.yanger.blog.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yanger.blog.service.BlogService;
import com.yanger.blog.vo.ArticleVo;
import com.yanger.blog.vo.BlogUserVo;
import com.yanger.blog.vo.BoardDataVo;
import com.yanger.blog.vo.EssayDataVo;
import com.yanger.blog.vo.HomeDataVo;
import com.yanger.blog.vo.LeavingMsgVo;
import com.yanger.blog.vo.PageQueryVo;
import com.yanger.blog.vo.StudyDataVo;
import com.yanger.blog.vo.ViewDataVo;
import com.yanger.common.annotation.Token;
import com.yanger.common.config.ServerConfig;
import com.yanger.common.util.JwtUtils;
import com.yanger.common.util.RedisMagger;
import com.yanger.common.vo.ApiResponse;
import com.yanger.common.vo.TokenMsg;
import com.yanger.mybatis.util.ResultPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api
// @Token
@RestController
@RequestMapping("/blog")
@Slf4j
public class BlogApi {

	@Autowired
	private BlogService blogService;

	@Autowired
	private RedisMagger RedisMagger;
	
	@Autowired
	private ServerConfig serverConfig;

	/**
	 * @description 测试redis
	 * @author YangHao
	 * @time 2018年12月12日-下午10:06:27
	 */
	@GetMapping("t")
	public void t() {
		if (RedisMagger.hasKey("r")) {
			String v = (String) RedisMagger.get("r");
			System.out.println(v);
		} else {
			RedisMagger.set("r", "123测试缓存");

		}
	}

	@GetMapping("log")
	public void log() {
		log.info("这是info信息2");
		log.error("这是error信息");
	}
	
	/**
	 * @description 博客首页数据初始化
	 * @author YangHao
	 * @date 2018年9月3日-下午10:51:50
	 * @return
	 */
	@ApiOperation(value = "博客首页数据初始化", notes = "")
	@GetMapping("/homeInit")
	public ApiResponse<HomeDataVo> homeInit() {
		ApiResponse<HomeDataVo> api = new ApiResponse<>();
		try {
			HomeDataVo homeData = blogService.getHomeData();
			homeData.setServerPath(serverConfig.getUrl());
			api.setData(homeData);
		} catch (Exception e) {
			api.error("加载博客首页数据失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 学习笔记页面数据初始化
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "学习笔记页面数据初始化", notes = "")
	@GetMapping("/studyInit")
	public ApiResponse<StudyDataVo> studyInit() {
		ApiResponse<StudyDataVo> api = new ApiResponse<>();
		try {
			StudyDataVo studyDataVo = blogService.getStudyData();
			studyDataVo.setServerPath(serverConfig.getUrl());
			api.setData(studyDataVo);
		} catch (Exception e) {
			api.error("加载学习笔记数据失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 学习笔记页面数据初始化
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "心情随笔页面数据初始化", notes = "")
	@GetMapping("/essayInit")
	public ApiResponse<EssayDataVo> essayInit() {
		ApiResponse<EssayDataVo> api = new ApiResponse<>();
		try {
			EssayDataVo essayDataVo = blogService.getEssayData();
			essayDataVo.setServerPath(serverConfig.getUrl());
			api.setData(essayDataVo);
		} catch (Exception e) {
			api.error("加载心情随笔数据失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 查询文章分页数据
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "查询文章分页数据", notes = "")
	@PostMapping("/articlePage")
	public ApiResponse<ResultPage<ArticleVo>> articlePage(@RequestBody PageQueryVo pageQueryVo) {
		ApiResponse<ResultPage<ArticleVo>> api = new ApiResponse<>();
		try {
			ResultPage<ArticleVo> page = blogService.getPageData(pageQueryVo);
			api.setData(page);
		} catch (Exception e) {
			api.error("加载文章分页数据失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 用户注册
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "用户注册", notes = "")
	@PostMapping("/register")
	public ApiResponse<String> register(@RequestBody BlogUserVo blogUserVo) {
		ApiResponse<String> api = new ApiResponse<>();
		try {
			blogService.userRegister(blogUserVo);
			api.setData("注册成功");
		} catch (Exception e) {
			api.error("用户注册失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 用户登录
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "用户登录", notes = "")
	@PostMapping("/login")
	public ApiResponse<BlogUserVo> login(@RequestBody BlogUserVo blogUserVo) {
		ApiResponse<BlogUserVo> api = new ApiResponse<>();
		try {
			BlogUserVo user = blogService.userLogin(blogUserVo);
			if (user != null) {
				api.setData(user);
				// 添加token
				String token = JwtUtils.sign(new TokenMsg().setInfo(user));
				api.setToken(token);
			} else {
				api.error("输入的账号不存在或密码错误");
			}
		} catch (Exception e) {
			api.error("用户登录失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 校验用户名是否被使用
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "校验用户名是否被使用 ", notes = "")
	@GetMapping("/checkCode")
	public ApiResponse<String> checkCode(@RequestParam(value = "code") String code) {
		ApiResponse<String> api = new ApiResponse<>();
		try {
			Boolean exist = blogService.checkUserCode(code);
			if (exist) {
				api.error("已经被使用");
			}
		} catch (Exception e) {
			api.error("校验用户名失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 留言板页面数据初始化
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "留言板页面数据初始化", notes = "")
	@GetMapping("/boardInit")
	public ApiResponse<BoardDataVo> boardInit() {
		ApiResponse<BoardDataVo> api = new ApiResponse<>();
		try {
			BoardDataVo boardDataVo = blogService.getBoardData();
			api.setData(boardDataVo);
		} catch (Exception e) {
			api.error("加载留言板数据失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 查询留言分页数据
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "查询留言分页数据", notes = "")
	@PostMapping("/msgPage")
	public ApiResponse<ResultPage<LeavingMsgVo>> msgPage(@RequestBody PageQueryVo pageQueryVo) {
		ApiResponse<ResultPage<LeavingMsgVo>> api = new ApiResponse<>();
		try {
			ResultPage<LeavingMsgVo> page = blogService.findMsgPage(pageQueryVo.getPageNo(), 6);
			api.setData(page);
		} catch (Exception e) {
			api.error("加载留言分页数据失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 查询文章留言分页数据
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "查询文章留言分页数据", notes = "")
	@PostMapping("/artMsgPage")
	public ApiResponse<ResultPage<LeavingMsgVo>> artMsgPage(@RequestBody PageQueryVo pageQueryVo) {
		ApiResponse<ResultPage<LeavingMsgVo>> api = new ApiResponse<>();
		try {
			ResultPage<LeavingMsgVo> page = blogService.getArtMsgPageData(pageQueryVo);
			api.setData(page);
		} catch (Exception e) {
			api.error("加载文章留言分页数据失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 发表留言
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "发表留言", notes = "")
	@PostMapping("/leaveMsg")
	@Token
	public ApiResponse<ResultPage<LeavingMsgVo>> msgPage(@RequestBody LeavingMsgVo msgVo, HttpServletRequest request) {
		ApiResponse<ResultPage<LeavingMsgVo>> api = new ApiResponse<>();
		TokenMsg user = (TokenMsg) request.getAttribute("user");
		try {
			ResultPage<LeavingMsgVo> page = blogService.leaveMsg(user, msgVo);
			api.setData(page);
		} catch (Exception e) {
			api.error("加载留言分页数据失败");
			e.printStackTrace();
		}
		return api;
	}

	/**
	 * @description 文章浏览界面
	 * @author YangHao
	 * @date 2018年9月6日-下午11:07:41
	 * @return
	 */
	@ApiOperation(value = "文章浏览界面", notes = "")
	@GetMapping("/view")
	public ApiResponse<ViewDataVo> view(@RequestParam(value = "id") Integer id) {
		ApiResponse<ViewDataVo> api = new ApiResponse<>();
		try {
			ViewDataVo viewDataVo = blogService.view(id);
			api.setData(viewDataVo);
		} catch (Exception e) {
			api.error("文章浏览加载失败");
			e.printStackTrace();
		}
		return api;
	}

}
