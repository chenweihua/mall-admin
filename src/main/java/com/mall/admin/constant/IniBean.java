package com.mall.admin.constant;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mall.admin.exception.SystemConfigNotFoundException;
import com.mall.admin.service.mallbase.MallIniService;
import com.mall.admin.vo.mallbase.MallIni;

/**
 * 系统全局配置bean
 * 
 * @author Singal
 * 
 */
public class IniBean {
	private static Map<String, MallIni> iniMap = new HashMap<String, MallIni>();
	private static Map<String, String> iniValueMap = new HashMap<String, String>();

	@Autowired
	private MallIniService mallIniService;

	private IniBean() {
	}

	/**
	 * 初始化函数
	 */
	public void init() {
		LogConstant.mallLog.info("init ini");
		refresh();
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		Map<String, MallIni> tempIniMap = new HashMap<String, MallIni>();
		Map<String, String> tempIniValueMap = new HashMap<String, String>();
		List<MallIni> iniList = mallIniService.getAllMallIni();
		if (iniList != null) {
			for (MallIni mallIni : iniList) {
				tempIniMap.put(mallIni.getIniName(), mallIni);
				tempIniValueMap
						.put(mallIni.getIniName(), mallIni.getIniValue());
			}
		}
		iniMap = tempIniMap;
		iniValueMap = tempIniValueMap;
		LogConstant.mallLog.info("refresh "
				+ (iniList == null ? 0 : iniList.size()) + " inis");
	}

	/**
	 * 根据key前缀获得整个ini对象
	 * 
	 * @param keyStart
	 * @return
	 */
	public static List<MallIni> getIniMapStartWith(String keyStart) {
		LinkedList<MallIni> list = new LinkedList<MallIni>();
		for (Map.Entry<String, MallIni> ini : iniMap.entrySet()) {
			if (ini.getKey().startsWith(keyStart)) {
				list.add(ini.getValue());
			}
		}
		return list;
	}

	/**
	 * 直接返回float类型的ini值，可以指定默认值
	 *
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static float getIniFloatValue(String key, String defaultValue) {
		String iniValue = getIniValue(key, defaultValue);
		float result;
		try {
			result = Float.parseFloat(iniValue);
		} catch (NumberFormatException e) {
			throw new SystemConfigNotFoundException(
					"not found a float for ini :" + key);
		}
		return result;
	}

	/**
	 * 根据key获得整个ini对象
	 * 
	 * @param key
	 * @return
	 */
	public static MallIni getIniMap(String key) {
		return iniMap.get(key);
	}

	/**
	 * 根据key获得ini的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getIniValue(String key) {
		if (!iniValueMap.containsKey(key)) {
			throw new SystemConfigNotFoundException("no ini found for :" + key);
		}
		return iniValueMap.get(key);
	}

	/**
	 * 根据key获得ini值，可以指定默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getIniValue(String key, String defaultValue) {
		if (iniValueMap.containsKey(key)) {
			return iniValueMap.get(key);
		} else if (StringUtils.isNotBlank(defaultValue)) {
			return defaultValue;
		} else {
			throw new SystemConfigNotFoundException("no ini found for :" + key);
		}
	}
	
	/**
	 * 根据key获得ini值，可以指定默认值,不抛异常，没有直接返回默认
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getIniSpecifiedValue(String key, String defaultValue) {
		if (iniValueMap.containsKey(key)) {
			return iniValueMap.get(key);
		} 
		else {
			return defaultValue;
		} 
	}

	/**
	 * 直接返回int类型的ini值，可以指定默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getIniIntValue(String key, String defaultValue) {
		String iniValue = getIniValue(key, defaultValue);
		int result;
		try {
			result = Integer.parseInt(iniValue);
		} catch (NumberFormatException e) {
			throw new SystemConfigNotFoundException(
					"not found an int for ini :" + key);
		}
		return result;
	}

	/**
	 * 直接返回int类型的ini值，可以指定默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getIniIntValue(String key, int defaultValue) {
		int result = defaultValue;
		if (iniValueMap.containsKey(key)) {
			String iniValue = getIniValue(key);
			try {
				result = Integer.parseInt(iniValue);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return result;
	}

	/**
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static long getIniLongValue(String key, long defaultValue) {
		long result = defaultValue;
		if (iniValueMap.containsKey(key)) {
			String iniValue = getIniValue(key);
			try {
				result = Long.parseLong(iniValue);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return result;
	}
	
	
	public static Map<String,MallIni> getMallIniMap() {
		return iniMap;
	}
}
