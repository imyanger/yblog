package com.yanger.common.util;

import java.util.List;

import com.yanger.mybatis.paginator.Order;
import com.yanger.mybatis.paginator.PageParam;

/**
 * @description 分页组件工具类
 * @author 杨号
 * @date 2018年9月14日
 */
public class ParamUtils {

	/**
	 * @description 初始化PageParam
	 * @author YangHao
	 * @date 2018年9月3日-下午11:21:51
	 * @param page：当前页号
	 * @param size：数量
	 * @return
	 */
	public static PageParam getPageParam(int page, int size) {
		PageParam pageParam = new PageParam();
		pageParam.setPage(page);
		pageParam.setLimit(size);
		return pageParam;
	}

	/**
	 * @description 初始化PageParam
	 * @author YangHao
	 * @date 2018年9月3日-下午11:21:51
	 * @param page：当前页号
	 * @param size：数量
	 * @param orderCol：排序的列
	 * @return
	 */
	public static PageParam getAscPageParam(int page, int size, String orderCol) {
		PageParam pageParam = new PageParam(page, size);
		List<Order> orders = Order.formString(orderCol + ".asc");
		pageParam.setOrders(orders);
		return pageParam;
	}

	/**
	 * @description 初始化PageParam
	 * @author YangHao
	 * @date 2018年9月3日-下午11:21:51
	 * @param page：当前页号
	 * @param size：数量
	 * @param orderCol：排序的列
	 * @return
	 */
	public static PageParam getDescPageParam(int page, int size, String orderCol) {
		PageParam pageParam = new PageParam();
		pageParam.setPage(page);
		pageParam.setLimit(size);
		List<Order> orders = Order.formString(orderCol + ".desc");
		pageParam.setOrders(orders);
		return pageParam;
	}

	/**
	 * @description 初始化PageParam
	 * @author YangHao
	 * @date 2018年9月3日-下午11:21:51
	 * @param size：数量
	 * @return
	 */
	public static PageParam getPageParam(int size) {
		return getPageParam(PageParam.NO_PAGE, size);
	}

	/**
	 * @description 初始化PageParam
	 * @author YangHao
	 * @date 2018年9月3日-下午11:21:51
	 * @param size：数量
	 * @param orderCol：排序的列
	 * @return
	 */
	public static PageParam getAscPageParam(int size, String orderCol) {
		return getAscPageParam(PageParam.NO_PAGE, size, orderCol);
	}

	/**
	 * @description 初始化PageParam
	 * @author YangHao
	 * @date 2018年9月3日-下午11:21:51
	 * @param size：数量
	 * @param orderCol：排序的列
	 * @return
	 */
	public static PageParam getDescPageParam(int size, String orderCol) {
		return getDescPageParam(PageParam.NO_PAGE, size, orderCol);
	}

}
