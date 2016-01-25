package com.mall.admin.service.goods;

import java.util.List;

import com.mall.admin.vo.goods.PropertyValue;

public interface PropertyValueService {
	// 插入
	public long insert(PropertyValue propertyValue);

	public int deleteById(long propertyValueId);

	public int updateByObject(PropertyValue propertyValue);

	public PropertyValue getById(long propertyValueId);
	
	public List<PropertyValue> getByNameId(long propertyNameId);

}
