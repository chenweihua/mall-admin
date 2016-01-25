package com.mall.admin.model.dao.goods;

import java.util.List;

import com.mall.admin.vo.goods.PropertyValue;

public interface PropertyValueDao {

	public PropertyValue getById(long propertyValueId);
	
	public List<PropertyValue> getByNameId(long propertyNameId);

	public int deleteById(long propertyValueId);

	public int updateByObject(PropertyValue propertyValue);

	public long insert(PropertyValue propertyValue);

}
