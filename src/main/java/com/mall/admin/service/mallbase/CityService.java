package com.mall.admin.service.mallbase;

import java.util.List;
import java.util.Map;

import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.mallbase.District;
import com.mall.admin.vo.mallbase.dto.CityDto;

public interface CityService {

	public List<City> getCityListByPid(long pid);

	public List<City> getCityListById(long id);

	public City getCityById(long cityId);

	public List<CityDto> getCitys(List<Long> ldcCity);

	// pengnanjing add
	/**
	 * 通过查询条件得到城市信息
	 * 
	 * @param params
	 * @return
	 */
	public List<City> getCitiesByParams(Map params);

	public List<City> getCityList();

	public List<District> getDistrictList();

	public City getCityByName(String cityName);

	public int add(City city);

	public int deleteById(long id, long operatorId);

	public int edit(City city);
	
	public City getCityByUcId(Long ucId, Long level);
	// add end
}
