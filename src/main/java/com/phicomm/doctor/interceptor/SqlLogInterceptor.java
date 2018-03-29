package com.phicomm.doctor.interceptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 日志查询拦截器
 * 
 * @author zhihongp
 * 
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class SqlLogInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}

	/*private static final ThreadLocal<String> sqlLogContextHolder = new ThreadLocal<String>();

	private static final long DEFAULT_SLOW_LIMIT = 1000l;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static boolean openLog;

	private static int logLength;

	private static long slowLimit;

	private static String ignorePattern;

	public static void setOpenLog(boolean openLog) {
		SqlLogInterceptor.openLog = openLog;
	}

	public static void setLogLength(int logLength) {
		SqlLogInterceptor.logLength = logLength;
	}

	public static void setSlowLimit(long slowLimit) {
		SqlLogInterceptor.slowLimit = slowLimit;
	}

	public static void setIgnorePattern(String ignorePattern) {
		SqlLogInterceptor.ignorePattern = ignorePattern;
	}

	public static String getExecuteSql() {
		String sql = sqlLogContextHolder.get();
		return sql;
	}

	public static void setExecuteSql(String sql) {
		sqlLogContextHolder.set(sql);
	}

	public static void clearSqlLogContext() {
		sqlLogContextHolder.remove();
	}

	// public static Boolean getDdlFlag() {
	// Boolean ddlFlag = ddlFlagContextHolder.get();
	// return ddlFlag;
	// }
	//
	// public static void setDdlFlag(Boolean ddlFlag) {
	// ddlFlagContextHolder.set(ddlFlag);
	// }
	//
	// public static void clearDdlFlagContext() {
	// ddlFlagContextHolder.remove();
	// }

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if (openLog) {
			long startTime = System.currentTimeMillis();
			long endTime = 0l;
			Object obj = null;

			try {
				obj = invocation.proceed();
			} catch (Throwable t) {
				obj = t.getClass().getCanonicalName() + ":" + t.getMessage();
				throw t;
			} finally {
				MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
				String sqlId = mappedStatement.getId();
				boolean sqlLogFlag = getSqlLogFlag(sqlId);

				if (sqlLogFlag) {
					String sql = getSql(invocation, mappedStatement);
					endTime = (endTime == 0 ? System.currentTimeMillis() : endTime);
					long cost = endTime - startTime;
					long slowLimit = SqlLogInterceptor.slowLimit != 0l ? SqlLogInterceptor.slowLimit : DEFAULT_SLOW_LIMIT;
					boolean slowQuery = (cost > slowLimit);
					String result = "";

					if (obj != null) {
						result = JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss.SSS", SerializerFeature.DisableCircularReferenceDetect);
					}

					boolean ddlFlag = getDdlFlag();
					clearSqlLogContext();
					String sqlLog = getSqlLog(sqlId, sql, result, slowQuery, ddlFlag, startTime, endTime, cost);
					int logLength = SqlLogInterceptor.logLength != 0 ? SqlLogInterceptor.logLength : 10000;

					if (logLength != -1 && sqlLog.length() > logLength) {
						sqlLog = sqlLog.substring(0, logLength);
					}

					log.info(sqlLog);
				}
			}

			return obj;
		} else {
			Object obj = invocation.proceed();
			return obj;
		}
	}

	private boolean getSqlLogFlag(String sqlId) {

		Pattern pattern = Pattern.compile(ignorePattern);
		Matcher matcher = pattern.matcher(sqlId);

		if (matcher.matches()) {
			return false;
		} else {
			return true;
		}
	}

	private String getSql(Invocation invocation, MappedStatement mappedStatement) {
		String sql = getExecuteSql();

		if (sql == null) {
			Object parameter = invocation.getArgs()[1];
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			String interceptSql = boundSql.getSql();
			sql = handleSql(interceptSql, mappedStatement, boundSql);
		}

		return sql;
	}

	private Boolean getDdlFlag() {
		String sql = getExecuteSql();

		if (sql != null) {
			return true;
		} else {
			return false;
		}
	}

	private String getSqlLog(String method, String sql, String result, boolean slowQuery, boolean ddlFlag, long startTime, long endTime, long cost) {
		String startTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(startTime));
		String endTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(endTime));
		String slowQueryLog = slowQuery ? "(slowQuery)" : "";
		String ddlFlagLog = ddlFlag ? "(ddl)" : "";
		return String.format("[Sql]" + slowQueryLog + ddlFlagLog + " sql:%s|method:%s|result:%s|[start:%s, end:%s, cost:%dms]", sql, method, result,
				startTimeStr, endTimeStr, cost);
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	
	*//**
	 * 处理拦截的sql，主要包括格式化去掉空格，将传入的参数填值进去
	 * 
	 * @param interceptSql
	 * @param handler
	 * @param boundSql
	 * @return
	 *//*
	public String handleSql(String interceptSql, MappedStatement mappedStatement, BoundSql boundSql) {
		String sql = interceptSql.replaceAll("[\\s]+", " ").trim();
		Configuration configuration = mappedStatement.getConfiguration();
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

		try {
			if (parameterObject != null && parameterMappings != null && !parameterMappings.isEmpty()) {
				TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

				if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
					sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
				} else {
					MetaObject metaObject = configuration.newMetaObject(parameterObject);

					for (ParameterMapping parameterMapping : parameterMappings) {
						String propertyName = parameterMapping.getProperty();
						if (metaObject.hasGetter(propertyName)) {
							Object obj = metaObject.getValue(propertyName);
							sql = sql.replaceFirst("\\?", getParameterValue(obj));
						} else if (boundSql.hasAdditionalParameter(propertyName)) {
							Object obj = boundSql.getAdditionalParameter(propertyName);
							sql = sql.replaceFirst("\\?", getParameterValue(obj));
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Parse sql exception, " + sql);
		}

		return sql;
	}
	
	*//**
	 * 获取参数值
	 * 
	 * @param obj
	 * @return
	 *//*
	public static String getParameterValue(Object obj) {
		String value = null;
		
		if (obj == null) {
			value = "null";
		} else if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			Date date = (Date) obj;
			DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(date) + "'";
		} else {
			value = obj.toString();
		}
		
		return getSafeString(value);
	}
	
	public static String getSafeString(String str) {
		return str.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
	}*/
}

