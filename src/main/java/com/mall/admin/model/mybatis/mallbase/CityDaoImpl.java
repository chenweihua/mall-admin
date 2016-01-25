package com.mall.admin.model.mybatis.mallbase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.mallbase.CityDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.mallbase.City;

@Repository
public class CityDaoImpl extends BaseMallDaoImpl implements CityDao {

	@Override
	public List<City> getCityListByPid(long pid) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("City.getCityByPid", pid);
	}

	@Override
	public List<City> getCityListById(long id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("City.getCityById", id);
	}

	@Override
	public City getCityById(long cityId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("City.getCityByCityId", cityId);
	}

	@Override
	public List<City> getCitys() {
		return this.getSqlSession().selectList("City.getCitys");
	}

	@Override
	public List<City> getCitiesByParam(Map params) {
		if (params == null || params.isEmpty()) {
			long pid = 0;
			return this.getSqlSession().selectList("City.getCityByPid", pid);
		}

		return this.getSqlSession().selectList("City.getCitiesByParam", params);
	}

	@Override
	public int addCity(City city) {
		return this.getSqlSession().insert("City.addCity", city);
	}

	@Override
	public int deleteCityById(long cityId, long operatorId) {
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("cityId", cityId);
		params.put("operatorId", operatorId);
		return this.getSqlSession().update("City.deleteCityById", params);
	}

	@Override
	public int updateCity(City city) {
		return this.getSqlSession().update("City.updateCity", city);
	}

	@Override
	public City getCityByName(String cityName) {
		return this.getSqlSession().selectOne("City.getByName", cityName);
	}
	
	public City getCityByUcId(Long ucId,Long level) {
		Map parameterMap = new HashMap();
		parameterMap.put("ucId", ucId);
		parameterMap.put("level", level);
		return this.getSqlSession().selectOne("City.getByUcId", parameterMap);
	}
}
