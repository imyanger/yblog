package com.yanger.mybatis.dialect;

import org.apache.ibatis.mapping.MappedStatement;

import com.yanger.mybatis.paginator.PageParam;

/**
 * Dialect for HSQLDB
 */
public class HSQLDialect extends Dialect {

	public HSQLDialect(MappedStatement mappedStatement, Object parameterObject, PageParam pageParam) {
		super(mappedStatement, parameterObject, pageParam);
	}

	@Override
	protected String getLimitString(String sql, String offsetName, int offset, String limitName, int limit) {
		boolean hasOffset = offset > 0;
		return new StringBuilder(sql.length() + 10).append(sql).insert(sql.toLowerCase().indexOf("select") + 6,
				hasOffset ? " limit " + offset + " " + limit : " top " + limit).toString();
	}

}
