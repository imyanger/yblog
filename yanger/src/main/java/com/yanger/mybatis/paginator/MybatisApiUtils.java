package com.yanger.mybatis.paginator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @description MyBatisApi的工具类
 * @author 杨号
 * @date 2018年9月14日
 */
public class MybatisApiUtils {

	/** 默认分页大小 */
	public static final int DEFAULT_PAGE_SIZE = 10;

	/** 最大分页大小 */
	public static final int MAX_PAGE_SIZE = 3000;

	public static PageParam getPageParam() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return getPageParam(request);
	}

	public static PageParam getPageParam(HttpServletRequest request) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		String myValue = (String) requestAttributes.getAttribute("pageNo", RequestAttributes.SCOPE_REQUEST);
		int inPageSize = 0;
		int inPageNo = 0;
		int inTotalCount = 0;
		inPageNo = getParameterValue(request, "_pageNo", 1);
		if (inPageNo < 1) {
			inPageNo = 1;
		}

		inPageSize = getParameterValue(request, "_pageSize", DEFAULT_PAGE_SIZE);
		if (inPageSize < 0) {
			inPageSize = DEFAULT_PAGE_SIZE;
		}
		if (inPageSize > MAX_PAGE_SIZE) {
			throw new IllegalArgumentException("pageSize exceeded maxPageSize[" + MAX_PAGE_SIZE + "]");
		}
		PageParam pageParam = new PageParam(inPageNo, inPageSize);

		List<Order> orders = getListOrders(request);
		if (orders != null) {
			pageParam.setOrders(orders);
		}

		inTotalCount = getParameterValue(request, "_totalCount", 0);
		if (inTotalCount > 0) {
			pageParam.setContainsTotalCount(false);
		}
		return pageParam;
	}

	public static List<Order> getListOrders(HttpServletRequest request) {
		Enumeration<String> parameters = request.getParameterNames();
		List<Order> orders = null;
		while (parameters.hasMoreElements()) {
			String parameterKey = parameters.nextElement();
			// 要进行排序，要排序的属性不能为空，为空时不在获取后面排序属性
			if (parameterKey.contains("orders")) {
				int initIndex = 0;
				StringBuffer currentCommon = new StringBuffer("orders[");
				int commonLength = currentCommon.length();
				String currentKey, currentProperty, currentDirection, currentExp;
				Order.Direction currentDir = null;

				while (initIndex != -1) {
					currentKey = currentCommon.append(initIndex).append("][property]").toString();
					// 要排序的属性
					currentProperty = request.getParameter(currentKey);
					if (currentProperty == null) {
						break;// 已经获取完所有要排序的信息
					} else if (initIndex == 0) {
						orders = new ArrayList<Order>();
					}
					// 要排序的方向
					currentKey = currentCommon.delete(commonLength, currentCommon.length()).append(initIndex)
							.append("][direction]").toString();
					currentDirection = request.getParameter(currentKey);
					if (currentDirection != null) {
						currentDir = Order.Direction.fromString(currentDirection);
					}

					// 属性的表达式
					currentKey = currentCommon.delete(commonLength, currentCommon.length()).append(initIndex++)
							.append("][orderExpr]").toString();
					currentExp = request.getParameter(currentKey);
					Order order = new Order(currentProperty, currentDir, currentExp);
					orders.add(order);
				}
			}
		}
		return orders;
	}

	public static int getParameterValue(HttpServletRequest request, String name, int def) {
		String str = request.getParameter(name);
		int value = def;
		if (str != null) {
			value = Integer.parseInt(str, 10);
		}
		return value;
	}

	public String getParameterValue(HttpServletRequest request, String name, String def) {
		String str = request.getParameter(name);
		String value = def;
		if (str != null) {
			value = str;
		}
		return value;
	}

}
