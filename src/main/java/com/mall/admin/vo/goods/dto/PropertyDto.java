package com.mall.admin.vo.goods.dto;

public class PropertyDto {
	private long bgGoodsId;
	private long bgSkuId;
	private long propertyNameId;
	private String propertyName;
	private long propertyValueId;
	private String propertyValue;
	public long getBgGoodsId() {
		return bgGoodsId;
	}
	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
	}
	public long getBgSkuId() {
		return bgSkuId;
	}
	public void setBgSkuId(long bgSkuId) {
		this.bgSkuId = bgSkuId;
	}
	public long getPropertyNameId() {
		return propertyNameId;
	}
	public void setPropertyNameId(long propertyNameId) {
		this.propertyNameId = propertyNameId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public long getPropertyValueId() {
		return propertyValueId;
	}
	public void setPropertyValueId(long propertyValueId) {
		this.propertyValueId = propertyValueId;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
}
