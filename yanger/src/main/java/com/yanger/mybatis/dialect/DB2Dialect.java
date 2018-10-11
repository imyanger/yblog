package com.yanger.mybatis.dialect;

import org.apache.ibatis.mapping.MappedStatement;

import com.yanger.mybatis.paginator.PageParam;


public class DB2Dialect extends Dialect{

    public DB2Dialect(MappedStatement mappedStatement, Object parameterObject, PageParam pageParam) {
        super(mappedStatement, parameterObject, pageParam);
    }
	
	private static String getRowNumber(String sql) {
		StringBuilder rownumber = new StringBuilder(50)
			.append("rownumber() over(");

		int orderByIndex = sql.toLowerCase().indexOf("order by");

		if ( orderByIndex>0 && !hasDistinct(sql) ) {
			rownumber.append( sql.substring(orderByIndex) );
		}

		rownumber.append(") as rownumber_,");

		return rownumber.toString();
	}
	
	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct")>=0;
	}

	@Override
    protected String getLimitString(String sql, String offsetName,int offset, String limitName, int limit) {
		int startOfSelect = sql.toLowerCase().indexOf("select");

		StringBuilder pagingSelect = new StringBuilder( sql.length()+100 )
					.append( sql.substring(0, startOfSelect) ) //add the comment
					.append("select * from ( select ") //nest the main query in an outer select
					.append( getRowNumber(sql) ); //add the rownnumber bit into the outer query select list

		if ( hasDistinct(sql) ) {
			pagingSelect.append(" row_.* from ( ") //add another (inner) nested select
				.append( sql.substring(startOfSelect) ) //add the main query
				.append(" ) as row_"); //close off the inner nested select
		}
		else {
			pagingSelect.append( sql.substring( startOfSelect + 6 ) ); //add the main query
		}

		pagingSelect.append(" ) as temp_ where rownumber_ ");

		//add the restriction to the outer select
		if (offset > 0) {
			pagingSelect.append("between ?+1 and ?");
            setPageParameter(offsetName,offset,Integer.class);
            setPageParameter("__offsetEnd",offset+limit,Integer.class);
		}
		else {
			pagingSelect.append("<= ?");
            setPageParameter(limitName,limit,Integer.class);
		}

		return pagingSelect.toString();
	}
}
