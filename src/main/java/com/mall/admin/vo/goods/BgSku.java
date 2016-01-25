package com.mall.admin.vo.goods;

import java.sql.Timestamp;
import java.util.Map;

import com.mall.admin.constant.Constants;
import com.mall.admin.util._;

/**
 * 后台sku，作为前台sku的模板，主要用于统一设置
 *
 * @date 2015年7月14日 下午3:45:26
 * @author zhangshuai
 */
public class BgSku {
	
	private long bgSkuId;

	private long bgGoodsId;

	private long originPrice;

	private long appPrice;

	private long wapPrice;

	private long stock;

	private int skuType;

	private int skuStatus;
	
	private String imageUrl;

	private Timestamp createTime;

	private Timestamp updateTime;

	private long operator;

	private String wmsGoodsGbms;

	private int isDel;
	
	private long storageId;
	//propertyNameId - skuProperty
	private Map<Long,PropertyValue> skuPropertyMap;

	public BgSku() {
		super();
		this.setSkuStatus(Constants.SKU_STATUS_DEFAULT);
		this.setOriginPrice(Constants.SKU_ORIGIN_PRICE_DEFAULT);
		this.setAppPrice(Constants.SKU_APP_PRICE_DEFAULT);
		this.setWapPrice(Constants.SKU_WEB_PRICE_DEFAULT);
		this.setStock(Constants.SKU_STOCK_DEFAULT);
		this.createTime = _.currentTime();
		this.updateTime = _.currentTime();
		this.isDel = 0;
	}

	public long getBgSkuId() {
		return bgSkuId;
	}

	public void setBgSkuId(long bgSkuId) {
		this.bgSkuId = bgSkuId;
	}

	public long getBgGoodsId() {
		return bgGoodsId;
	}

	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
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

	public String getWmsGoodsGbms() {
		return wmsGoodsGbms;
	}

	public void setWmsGoodsGbms(String wmsGoodsGbms) {
		this.wmsGoodsGbms = wmsGoodsGbms;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
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

	public Map<Long, PropertyValue> getSkuPropertyMap() {
		return skuPropertyMap;
	}

	public void setSkuPropertyMap(Map<Long, PropertyValue> skuPropertyMap) {
		this.skuPropertyMap = skuPropertyMap;
	}

}