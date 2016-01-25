package com.mall.admin.service.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.goods.PropertyValueDao;
import com.mall.admin.service.goods.PropertyValueService;
import com.mall.admin.vo.goods.PropertyValue;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
	@Autowired
	PropertyValueDao propertyValueDao;

	@Override
	public long insert(PropertyValue propertyValue) {
		return propertyValueDao.insert(propertyValue);
	}

	@Override
	public int deleteById(long propertyValueId) {
		return propertyValueDao.deleteById(propertyValueId);
	}

	@Override
	public int updateByObject(PropertyValue propertyValue) {
		return propertyValueDao.updateByObject(propertyValue);
	}

	@Override
	public PropertyValue getById(long propertyValueId) {
		return propertyValueDao.getById(propertyValueId);
	}

	@Override
	public List<PropertyValue> getByNameId(long propertyNameId) {
		return propertyValueDao.getByNameId(propertyNameId);
	}
	
}
