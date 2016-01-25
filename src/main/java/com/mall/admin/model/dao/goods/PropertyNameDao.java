package com.mall.admin.model.dao.goods;

import java.util.List;

import com.mall.admin.vo.goods.PropertyName;
import com.mall.admin.vo.goods.dto.PropertyDto;

public interface PropertyNameDao {

	public PropertyName getById(long propertyNameId);
	
	public List<PropertyName> getByCategoryId(long propertyCategoryId);

	public int deleteById(long propertyNameId);

	public int updateByObject(PropertyName propertyName);

	public long insert(PropertyName propertyName);
	
	public List<PropertyDto> selectPropertyDto(long bgGoodsId);
	
	public List<PropertyName> getPnListByBgGoodsId(long bgGoodsId);

}
