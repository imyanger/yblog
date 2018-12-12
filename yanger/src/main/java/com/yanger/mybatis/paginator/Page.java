package com.yanger.mybatis.paginator;

import java.util.ArrayList;
import java.util.Collection;

import com.yanger.mybatis.util.Paginator;

/**
 * @description 分页器信息
 * @author 杨号
 * @date 2018年9月14日
 */
public class Page<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;

	private Paginator paginator;

	/**
	 * @description 得到分页器，通过Paginator可以得到总页数等值
	 * @return 分页器
	 */
	public Paginator getPaginator() {
		return paginator;
	}

	public Page() {
	}

	public Page(Collection<? extends E> c) {
		super(c);
	}

	public Page(Collection<? extends E> c, Paginator p) {
		super(c);
		this.paginator = p;
	}

	public Page(Paginator p) {
		this.paginator = p;
	}

	public int getPageSize() {
		if (paginator != null) {
			return paginator.getLimit();
		}
		return 0;
	}

	public int getPageNo() {
		if (paginator != null) {
			return paginator.getPage();
		}
		return 0;
	}

	public int getTotalCount() {
		if (paginator != null) {
			return paginator.getTotalCount();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Page)) {
			return false;
		}
		Page<E> fobj = (Page<E>) obj;
		if (paginator != null) {
			return paginator.equals(fobj.getPaginator());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (paginator != null) {
			return getPaginator().hashCode();
		} else {
			return super.hashCode();
		}
	}

}
