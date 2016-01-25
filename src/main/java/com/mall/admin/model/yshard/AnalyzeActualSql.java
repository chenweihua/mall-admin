package com.mall.admin.model.yshard;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

public class AnalyzeActualSql {
	private MappedStatement mappedStatement;
	private Object parameterObject;
	private BoundSql boundSql;

	public AnalyzeActualSql(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
		this.setMappedStatement(mappedStatement);
		this.setParameterObject(parameterObject);
		this.setBoundSql(boundSql);
	}

	public String getActualSql(String originalSql, TableShard tableShard) throws Exception {
		String newSql = null;

		if (tableShard == null) {
			return newSql;
		}

		String tableName = tableShard != null ? tableShard.tableName().trim() : "";
		String shardType = tableShard != null ? tableShard.shardType().trim() : "";
		String shardBy = tableShard != null ? tableShard.shardBy().trim() : "";


		if (shardType.startsWith("%") && shardBy.equals("userId")) {
			long userId = getUserId();
			long div = Long.parseLong(shardType.substring(1));
			long suffix = userId % div;
			String newTableName = tableName + "_" + suffix;
			newSql = originalSql.replaceAll(tableName, newTableName);
		} else {
			long div = Long.parseLong(shardType.substring(1));
			String[] valueArr = getShardByValues(shardBy);
			int hashCode = String.join(",", valueArr).hashCode(); //根据多个字段用，连接的字符串的hashcode值作为分表的依据
			long suffix = Math.abs(hashCode) % div;
			String newTableName = tableName + "_" + suffix;
			newSql = originalSql.replaceAll(tableName, newTableName);
		}

		return newSql;
	}
	
	
	/**
	 * 取得shardBy字段的值
	 * 注意：根据多个字段进行分库分表时，必须固定各个字段的顺序，这里的返回的取值也是按着shardBy本身的顺序
	 * 这里按String类型
	 */
	private String[] getShardByValues(String shardBy) throws Exception {
		String[] shardByArr = shardBy.split(",");
		String[] valueArr = new String[shardByArr.length];
		Object parameterObject = boundSql.getParameterObject();
		if (parameterObject != null) {
			for(int i = 0; i < shardByArr.length; i++) {
				String eachShardBy = shardByArr[i];
				if (parameterObject instanceof Map<?, ?>) {
					valueArr[i] = (String) ((Map<?, ?>) parameterObject).get(eachShardBy);
				} else {
					Field field = ReflectHelper.getFieldByFieldName(parameterObject, eachShardBy);
					if (field != null) {
						valueArr[i] = (String) ReflectHelper.getValueByFieldName(parameterObject, eachShardBy);
					}
				}
			}
		}
		return valueArr;
	}
	
	
	

	private long getUserId() throws SecurityException, NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		Object parameterObject = boundSql.getParameterObject();
		long userId = 0L;
		if (parameterObject != null) {
			if (parameterObject instanceof Long) {
				userId = (Long) parameterObject;
			} else if (parameterObject instanceof Map<?, ?>) {
				userId = (Long) ((Map<?, ?>) parameterObject).get("userId");
			} else {
				Field userIdField = ReflectHelper.getFieldByFieldName(parameterObject, "userId");
				if (userIdField != null) {
					userId = (Long) ReflectHelper.getValueByFieldName(parameterObject, "userId");
				}
			}
		}
		return userId;
	}

	public Object getParameterObject() {
		return parameterObject;
	}

	public void setParameterObject(Object parameterObject) {
		this.parameterObject = parameterObject;
	}

	public MappedStatement getMappedStatement() {
		return mappedStatement;
	}

	public void setMappedStatement(MappedStatement mappedStatement) {
		this.mappedStatement = mappedStatement;
	}

	public BoundSql getBoundSql() {
		return boundSql;
	}

	public void setBoundSql(BoundSql boundSql) {
		this.boundSql = boundSql;
	}

	static class ReflectHelper {
		public static Field getFieldByFieldName(Object obj, String fieldName) {
			for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
					.getSuperclass()) {
				try {
					return superClass.getDeclaredField(fieldName);
				} catch (NoSuchFieldException e) {
				}
			}
			return null;
		}

		public static Object getValueByFieldName(Object obj, String fieldName) throws SecurityException,
				NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
			Field field = getFieldByFieldName(obj, fieldName);
			Object value = null;
			if (field != null) {
				if (field.isAccessible()) {
					value = field.get(obj);
				} else {
					field.setAccessible(true);
					value = field.get(obj);
					field.setAccessible(false);
				}
			}
			return value;
		}

		public static void setValueByFieldName(Object obj, String fieldName, Object value) throws SecurityException,
				NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
			Field field = obj.getClass().getDeclaredField(fieldName);
			if (field.isAccessible()) {
				field.set(obj, value);
			} else {
				field.setAccessible(true);
				field.set(obj, value);
				field.setAccessible(false);
			}
		}
	}

}
