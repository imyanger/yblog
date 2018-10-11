package com.yanger.mybatis.dialect;

import org.apache.ibatis.mapping.MappedStatement;

import com.yanger.mybatis.paginator.PageParam;

/*
 * 支持Informix(11版本及以上)
 */
public class InformixDialect extends Dialect {

	public InformixDialect(MappedStatement mappedStatement, Object parameterObject,PageParam pageParam) {
        super(mappedStatement, parameterObject, pageParam);
	}

	@Override
	protected String getLimitString(String sql, String offsetName, int offset, String limitName, int limit) {
		StringBuilder buffer = new StringBuilder(sql.length() + 20).append(sql);
		if (offset > 0) {
			buffer.append(" skip ? first ?");
			setPageParameter(offsetName, offset, Integer.class);
			setPageParameter(limitName, limit, Integer.class);
		} else {
			buffer.append(" first ?");
			setPageParameter(limitName, limit, Integer.class);
		}
		return buffer.toString();
	}

}
