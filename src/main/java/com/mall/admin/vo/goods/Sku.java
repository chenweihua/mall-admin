package com.mall.admin.vo.goods;

/**
 * 前台Sku，后台sku加入学校属性
 *
 * @date 2015年7月14日 下午4:21:13
 * @author zhangshuai
 */
public class Sku {

	private long skuId;

	private long bgSkuId;

	private long collegeId;

	private long goodsId;

	private long originPrice;

	private long appPrice;

	private long wapPrice;

	private long stock;

	private int skuType;

	private int skuStatus;
	
	private String imageUrl;

	private int distributeType;
	public static final int RDC_DISTRIBUTE_TYPE = 0;
	public static final int LDC_DISTRIBUTE_TYPE = 1;
	public static final int VM_DISTRIBUTE_TYPE = 2;

	private int isDel;
	private long storageId;

	public Sku() {
	}

	public Sku(BgSku bgSku) {
		this.bgSkuId = bgSku.getBgSkuId();
		this.originPrice = bgSku.getOriginPrice();
		this.appPrice = bgSku.getAppPrice();
		this.wapPrice = bgSku.getWapPrice();
		this.stock = bgSku.getStock();
		this.skuType = bgSku.getSkuType();
		this.skuStatus = bgSku.getSkuStatus();
		this.imageUrl = bgSku.getImageUrl();
		this.isDel = 0;
		this.storageId = bgSku.getStorageId();
	}

	public long getSkuId() {
		return skuId;
	}

	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	public long getBgSkuId() {
		return bgSkuId;
	}

	public void setBgSkuId(long bgSkuId) {
		this.bgSkuId = bgSkuId;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public long getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(long originPrice) {
		this.originPrice = originPrice;
	}

	public long getAppPrice() {
		return appPrice;
	}

	public void setAppPrice(long appPrice) {
		this.appPrice = appPrice;
	}

	public long getWapPrice() {
		return wapPrice;
	}

	public void setWapPrice(long wapPrice) {
		this.wapPrice = wapPrice;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public int getSkuType() {
		return skuType;
	}

	public void setSkuType(int skuType) {
		this.skuType = skuType;
	}

	public int getSkuStatus() {
		return skuStatus;
	}

	public void setSkuStatus(int skuStatus) {
		this.skuStatus = skuStatus;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getDistributeType() {
		return distributeType;
	}

	public void setDistributeType(int distributeType) {
		this.distributeType = distributeType;
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
}