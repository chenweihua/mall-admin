package com.mall.admin.vo.supplier;

import java.io.Serializable;
import java.sql.Timestamp;

public class Suppiler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2737239623327677177L;
	
	private int shopId;
	
	private String shopName;
	/**
	 * 店主姓名
	 */
	private String shopKeeper;
	/**
	 * 电话
	 */
	private String shopPhone;
	
	private int shopWeight;
	/**
	 * 店铺类型，1：O2O模式；2：电商模式
	 */
	private int shopType;
	public static final int SHOP_TYPE_O2O = 1;
	public static final int SHOP_TYPE_EC = 2;
	
	/**
	 * 店铺开关，0：关闭；1：开启
	 */
	private int shopIsOpen;
	public static final int SHOP_ISOPEN = 1;
	public static final int SHOP_ISCLOSED = 0;
	
	/**
	 * 供应商ID，来源磐石系统
	 */
	private String sellerId;
	/**
	 * 供应商编码
	 */
	private String sellerCode;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private int operateId;
	/**
	 * 仓库ID
	 */
	private int storageId;
	
	private String imageUrl;

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopKeeper() {
		return shopKeeper;
	}

	public void setShopKeeper(String shopKeeper) {
		this.shopKeeper = shopKeeper;
	}

	public String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}

	public int getShopWeight() {
		return shopWeight;
	}

	public void setShopWeight(int shopWeight) {
		this.shopWeight = shopWeight;
	}

	public int getShopType() {
		return shopType;
	}

	public void setShopType(int shopType) {
		this.shopType = shopType;
	}

	public int getShopIsOpen() {
		return shopIsOpen;
	}

	public void setShopIsOpen(int shopIsOpen) {
		this.shopIsOpen = shopIsOpen;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public int getOperateId() {
		return operateId;
	}

	public void setOperateId(int operateId) {
		this.operateId = operateId;
	}

	public int getStorageId() {
		return storageId;
	}

	public void setStorageId(int storageId) {
		this.storageId = storageId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
