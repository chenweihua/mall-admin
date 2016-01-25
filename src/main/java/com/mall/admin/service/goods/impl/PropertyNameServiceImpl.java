package com.mall.admin.service.goods.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mall.admin.model.dao.goods.BgGoodsDao;
import com.mall.admin.model.dao.goods.PropertyNameDao;
import com.mall.admin.service.goods.PropertyNameService;
import com.mall.admin.service.goods.PropertyValueService;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.PropertyName;
import com.mall.admin.vo.goods.PropertyValue;
import com.mall.admin.vo.goods.dto.PropertyDto;

@Service
public class PropertyNameServiceImpl implements PropertyNameService {
	@Autowired
	private PropertyNameDao propertyNameDao;
	@Autowired
	private BgGoodsDao bgGoodsDao;
	@Autowired
	private PropertyValueService propertyValueService;

	@Override
	public long insert(PropertyName propertyName) {
		return propertyNameDao.insert(propertyName);
	}

	@Override
	public int deleteById(long propertyNameId) {
		return propertyNameDao.deleteById(propertyNameId);
	}

	@Override
	public int updateByObject(PropertyName propertyName) {
		return propertyNameDao.updateByObject(propertyName);
	}

	@Override
	public PropertyName getById(long propertyNameId) {
		return propertyNameDao.getById(propertyNameId);
	}

	@Override
	public List<PropertyName> getByCategoryId(long propertyCategoryId) {
		return propertyNameDao.getByCategoryId(propertyCategoryId);
	}

	@Override
	public List<PropertyName> getByCategoryIdWithValues(long propertyCategoryId) {
		List<PropertyName> propertyNameList = propertyNameDao.getByCategoryId(propertyCategoryId);
		int index = 1;
		for(PropertyName propertyName : propertyNameList){
			propertyName.setIndex(index);
			List<PropertyValue> propertyValueList = propertyValueService.getByNameId(propertyName.getPropertyNameId());
			Object[] pvArray = new Object[propertyValueList.size()/5 + 1];
			List<PropertyValue> pvList = null;
			for(int i=0,len=propertyValueList.size();i<len;i++){
				if(i%5 == 0){
					pvList = new ArrayList<>();
					pvList.add(propertyValueList.get(i));
					pvArray[i/5] = pvList;
				}else{
					pvList.add(propertyValueList.get(i));
				}
			}
			propertyName.setPropertyVauleListArray(pvArray);
			propertyName.setPropertyVauleList(propertyValueList);
			index++;
		}
		return propertyNameList;
	}

	@Override
	public Object buildPropertyNameDtos(long bgGoodsId) {
		
		List<PropertyDto> propertyDtos = propertyNameDao.selectPropertyDto(bgGoodsId);
		
		List<Long> pnIds = new ArrayList<>();
		List<Long> pvIds = new ArrayList<>();
		JsonObject pnDtos = new JsonObject();
		
		BgGoods temp = null;
		JsonArray pnArray = new JsonArray();
		if(propertyDtos == null || propertyDtos.size() == 0){
			pnDtos.addProperty("code", -1);
			pnDtos.addProperty("msg", "商品没配置属性值！");
			return pnDtos;
		}
		//解析pName
		for(PropertyDto property : propertyDtos){
			if(!pnIds.contains(property.getPropertyNameId())){
				pnIds.add(property.getPropertyNameId());
				JsonObject pnObject = new JsonObject();
				pnObject.addProperty("propertyNameId", property.getPropertyNameId());
				pnObject.addProperty("propertyName", property.getPropertyName());
				JsonArray pvArray = new JsonArray();
				//解析pValue
				for(PropertyDto property2 : propertyDtos){
					if(property.getPropertyNameId() == property2.getPropertyNameId() && !pvIds.contains(property2.getPropertyValueId())){
						pvIds.add(property2.getPropertyValueId());
						JsonObject pvObject = new JsonObject();
						pvObject.addProperty("propertyValueId", property2.getPropertyValueId());
						pvObject.addProperty("propertyValue", property2.getPropertyValue());
						JsonArray skuArray = new JsonArray();
						//解析pValue对应的单品
						for(PropertyDto property3 : propertyDtos){
							if(property2.getPropertyValueId() == property3.getPropertyValueId()){
								JsonObject skuObject = new JsonObject();
								skuObject.addProperty("bgSkuId", property3.getBgSkuId());
								temp = bgGoodsDao.getSingByBgSkuId(property3.getBgSkuId());
								if(temp != null){
									skuObject.addProperty("bgSkuName", temp.getBgGoodsName());
								}
								skuArray.add(skuObject);
							}
						}
						pvObject.add("bgSkus", skuArray);
						pvArray.add(pvObject);
					}
				}
				pnObject.add("pValues", pvArray);
				pnArray.add(pnObject);
			}
		}
		pnDtos.addProperty("code", 0);
		pnDtos.addProperty("msg", "success");
		pnDtos.add("data", pnArray);
		return pnDtos;
	}

	@Override
	public List<PropertyName> getPnListByBgGoodsId(long bgGoodsId) {
		return propertyNameDao.getPnListByBgGoodsId(bgGoodsId);
	}
}
