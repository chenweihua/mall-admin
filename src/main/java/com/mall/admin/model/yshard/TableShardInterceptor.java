package com.mall.admin.model.yshard;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class TableShardInterceptor implements Interceptor {
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();

	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

	private String gatherSqlRegEx = ".*ForCollect";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY);
		// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
		while (metaStatementHandler.hasGetter("h")) {
			Object object = metaStatementHandler.getValue("h");
			metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		while (metaStatementHandler.hasGetter("target")) {
			Object object = metaStatementHandler.getValue("target");
			metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
		Object parameterObject = metaStatementHandler.getValue("delegate.boundSql.parameterObject");

		if (originalSql != null && !originalSql.equals("")) {
			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
					.getValue("delegate.mappedStatement");
			String id = mappedStatement.getId();

			if (!id.matches(gatherSqlRegEx)) {
				String className = getFullClassName(id.substring(0, id.lastIndexOf(".")));
				Class<?> classObj = Class.forName(className);
				// 根据配置自动生成分表SQL
				TableShard tableShard = classObj.getAnnotation(TableShard.class);
				if (tableShard != null) {
					AnalyzeActualSql as = new AnalyzeActualSql(mappedStatement, parameterObject, boundSql);
					String newSql = as.getActualSql(originalSql, tableShard);
					if (newSql != null) {
						// System.out.println("ccccccccccccccccccccc>>newSql\t"
						// +
						// newSql);
						metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
					}
				} else {
					// System.out.println("11111111111111111111111111");
				}
			}
		}
		// 传递给下一个拦截器处理
		return invocation.proceed();
	}

	/**
	 * 获得全路径名
	 */
	private String getFullClassName(String className) {
		
		return "com.mall.admin.model.dao.shard." + className  + "Shard";
		
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
	}
	
}
