package com.yanger.mybatis.dialect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.SimpleTypeRegistry;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.yanger.mybatis.paginator.Order;
import com.yanger.mybatis.paginator.PageParam;
import com.yanger.mybatis.util.SQLHelper;


/**
 * 类似hibernate的Dialect,只精简出分页部分 
 */
public class Dialect {
	
    protected TypeHandlerRegistry typeHandlerRegistry;
    
    protected MappedStatement mappedStatement;
    
    protected PageParam pageParam;
    
    protected Object parameterObject; 
    
    protected List<ParameterMapping> parameterMappings;
    
    protected Map<String, Object> pageParameters = new HashMap<>();

    private String pageSQL;
    
    private String countSQL;

    public Dialect(MappedStatement mappedStatement, Object parameterObject, PageParam pageParam){
        this.mappedStatement = mappedStatement;
        this.parameterObject = parameterObject;
        this.pageParam = pageParam;
        this.typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();

        init();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected void init(){ 
    	BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        parameterMappings = new ArrayList<>(boundSql.getParameterMappings());
        if(parameterObject instanceof Map){
            pageParameters.putAll((Map)parameterObject);
        }else if( parameterObject != null){
            Class<?> cls = parameterObject.getClass();
            if(cls.isPrimitive() || cls.isArray() ||
                    SimpleTypeRegistry.isSimpleType(cls) ||
                    Enum.class.isAssignableFrom(cls) ||
                    Collection.class.isAssignableFrom(cls)){
                for (ParameterMapping parameterMapping : parameterMappings) {
                    pageParameters.put(parameterMapping.getProperty(),parameterObject);
                }
            }else{
                MetaObject metaObject = mappedStatement.getConfiguration().newMetaObject(parameterObject);
                ObjectWrapper wrapper = metaObject.getObjectWrapper();
                for (ParameterMapping parameterMapping : parameterMappings) {
                    PropertyTokenizer prop = new PropertyTokenizer(parameterMapping.getProperty());
                    pageParameters.put(parameterMapping.getProperty(),wrapper.get(prop));
                }
            } 
        }
        initSQL(boundSql.getSql().trim());
    }
    private void initSQL(String inSQL){ 
        String sql = inSQL;
        if(sql.endsWith(";")) {
        	sql = sql.substring(0,sql.length()-1); 
        }
        pageSQL = sql;
        if(pageParam.getOrders() != null && !pageParam.getOrders().isEmpty()){
            pageSQL = getSortString(sql, pageParam.getOrders());
        }
        if(pageParam.getOffset() != RowBounds.NO_ROW_OFFSET
                || pageParam.getLimit() != RowBounds.NO_ROW_LIMIT){
            pageSQL = getLimitString(pageSQL, "__offset", pageParam.getOffset(), "__limit",pageParam.getLimit());
        }

        countSQL = getCountString(sql);
    }

    public List<ParameterMapping> getParameterMappings(){
        return parameterMappings;
    }

    public Object getParameterObject(){
        return pageParameters;
    }


    public String getPageSQL(){
        return pageSQL;
    }

    protected void setPageParameter(String name, Object value, Class<?> type){
        ParameterMapping parameterMapping = new ParameterMapping.Builder(mappedStatement.getConfiguration(), name, type).build();
        parameterMappings.add(parameterMapping);
        pageParameters.put(name, value);
    }


    public String getCountSQL(){
        return countSQL;
    }

    
    /**
     * 将sql变成分页sql语句
     */
    protected String getLimitString(String sql, String offsetName,int offset, String limitName, int limit) {
        throw new UnsupportedOperationException("paged queries not supported");
    }

    /**
     * 将sql转换为总记录数SQL
     * @param sql SQL语句
     * @return 总记录数的sql
     */
    protected String getCountString(String sql){
        return "select count(1) from (" + SQLHelper.removeOrders(sql) + ") tmp_count";
    }

    /**
     * 将sql转换为带排序的SQL
     * @param sql SQL语句
     * @return 总记录数的sql
     */
    protected String getSortString(String sql, List<Order> orders){
        if(orders == null || orders.isEmpty()){
            return sql;
        }
        List<ResultMap> resultMaps = mappedStatement.getResultMaps();
        Map<String,String> propertyColumn =null;
        if(resultMaps != null && resultMaps.get(0) != null){
            propertyColumn = new HashMap<String,String>();
            List<ResultMapping> resultMappings = resultMaps.get(0).getResultMappings();
            for(ResultMapping resultMap :resultMappings){
                propertyColumn.put(resultMap.getProperty(),resultMap.getColumn());
            }
        }
        StringBuilder buffer = new StringBuilder("select * from (").append(sql).append(") temp_order order by ");
        for(Order order : orders){
            if(order != null){
                if(propertyColumn !=null && propertyColumn.containsKey(order.getProperty())){
                    order.setProperty(propertyColumn.get(order.getProperty()));
                }
                buffer.append(order.toString())
                        .append(", ");
            }

        }
        buffer.delete(buffer.length()-2, buffer.length());
        return buffer.toString();
    }
}
