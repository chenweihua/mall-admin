package com.mall.admin.service.mallbase.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.mallbase.CityDao;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.mallbase.District;
import com.mall.admin.vo.mallbase.dto.CityDto;

@Service
public class CityServiceImpl implements CityService {

	public final static int CITY_PID = 0;

	@Autowired
	private CityDao cityDao;

	@Override
	public List<City> getCityListByPid(long pid) {
		// TODO Auto-generated method stub
		return cityDao.getCityListByPid(pid);
	}

	@Override
	public List<City> getCityListById(long id) {
		// TODO Auto-generated method stub
		return cityDao.getCityListById(id);
	}

	@Override
	public City getCityById(long cityId) {
		// TODO Auto-generated method stub
		return cityDao.getCityById(cityId);
	}

	@Override
	public List<City> getCitiesByParams(Map params) {
		// TODO Auto-generated method stub
		return cityDao.getCitiesByParam(params);
	}

	@Override
	public List<District> getDistrictList() {
		// TODO Auto-generated method stub
		List<District> districtList = new ArrayList<District>();
		List<City> cityList = cityDao.getCityListByPid(CITY_PID);
		for (City city : cityList) {
			if(city.getIsDel() == 0){
				District district = new District();
				district.setCity(city);
				List<City> areas;
				areas = cityDao.getCityListByPid(city.getCityId());
				if (areas == null || areas.size() == 0) {
					areas.add(city);
				}
				district.setAreas(areas);
				districtList.add(district);
			}
		}
		return districtList;
	}

	@Override
	public List<CityDto> getCitys(List<Long> ldcCity) {
		List<City> citys = cityDao.getCitys();
		List<CityDto> list = new ArrayList<CityDto>();
		for (City city : citys) {
			if (ldcCity.contains(city.getCityId())) {
				CityDto dto = new CityDto();
				dto.setCity(city);
				list.add(dto);
			}
		}
		return list;
	}

	@Override
	public List<City> getCityList() {
		return cityDao.getCitys();
	}

	@Override
	public City getCityByName(String cityName) {
		return cityDao.getCityByName(cityName);
	}
	
	public City getCityByUcId(Long ucId,Long level) {
		return cityDao.getCityByUcId(ucId,level);
	}

	@Override
	public int add(City city) {
		// TODO Auto-generated method stub
		return cityDao.addCity(city);
	}

	@Override
	public int deleteById(long id, long operatorId) {
		// TODO Auto-generated method stub
		return cityDao.deleteCityById(id, operatorId);
	}

	@Override
	public int edit(City city) {
		// TODO Auto-generated method stub
		return cityDao.updateCity(city);
	}

}
