package com.mall.admin.vo.ump;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.mall.admin.util.FeatureUtil;


/**
 * 基础表
 * 解决key，value 对的方式
 * 
 * @author 温德亮
 *
 */
public class Feature implements Serializable{

	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -807260412256765339L;

	
	/**
	 * 对应数据库字段，存储的值
	 */
	protected String feature;

	/**
	 * 转换成一个map根据id获得
	 */
	protected Map<String, String> featureMap;
	
	/**
	 * 给Ibatis使用
	 * 
	 * @return
	 */
	public String getFeature() {
		String feature = FeatureUtil.toString(featureMap);
		if (StringUtils.isBlank(feature)) {
			return null;
		}
		return feature;
	}

	/**
	 * 给Ibatis使用
	 * 
	 * @return
	 */
	public void setFeature(String feature) {
		this.featureMap = FeatureUtil.toMap(feature);
	}

	public void setFeatureMap(Map<String, String> featureMap) {
		this.featureMap = featureMap;
	}

	public Map<String, String> getFeatureMap() {
		return featureMap;
	}

	public String getFeatureValue(String featureKey) {
		return featureMap == null ? null : featureMap.get(featureKey);
	}

	public void setFeatureValue(String key, String value) {
		if (featureMap == null) {
			featureMap = new HashMap<String, String>();
		}
		if (StringUtils.isBlank(value)) {
			// 如果value是空，则认为是删除该key
			featureMap.remove(key);
		} else {
			featureMap.put(key, value);
		}
	}
	
	
	

}
