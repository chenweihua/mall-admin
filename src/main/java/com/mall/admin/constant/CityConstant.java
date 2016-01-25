package com.mall.admin.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.vo.mallbase.City;

public class CityConstant {
	private static Map<Long, City> cityMap = new HashMap<Long, City>();
	private static Map<String, City> cityNameMap = new HashMap<String, City>();

	@Autowired
	private CityService cityService;

	private CityConstant()
	{
	}

	/**
	 * 初始化函数
	 */
	public void init() {
		LogConstant.mallLog.debug("init city");
		refresh();
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		Map<Long, City> tempcityMap = new HashMap<Long, City>();
		Map<String, City> tempNamecityMap = new HashMap<String, City>();
		List<City> cityList = cityService.getCityList();
		if (!cityList.isEmpty()) {
			for (City city : cityList) {
				tempcityMap.put(city.getCityId(), city);
				tempNamecityMap.put(city.getCityName(), city);
			}
		}
		cityMap = tempcityMap;
		cityNameMap = tempNamecityMap;

		// 将子挂在父上
		for (City city : cityMap.values()) {
			if (city.getPid() != 0L) {
				City parentCity = cityMap.get(city.getPid());
				if (parentCity != null) {
					parentCity.addSon(city);
				}

			}
		}

		LogConstant.mallLog.info("refresh " + (cityList.isEmpty() ? 0 : cityList.size()) + " city");
	}

	/**
	 * 根据id获得整个city对象
	 * 
	 * @param key
	 * @return
	 */
	public static City getcityById(Long id) {
		return cityMap.get(id);
	}

	/**
	 * 根据name获得整个city对象
	 * 
	 * @param key
	 * @return
	 */
	public static City getcityByName(String name) {
		return cityNameMap.get(name);
	}

	public static Map<Long, City> getcityMap() {
		return cityMap;
	}

	public static Map<String, City> getcityNameMap() {
		return cityNameMap;
	}
}
