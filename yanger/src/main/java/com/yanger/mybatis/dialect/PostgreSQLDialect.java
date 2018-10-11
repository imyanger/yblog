package com.yanger.mybatis.dialect;

import org.apache.ibatis.mapping.MappedStatement;

import com.yanger.mybatis.paginator.PageParam;

public class PostgreSQLDialect extends Dialect{

    public PostgreSQLDialect(MappedStatement mappedStatement, Object parameterObject, PageParam pageParam) {
        super(mappedStatement, parameterObject, pageParam);
    }

    @Override
    protected String getLimitString(String sql, String offsetName,int offset, String limitName, int limit) {
        StringBuilder buffer = new StringBuilder( sql.length()+20 ).append(sql);
        if(offset > 0){
            buffer.append(" limit ? offset ?");
            setPageParameter(limitName, limit, Integer.class);
            setPageParameter(offsetName, offset, Integer.class);

        }else{
            buffer.append(" limit ?");
            setPageParameter(limitName, limit, Integer.class);
        }
		return buffer.toString();
	}
}
