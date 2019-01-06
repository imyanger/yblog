package com.yanger.common.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yanger.ueditor.ActionEnter;

@RestController
// @CrossOrigin
@RequestMapping("/ueditor")
public class UeditorApi {

	/**
	 * @description ueditor插件加载图片
	 * @author YangHao
	 * @time 2018年12月12日-下午10:29:48
	 * @param request
	 * @param response
	 */
	@RequestMapping("/img")
	@ResponseBody
	public void getConfigInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		try {
			String exec = new ActionEnter(request, rootPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}

}
