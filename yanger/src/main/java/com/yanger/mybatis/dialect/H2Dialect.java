package com.yanger.mybatis.dialect;

import org.apache.ibatis.mapping.MappedStatement;

import com.yanger.mybatis.paginator.PageParam;

/**
 * A dialect compatible with the H2 database.
 */
public class H2Dialect extends Dialect {

	public H2Dialect(MappedStatement mappedStatement, Object parameterObject, PageParam pageParam) {
		super(mappedStatement, parameterObject, pageParam);
	}

	@Override
	protected String getLimitString(String sql, String offsetName, int offset, String limitName, int limit) {
		return new StringBuilder(sql.length() + 40).append(sql)
				.append((offset > 0) ? " limit " + limit + " offset " + offset : " limit " + limit).toString();
	}

}