package com.mall.admin.vo.mallbase.dto;

import com.mall.admin.vo.mallbase.Storage;

public class StorageDto {
	private Storage storage;
	private long originPrice;
	private long wapPrice;
	private long appPrice;
	private long maxNum;

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
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
