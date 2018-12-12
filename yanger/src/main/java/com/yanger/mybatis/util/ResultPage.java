package com.yanger.mybatis.util;

import java.io.Serializable;
import java.util.List;

public class ResultPage<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long pageNo;
	
	private long pageSize;
	
	private long totalCount;
	
	private List<T> data;

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	@SuppressWarnings("rawtypes")
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ResultPage)) {
			return false;
		}
		ResultPage<?> other = (ResultPage) o;
		if (!other.canEqual(this)) {
			return false;
		}
		if (getPageNo() != other.getPageNo()) {
			return false;
		}
		if (getPageSize() != other.getPageSize()) {
			return false;
		}
		if (getTotalCount() != other.getTotalCount()) {
			return false;
		}
		Object this$data = getData();
		Object other$data = other.getData();
		return this$data == null ? other$data == null : this$data.equals(other$data);
	}

	protected boolean canEqual(Object other) {
		return other instanceof ResultPage;
	}

	public int hashCode() {
		int result = 1;
		long $pageNo = getPageNo();
		result = result * 59 + (int) ($pageNo >>> 32 ^ $pageNo);
		long $perPage = getPageSize();
		result = result * 59 + (int) ($perPage >>> 32 ^ $perPage);
		long $totalCount = getTotalCount();
		result = result * 59 + (int) ($totalCount >>> 32 ^ $totalCount);
		Object $data = getData();
		result = result * 59 + ($data == null ? 43 : $data.hashCode());
		return result;
	}

	public String toString() {
		return "ResultPage(pageNo=" + getPageNo() + ", perPage=" + getPageSize() + ", totalCount=" + getTotalCount()
				+ ", data=" + getData() + ")";
	}

	public long getPageNo() {
		return pageNo;
	}

	public long getPageSize() {
		return pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public List<T> getData() {
		return data;
	}

	public ResultPage(long pageNo, long perPage, long totalCount, List<T> data) {
		this.pageNo = pageNo;
		this.pageSize = perPage;
		this.totalCount = totalCount;
		this.data = data;
	}

	public ResultPage() {
	}
	
}