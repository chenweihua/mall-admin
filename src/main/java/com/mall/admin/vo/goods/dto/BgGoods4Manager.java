package com.mall.admin.vo.goods.dto;

public class BgGoods4Manager {
	private long bgGoodsId;

	private String bgGoodsName;

	private String description;

	private String remark;

	private long categoryId;
	
	private long propertyCategoryId;

	private String unit;

	private String imageUrl;
	
	private int goodsType;
	
	private String wmsGoodsGbm;
	
	private String categoryName;
	
	private int bgGoodsStatus;
	
	private long stock;
	
	private long storageId;

	public long getBgGoodsId() {
		return bgGoodsId;
	}

	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
	}

	public String getBgGoodsName() {
		return bgGoodsName;
	}

	public void setBgGoodsName(String bgGoodsName) {
		this.bgGoodsName = bgGoodsName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getPropertyCategoryId() {
		return propertyCategoryId;
	}

	public void setPropertyCategoryId(long propertyCategoryId) {
		this.propertyCategoryId = propertyCategoryId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public String getWmsGoodsGbm() {
		return wmsGoodsGbm;
	}

	public void setWmsGoodsGbm(String wmsGoodsGbm) {
		this.wmsGoodsGbm = wmsGoodsGbm;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getBgGoodsStatus() {
		return bgGoodsStatus;
	}

	public void setBgGoodsStatus(int bgGoodsStatus) {
		this.bgGoodsStatus = bgGoodsStatus;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public long getStorageId() {
		return storageId;
	}

	public void setStorageId(long storageId) {
		this.storageId = storageId;
	}
}
