package com.mall.admin.service.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.goods.PropertyCategoryDao;
import com.mall.admin.service.goods.PropertyCategoryService;
import com.mall.admin.vo.goods.PropertyCategory;
@Service
public class PropertyCategoryServiceImpl implements PropertyCategoryService {
	@Autowired
	PropertyCategoryDao propertyCategoryDao;
	
	@Override
	public long insert(PropertyCategory propertyCategory) {
		return propertyCategoryDao.insert(propertyCategory);
	}

	@Override
	public int deleteById(long propertyCategoryId) {
		return propertyCategoryDao.deleteById(propertyCategoryId);
	}

	@Override
	public int updateByObject(PropertyCategory propertyCategory) {
		return propertyCategoryDao.updateByObject(propertyCategory);
	}

	@Override
	public PropertyCategory getById(long propertyCategoryId) {
		return propertyCategoryDao.getById(propertyCategoryId);
	}

	@Override
	public List<PropertyCategory> getByPid(long pid) {
		return propertyCategoryDao.getByPid(pid);
	}
	
}
