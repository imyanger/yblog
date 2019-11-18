package com.yanger.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 防止XSS sql注入攻击
 * @time 2018年3月8日 下午9:12:07
 */
@Order(1)
@Configuration
@WebFilter(filterName = "XSSFilter",urlPatterns = "/*")
public class XSSFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 使得流可以重复读取
		RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
		chain.doFilter(new XSSRequestWrapper(requestWrapper), response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
