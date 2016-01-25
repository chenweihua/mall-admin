package com.mall.admin.service.goods;

import java.util.List;

import com.mall.admin.vo.goods.PropertyName;

public interface PropertyNameService {
	// 插入
	public long insert(PropertyName propertyName);

	public int deleteById(long propertyNameId);

	public int updateByObject(PropertyName propertyName);

	public PropertyName getById(long propertyNameId);
	
	public List<PropertyName> getByCategoryId(long propertyCategoryId);
	
	public List<PropertyName> getByCategoryIdWithValues(long propertyCategoryId);
	
	public Object buildPropertyNameDtos(long bgGoodsId);
	
	public List<PropertyName> getPnListByBgGoodsId(long bgGoodsId);

}
