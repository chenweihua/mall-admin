package com.mall.admin.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * request到类转换，支持数据类型有限 如不能满足需求，可利用request.getParamet(),获取特定字段，单独处理
 * 
 * @author zhang-shuai
 *
 */
public class RequestUtil {

	/**
	 * request解析成map，具有相同名称的多个值，以逗号分隔
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> toMap(HttpServletRequest request) {
		Map<String, String> tempMap = new HashMap<String, String>();
		Map<String, String[]> map = request.getParameterMap();
		for (String key : map.keySet()) {
			String value = "";
			for (String s : map.get(key)) {
				value += s + ",";
			}
			value = value.substring(0, value.length() - 1);
			tempMap.put(key, value);
		}
		return tempMap;
	}

	/**
	 * request解析成对象，支持的数据类型：String,double,float,long,int,short,byte,TimeStamp
	 * 当request提交字段为空时，数值型字段默认-1,字符默认为"";当日期解析失败是，日期默认当前时间
	 * 
	 * @param request
	 * @param clazz
	 * @param pattern
	 *            必须是yyyy-mm-dd hh:mm:ss[.fffffffff]
	 * @return
	 */
	public static <T> T toBean(Map<String, String> params, Class<T> clazz) {
		return toBean(params, clazz, "");
	}

	/**
	 * request解析成对象，支持的数据类型：String,double,float,long,int,short,byte,TimeStamp
	 * 当request提交字段为空时，数值型字段默认-1,字符默认为"";当日期解析失败是，日期默认当前时间
	 * 
	 * @param request
	 * @param clazz
	 * @param pattern
	 *            必须是yyyy-mm-dd hh:mm:ss[.fffffffff]
	 * @return
	 */
	public static <T> T toBean(Map<String, String> params, Class<T> clazz,
			String pattern) {
		Map<String, Field> fields = getFieldMap(clazz);
		T t = null;
		try {
			t = clazz.newInstance();
			int i = 1;
			for (String key : fields.keySet()) {
				i = setValue(fields.get(key), t, params.get(key), pattern);
				if (i != 1) {
					return null;
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * request解析成对象，支持的数据类型：String,double,float,long,int,short,byte
	 * 当request提交字段为空时，数值型字段默认-1,字符默认为"";
	 * 
	 * @param request
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(HttpServletRequest request, Class<T> clazz) {
		return toBean(request, clazz, "");
	}

	/**
	 * request解析成对象，支持的数据类型：String,double,float,long,int,short,byte,TimeStamp
	 * 当request提交字段为空时，数值型字段默认-1,字符默认为"";当日期解析失败是，日期默认当前时间
	 * 
	 * @param request
	 * @param clazz
	 * @param pattern
	 *            必须是yyyy-mm-dd hh:mm:ss[.fffffffff]
	 * @return
	 */
	public static <T> T toBean(HttpServletRequest request, Class<T> clazz,
			String pattern) {
		Map<String, String> params = toMap(request);
		Map<String, Field> fields = getFieldMap(clazz);
		T t = null;
		try {
			t = clazz.newInstance();
			int i = 1;
			for (String key : fields.keySet()) {
				i = setValue(fields.get(key), t, params.get(key), pattern);
				if (i != 1) {
					return null;
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}

	private static Map<String, Field> getFieldMap(Class<?> clazz) {
		Map<String, Field> fields = new HashMap<String, Field>();
		for (Field field : clazz.getDeclaredFields()) {
			int mod = field.getModifiers();
			if (!Modifier.isStatic(mod))
				fields.put(field.getName(), field);
		}
		return fields;
	}

	private static <T> int setValue(Field field, T t, String value,
			String pattern) {
		// 设定私有属性可访问
		field.setAccessible(true);
		// 根据类型设值
		try {
			if (field.getType() == String.class) {
				field.set(t, value == null ? "" : value.trim());
				return 1;
			}
			if (field.getType() == double.class) {
				field.set(t, NumberUtils.toDouble(value, -1));
				return 1;
			}
			if (field.getType() == float.class) {
				field.set(t, NumberUtils.toFloat(value, -1));
				return 1;
			}
			if (field.getType() == long.class) {
				field.set(t, NumberUtils.toLong(value, -1));
				return 1;
			}
			if (field.getType() == int.class) {
				field.set(t, NumberUtils.toInt(value, -1));
				return 1;
			}
			if (field.getType() == short.class) {
				field.set(t, NumberUtils.toShort(value, (short) -1));
				return 1;
			}
			if (field.getType() == byte.class) {
				field.set(t, NumberUtils.toByte(value, (byte) -1));
				return 1;
			}
			if (field.getType() == Timestamp.class) {
				if (StringUtils.isEmpty(pattern) || StringUtils.isEmpty(value)) {
					field.set(t, new Timestamp(System.currentTimeMillis()));
					return 1;
				}
				field.set(t, Timestamp.valueOf(value));
				return 1;
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

}
