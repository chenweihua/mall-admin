package com.mall.admin.model.dao.mallbase;

import java.util.List;
import java.util.Map;

import com.mall.admin.vo.mallbase.City;

public interface CityDao {

	public List<City> getCityListByPid(long pid);

	public List<City> getCityListById(long id);

	public City getCityById(long cityId);

	public List<City> getCitys();

	// pengnanjing add 2015-7-20 11:36:30
	public List<City> getCitiesByParam(Map params);

	public int addCity(City city);

	public int deleteCityById(long cityId, long operatorId);

	public int updateCity(City city);

	public City getCityByName(String cityName);
	
	public City getCityByUcId(Long ucId,Long level);

	// add end
}
