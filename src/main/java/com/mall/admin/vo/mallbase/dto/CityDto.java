package com.mall.admin.vo.mallbase.dto;

import com.mall.admin.vo.mallbase.City;

public class CityDto {
	private City city;
	private long originPrice;
	private long wapPrice;
	private long appPrice;
	private long maxNum;

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public long getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(long originPrice) {
		this.originPrice = originPrice;
	}

	public long getWapPrice() {
		return wapPrice;
	}

	public void setWapPrice(long wapPrice) {
		this.wapPrice = wapPrice;
	}

	public long getAppPrice() {
		return appPrice;
	}

	public void setAppPrice(long appPrice) {
		this.appPrice = appPrice;
	}

	public long getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(long maxNum) {
		this.maxNum = maxNum;
	}

}
