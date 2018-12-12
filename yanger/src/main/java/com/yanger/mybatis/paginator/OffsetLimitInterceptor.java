package com.yanger.mybatis.paginator;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yanger.mybatis.dialect.Dialect;
import com.yanger.mybatis.util.Paginator;
import com.yanger.mybatis.util.SQLHelper;

/**
 * @description 基于mybatis-paginator插件，去掉线程池,为MyBatis提供基于方言(Dialect)的分页查询的插件,将拦截Executor.query()方法实现分页方言的插入
 * @author 杨号
 * @date 2018年9月14日
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OffsetLimitInterceptor implements Interceptor {

	private static Logger logger = LoggerFactory.getLogger(OffsetLimitInterceptor.class);

	static final int MAPPED_STATEMENT_INDEX = 0;

	static final int PARAMETER_INDEX = 1;

	static final int ROWBOUNDS_INDEX = 2;

	static final int RESULT_HANDLER_INDEX = 3;

	String dialectClass;
	boolean asyncTotalCount = false;

	public Object intercept(final Invocation invocation) throws Throwable {
		final Executor executor = (Executor) invocation.getTarget();
		final Object[] queryArgs = invocation.getArgs();
		final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		final Object parameter = queryArgs[PARAMETER_INDEX];
		final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
		final PageParam pageParam = new PageParam(rowBounds);

		if (pageParam.getOffset() == RowBounds.NO_ROW_OFFSET && pageParam.getLimit() == RowBounds.NO_ROW_LIMIT
				&& pageParam.getOrders().isEmpty()) {
			return invocation.proceed();
		}

		final Dialect dialect;
		try {
			Class clazz = Class.forName(dialectClass);
			Constructor constructor = clazz.getConstructor(MappedStatement.class, Object.class, PageParam.class);
			dialect = (Dialect) constructor.newInstance(ms, parameter, pageParam);
		} catch (Exception e) {
			throw new ClassNotFoundException("Cannot create dialect instance: " + dialectClass, e);
		}
		BoundSql boundSql = ms.getBoundSql(parameter);
		queryArgs[MAPPED_STATEMENT_INDEX] = copyFromNewSql(ms, boundSql, dialect.getPageSQL(),
				dialect.getParameterMappings(), dialect.getParameterObject());
		queryArgs[PARAMETER_INDEX] = dialect.getParameterObject();
		queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);

		List result = (List) invocation.proceed();
		Integer count;
		Cache cache = ms.getCache();
		if (cache != null && ms.isUseCache() && ms.getConfiguration().isCacheEnabled()) {
			CacheKey cacheKey = executor.createCacheKey(ms, parameter, new PageParam(), copyFromBoundSql(ms, boundSql,
					dialect.getCountSQL(), boundSql.getParameterMappings(), boundSql.getParameterObject()));
			count = (Integer) cache.getObject(cacheKey);
			if (count == null) {
				count = SQLHelper.getCount(ms, executor.getTransaction(), parameter, boundSql, dialect);
				cache.putObject(cacheKey, count);
			}
		} else {
			count = SQLHelper.getCount(ms, executor.getTransaction(), parameter, boundSql, dialect);
		}
		Paginator paginator = new Paginator(pageParam.getPage(), pageParam.getLimit(), count);
		return new Page(result, paginator);
	}

	private MappedStatement copyFromNewSql(MappedStatement ms, BoundSql boundSql, String sql,
			List<ParameterMapping> parameterMappings, Object parameter) {
		BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, sql, parameterMappings, parameter);
		return copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
	}

	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql,
			List<ParameterMapping> parameterMappings, Object parameter) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, parameterMappings, parameter);
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
			}
		}
		return newBoundSql;
	}

	// see: MapperBuilderAssistant
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		Builder builder = new Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
			StringBuilder keyProperties = new StringBuilder();
			for (String keyProperty : ms.getKeyProperties()) {
				keyProperties.append(keyProperty).append(",");
			}
			keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}

		// setStatementTimeout()
		builder.timeout(ms.getTimeout());

		// setStatementResultMap()
		builder.parameterMap(ms.getParameterMap());

		// setStatementResultMap()
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());

		// setStatementCache()
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		String dialectClassName = properties.getProperty("dialectClass");
		setDialectClass(dialectClassName);
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	public void setDialectClass(String dialectClass) {
		logger.debug("dialectClass: {} ", dialectClass);
		this.dialectClass = dialectClass;
	}
}
