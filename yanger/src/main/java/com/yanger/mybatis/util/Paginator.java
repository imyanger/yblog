package com.yanger.mybatis.util;

import java.io.Serializable;

/**
 * @description 分页器，根据page,limit,totalCount用于页面上分页显示多项内容，计算页码和当前页的偏移量，方便页面分页使用.
 *
 */
public class Paginator implements Serializable {

	private static final long serialVersionUID = -2429864663690465105L;

	private static final int DEFAULT_SLIDERS_COUNT = 10;

	/**
	 * 分页大小
	 */
	private int limit;
	/**
	 * 页数
	 */
	private int page;
	/**
	 * 总记录数
	 */
	private int totalCount;

	public Paginator(int page, int limit, int totalCount) {
		super();
		this.limit = limit;
		this.totalCount = totalCount;
		this.page = computePageNo(page);
	}

	/**
	 * @description 取得当前页号。
	 * @return 当前页号
	 */
	public int getPage() {
		return page;
	}

	public int getLimit() {
		return limit;
	}

	/**
	 * @description 取得总项数。
	 * @return 总项数
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @description 是否是首页（第一页），第一页页码为1
	 * @return 首页标识
	 */
	public boolean isFirstPage() {
		return page <= 1;
	}

	/**
	 * @description 是否是最后一页
	 * @return 末页标识
	 */
	public boolean isLastPage() {
		return page >= getTotalPages();
	}

	public int getPrePage() {
		if (isHasPrePage()) {
			return page - 1;
		} else {
			return page;
		}
	}

	public int getNextPage() {
		if (isHasNextPage()) {
			return page + 1;
		} else {
			return page;
		}
	}

	/**
	 * @description 判断指定页码是否被禁止，也就是说指定页码超出了范围或等于当前页码
	 * @param page-页码
	 * @return boolean 是否为禁止的页码
	 */
	public boolean isDisabledPage(int page) {
		return ((page < 1) || (page > getTotalPages()) || (page == this.page));
	}

	/**
	 * @description 是否有上一页
	 * @return 上一页标识
	 */
	public boolean isHasPrePage() {
		return (page - 1 >= 1);
	}

	/**
	 * @description 是否有下一页
	 * @return 下一页标识
	 */
	public boolean isHasNextPage() {
		return (page + 1 <= getTotalPages());
	}

	/**
	 * @description 开始行，可以用于oracle分页使用 (1-based)。
	 * @return 开始行
	 */
	public int getStartRow() {
		if (getLimit() <= 0 || totalCount <= 0)
			return 0;
		return page > 0 ? (page - 1) * getLimit() + 1 : 0;
	}

	/**
	 * @description 结束行，可以用于oracle分页使用 (1-based)。
	 * @return 结束行
	 */
	public int getEndRow() {
		return page > 0 ? Math.min(limit * page, getTotalCount()) : 0;
	}

	/**
	 * @description offset，计数从0开始，可以用于mysql分页使用(0-based)
	 * @return 偏移量
	 */
	public int getOffset() {
		return page > 0 ? (page - 1) * getLimit() : 0;
	}

	/**
	 * @description 得到 总页数
	 * @return 总页数
	 */
	public int getTotalPages() {
		if (totalCount <= 0) {
			return 0;
		}
		if (limit <= 0) {
			return 0;
		}

		int count = totalCount / limit;
		if (totalCount % limit > 0) {
			count++;
		}
		return count;
	}

	protected int computePageNo(int page) {
		return computePageNumber(page, limit, totalCount);
	}

	/**
	 * @description 页码滑动窗口，并将当前页尽可能地放在滑动窗口的中间部位。
	 * @return Slider
	 */
	public Integer[] getSlider() {
		return slider(DEFAULT_SLIDERS_COUNT);
	}

	/**
	 * @description 页码滑动窗口，并将当前页尽可能地放在滑动窗口的中间部位。 注意:不可以使用
	 *              getSlider(1)方法名称，因为在JSP中会与getSlider()方法冲突，报exception
	 * @param slidersCount-窗口数量
	 * @return 页码滑动窗口
	 */
	public Integer[] slider(int slidersCount) {
		return generateLinkPageNumbers(getPage(), (int) getTotalPages(), slidersCount);
	}

	private static int computeLastPageNumber(int totalItems, int pageSize) {
		if (pageSize <= 0) {
			return 1;
		}
		int result = totalItems % pageSize == 0 ? totalItems / pageSize : totalItems / pageSize + 1;
		if (result <= 1) {
			result = 1;
		}
		return result;
	}

	private static int computePageNumber(int page, int pageSize, int totalItems) {
		if (page <= 1) {
			return 1;
		}
		if (Integer.MAX_VALUE == page || page > computeLastPageNumber(totalItems, pageSize)) {
			// last page
			return computeLastPageNumber(totalItems, pageSize);
		}
		return page;
	}

	private static Integer[] generateLinkPageNumbers(int currentPageNumber, int lastPageNumber, int count) {
		int avg = count / 2;

		int startPageNumber = currentPageNumber - avg;
		if (startPageNumber <= 0) {
			startPageNumber = 1;
		}

		int endPageNumber = startPageNumber + count - 1;
		if (endPageNumber > lastPageNumber) {
			endPageNumber = lastPageNumber;
		}

		if (endPageNumber - startPageNumber < count) {
			startPageNumber = endPageNumber - count;
			if (startPageNumber <= 0) {
				startPageNumber = 1;
			}
		}

		java.util.List<Integer> result = new java.util.ArrayList<>();
		for (int i = startPageNumber; i <= endPageNumber; i++) {
			result.add(i);
		}
		return result.toArray(new Integer[result.size()]);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Paginator");
		sb.append("{page=").append(page);
		sb.append(", limit=").append(limit);
		sb.append(", totalCount=").append(totalCount);
		sb.append('}');
		return sb.toString();
	}

}
