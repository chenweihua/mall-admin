package com.mall.admin.model.dao.goods;

import java.util.List;

import com.mall.admin.vo.goods.PropertyCategory;

public interface PropertyCategoryDao {

	public PropertyCategory getById(long propertyCategoryId);
	
	public List<PropertyCategory> getByPid(long pid);

	public int deleteById(long propertyCategoryId);

	public int updateByObject(PropertyCategory propertyCategory);

	public long insert(PropertyCategory propertyCategory);

}
