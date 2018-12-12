package com.yanger.mybatis.paginator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

/**
 * @description 分页信息
 * @author 杨号
 * @date 2018年9月14日
 */
public class PageParam extends RowBounds implements Serializable {

	private static final long serialVersionUID = 1L;

	private int totalCount;

	public static final int NO_PAGE = 1;

	/** 页号 */
	protected int page = NO_PAGE;

	/** 分页大小 */
	protected int limit = NO_ROW_LIMIT;

	/** 分页排序信息 */
	private List<Order> orders = new ArrayList<>();

	/** 结果集是否包含TotalCount */
	private boolean containsTotalCount;

	public PageParam() {
		containsTotalCount = false;
	}

	public PageParam(RowBounds rowBounds) {
		if (rowBounds instanceof PageParam) {
			PageParam pageBounds = (PageParam) rowBounds;
			this.page = pageBounds.page;
			this.limit = pageBounds.limit;
			this.orders = pageBounds.orders;
			this.containsTotalCount = pageBounds.containsTotalCount;
		} else {
			this.page = (rowBounds.getOffset() / rowBounds.getLimit()) + 1;
			this.limit = rowBounds.getLimit();
		}

	}

	/**
	 * @description Query TOP N, default containsTotalCount = false
	 * @param limit-限制条数
	 */
	public PageParam(int limit) {
		this.limit = limit;
		this.containsTotalCount = false;
	}

	public PageParam(int page, int limit) {
		this(page, limit, new ArrayList<Order>(), true);
	}

	public PageParam(int page, int limit, boolean containsTotalCount) {
		this(page, limit, new ArrayList<Order>(), containsTotalCount);
	}

	/**
	 * @description Just sorting, default containsTotalCount = false
	 * @param orders-排序
	 */
	public PageParam(List<Order> orders) {
		this(NO_PAGE, NO_ROW_LIMIT, orders, false);
	}

	/**
	 * @description Just sorting, default containsTotalCount = false
	 * @param order-排序
	 */
	public PageParam(Order... order) {
		this(NO_PAGE, NO_ROW_LIMIT, order);
		this.containsTotalCount = false;
	}

	public PageParam(int page, int limit, Order... order) {
		this(page, limit, Arrays.asList(order), true);
	}

	public PageParam(int page, int limit, List<Order> orders) {
		this(page, limit, orders, true);
	}

	public PageParam(int page, int limit, List<Order> orders, boolean containsTotalCount) {
		this.page = page;
		this.limit = limit;
		this.orders = orders;
		this.containsTotalCount = containsTotalCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public boolean isContainsTotalCount() {
		return containsTotalCount;
	}

	public void setContainsTotalCount(boolean containsTotalCount) {
		this.containsTotalCount = containsTotalCount;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public int getOffset() {
		if (page >= 1) {
			return (page - 1) * limit;
		}
		return 0;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("PageBounds{");
		sb.append("page=").append(page);
		sb.append(", limit=").append(limit);
		sb.append(", orders=").append(orders);
		sb.append(", totalCount=").append(totalCount);
		sb.append(", containsTotalCount=").append(containsTotalCount);
		sb.append('}');
		return sb.toString();
	}

}
