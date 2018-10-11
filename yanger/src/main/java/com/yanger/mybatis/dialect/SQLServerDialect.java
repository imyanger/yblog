package com.yanger.mybatis.dialect;

import org.apache.ibatis.mapping.MappedStatement;

import com.yanger.mybatis.paginator.PageParam;

public class SQLServerDialect extends Dialect{

    public SQLServerDialect(MappedStatement mappedStatement, Object parameterObject, PageParam pageParam) {
        super(mappedStatement, parameterObject, pageParam);
    }


    /**
	 * Add a LIMIT clause to the given SQL SELECT
	 *
	 * The LIMIT SQL will look like:
	 *
	 * WITH query AS
	 *      (SELECT TOP 100 percent ROW_NUMBER() OVER (ORDER BY CURRENT_TIMESTAMP) as __row_number__, * from table_name)
	 * SELECT *
	 * FROM query
	 * WHERE __row_number__ BETWEEN :offset and :lastRows
	 * ORDER BY __row_number__
	 * 
	 * @param sql The SQL statement to base the limit query off of.
	 * @param offset         Offset of the first row to be returned by the query (zero-based)
	 * @param limit           Maximum number of rows to be returned by the query
	 * @return A new SQL statement with the LIMIT clause applied.
	 */
    @Override
    protected String getLimitString(String sql, String offsetName,int offset, String limitName, int limit) {
		StringBuilder pagingBuilder = new StringBuilder();
		String orderby = getOrderByPart(sql);
		String distinctStr = "";

		String loweredString = sql.toLowerCase();
		String sqlPartString = sql;
		if (loweredString.trim().startsWith("select")) {
			int index = 6;
			if (loweredString.startsWith("select distinct")) {
				distinctStr = "DISTINCT ";
				index = 15;
			}
			sqlPartString = sqlPartString.substring(index);
		}
		pagingBuilder.append(sqlPartString);

		// if no ORDER BY is specified use fake ORDER BY field to avoid errors
		if (orderby == null || orderby.length() == 0) {
			orderby = "ORDER BY CURRENT_TIMESTAMP";
		}

		StringBuilder result = new StringBuilder();
		result.append("WITH query AS (SELECT ")
				.append(distinctStr)
				.append("TOP 100 PERCENT ")
				.append(" ROW_NUMBER() OVER (")
				.append(orderby)
				.append(") as __row_number__, ")
				.append(pagingBuilder)
				.append(") SELECT * FROM query WHERE __row_number__ > ? AND __row_number__ <= ?")
				.append(" ORDER BY __row_number__");
        setPageParameter(offsetName,offset,Integer.class);
        setPageParameter("__offsetEnd",offset+limit,Integer.class);
		return result.toString();
	}

	static String getOrderByPart(String sql) {
		String loweredString = sql.toLowerCase();
		int orderByIndex = loweredString.indexOf("order by");
		if (orderByIndex != -1) {
			// if we find a new "order by" then we need to ignore
			// the previous one since it was probably used for a subquery
			return sql.substring(orderByIndex);
		} else {
			return "";
		}
	}
}
