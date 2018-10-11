package com.yanger.mybatis.util;

import java.util.ArrayList;
import java.util.List;

import com.yanger.mybatis.paginator.Page;
import com.yanger.mybatis.paginator.PageParam;

public class Pages {
	
	private static final String WHERE = " where ";
	
	private static final String WHERE_ONE_EQ_ONE = " where 1=1 ";
	
	private Pages() {

	}

	public static <K, V> ResultPage<V> convert(PageParam pageParam, Page<K> fromList, Class<V> toClass) {

		List<V> toList = new ArrayList<>();

		for (int i = 0; i < fromList.size(); i++) {
			V to;
			try {
				to = toClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			}

			Beans.copy().from(fromList.get(i)).to(to);
			toList.add(to);
		}
		int pageNo = 0;
		int pageSize = 0;
		int totalCount = 0;
		
		if (pageParam.isContainsTotalCount()) {
			pageNo = pageParam.getPage();
			pageSize = pageParam.getLimit();
			totalCount = pageParam.getTotalCount();
		} else {
			Paginator paginator = fromList.getPaginator();
			if (paginator != null) {
				pageNo = paginator.getPage();
				pageSize = paginator.getLimit();
				totalCount = paginator.getTotalCount();
			}
		}

		return new ResultPage<>(pageNo, pageSize, totalCount, toList);
	}
	public static String genQueryString(String queryStringIn) {
		String queryString = queryStringIn.replaceAll("\\s+", " ");
		String powerHql = " and Valid_Status='0' ";
		if ("".equals(powerHql)) {
			return queryString;
		}
		queryString = queryString.replaceAll("\\s+", " ");
		if (queryString.toLowerCase().indexOf(" group by ") != -1) {
			int orderIndex = queryString.toLowerCase().indexOf("group by");
			if (queryString.toLowerCase().indexOf(WHERE) != -1) {
				queryString = queryString.substring(0, orderIndex) + powerHql
						+ queryString.substring(orderIndex, queryString.length());
			} else {
				queryString = queryString.substring(0, orderIndex) + WHERE_ONE_EQ_ONE + powerHql
						+ queryString.substring(orderIndex, queryString.length());
			}
			return queryString;
		}
		if (queryString.toLowerCase().indexOf(" order by ") != -1) {
			int orderIndex = queryString.toLowerCase().indexOf("order by");
			if (queryString.toLowerCase().indexOf(WHERE) != -1) {
				queryString = queryString.substring(0, orderIndex) + powerHql
						+ queryString.substring(orderIndex, queryString.length());
			} else {
				queryString = queryString.substring(0, orderIndex) + WHERE_ONE_EQ_ONE + powerHql
						+ queryString.substring(orderIndex, queryString.length());
			}
			return queryString;
		}
		if(queryString.toLowerCase().indexOf(" limit ") != -1){
			int limitIndex = queryString.toLowerCase().indexOf("limit");
			if (queryString.toLowerCase().indexOf(WHERE) != -1) {
				queryString = queryString.substring(0, limitIndex) + powerHql
						+ queryString.substring(limitIndex, queryString.length());
			} else {
				queryString = queryString.substring(0, limitIndex) + WHERE_ONE_EQ_ONE + powerHql
						+ queryString.substring(limitIndex, queryString.length());
			}
			return queryString;
		}
		if (queryString.toLowerCase().indexOf(WHERE) != -1) {
			return queryString + powerHql;
		} 
	
		else {
			return queryString + WHERE_ONE_EQ_ONE + powerHql;
		}
	}
}
