package com.mall.admin.service.goods;

import java.util.List;

import com.mall.admin.vo.goods.PropertyCategory;

public interface PropertyCategoryService {
	// 插入
	public long insert(PropertyCategory propertyCategory);

	public int deleteById(long propertyCategoryId);

	public int updateByObject(PropertyCategory propertyCategory);

	public PropertyCategory getById(long propertyCategoryId);
	
	public List<PropertyCategory> getByPid(long pid);

}
