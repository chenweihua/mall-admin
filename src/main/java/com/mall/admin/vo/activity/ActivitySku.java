package com.mall.admin.vo.activity;

public class ActivitySku {

	private long activitySkuId;
	// 关联的前台活动goods的id
	private long activityGoodsId;
	// 关联的活动模板sku的id
	private long activityBgSkuId;
	private long collegeId;
	private long bgSkuId;
	private long originPrice;
	private long activityPrice;
	private long skuType;
	private String imageUrl;
	private long stock;
	private long storageType;
	private int isDel;
	private long storageId;

	public long getActivityBgSkuId() {
		return activityBgSkuId;
	}

	public void setActivityBgSkuId(long activityBgSkuId) {
		this.activityBgSkuId = activityBgSkuId;
	}

	public long getActivitySkuId() {
		return activitySkuId;
	}

	public void setActivitySkuId(long activitySkuId) {
		this.activitySkuId = activitySkuId;
	}

	public long getActivityGoodsId() {
		return activityGoodsId;
	}

	public void setActivityGoodsId(long activityGoodsId) {
		this.activityGoodsId = activityGoodsId;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public long getBgSkuId() {
		return bgSkuId;
	}

	public void setBgSkuId(long bgSkuId) {
		this.bgSkuId = bgSkuId;
	}

	public long getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(long originPrice) {
		this.originPrice = originPrice;
	}

	public long getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(long activityPrice) {
		this.activityPrice = activityPrice;
	}

	public long getSkuType() {
		return skuType;
	}

	public void setSkuType(long skuType) {
		this.skuType = skuType;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public long getStorageType() {
		return storageType;
	}

	public void setStorageType(long storageType) {
		this.storageType = storageType;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public long getStorageId() {
		return storageId;
	}

	public void setStorageId(long storageId) {
		this.storageId = storageId;
	}

	public static ActivitySku initActivitySKu(BgSkuForActivity bgSkuForActivity, long activityGoodsId,
			long collegeId) {
		ActivitySku activitySku = new ActivitySku();
		activitySku.setActivityGoodsId(activityGoodsId);
		activitySku.setActivityBgSkuId(bgSkuForActivity.getActivityBgSkuId());
		activitySku.setCollegeId(collegeId);
		activitySku.setBgSkuId(bgSkuForActivity.getBgSkuId());
		activitySku.setOriginPrice(bgSkuForActivity.getOriginPrice());
		activitySku.setActivityPrice(bgSkuForActivity.getActivityPrice());
		activitySku.setSkuType(bgSkuForActivity.getSkuType());
		activitySku.setStock(bgSkuForActivity.getStock());
		activitySku.setStorageType(bgSkuForActivity.getDistributeType());
		activitySku.imageUrl = bgSkuForActivity.getImageUrl();
		activitySku.setStorageId(bgSkuForActivity.getStorageId());
		return activitySku;
	}

}
