package com.mall.admin.vo.activity;

import java.util.Date;

import com.mall.admin.vo.goods.BgSku;

/**
 * 后台sku模板
 * 
 * @author Administrator
 *
 */
public class BgSkuForActivity {
	private long activityBgSkuId;
	private long bgSkuId;
	private long activityBgGoodsId;
	private long originPrice;
	private long activityPrice;
	private int stock;
	private int skuType;
	private String imageUrl;
	private Date createTime;
	private Date updateTime;
	private int distributeType;
	private long operator;
	private long storageId;
	
	private String bgGoodsName;
	private String unit;
	private String describe;

	public int getDistributeType() {
		return distributeType;
	}

	public void setDistributeType(int distributeType) {
		this.distributeType = distributeType;
	}

	public long getActivityBgSkuId() {
		return activityBgSkuId;
	}

	public void setActivityBgSkuId(long activityBgSkuId) {
		this.activityBgSkuId = activityBgSkuId;
	}

	public long getBgSkuId() {
		return bgSkuId;
	}

	public void setBgSkuId(long bgSkuId) {
		this.bgSkuId = bgSkuId;
	}

	public long getActivityBgGoodsId() {
		return activityBgGoodsId;
	}

	public void setActivityBgGoodsId(long activityBgGoodsId) {
		this.activityBgGoodsId = activityBgGoodsId;
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getSkuType() {
		return skuType;
	}

	public void setSkuType(int skuType) {
		this.skuType = skuType;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
	}

	public long getStorageId() {
		return storageId;
	}

	public void setStorageId(long storageId) {
		this.storageId = storageId;
	}
	

	public String getBgGoodsName() {
		return bgGoodsName;
	}

	public void setBgGoodsName(String bgGoodsName) {
		this.bgGoodsName = bgGoodsName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public static BgSkuForActivity initByBgSku(BgSku bgSku, int stock, int price) {
		return null;
	}

	public static BgSkuForActivity initBgSkuForActivity(BgSku bgsku, long activityBgGoodsId, long activtityPrice,
			long originPrice, int stock, long userId, int distributeType) {
		BgSkuForActivity bean = new BgSkuForActivity();
		bean.setBgSkuId(bgsku.getBgSkuId());
		bean.setActivityBgGoodsId(activityBgGoodsId);
		bean.setOriginPrice(originPrice);
		bean.setActivityPrice(activtityPrice);
		bean.setStock(stock);
		bean.setSkuType(bgsku.getSkuType());
		bean.setOperator(userId);
		bean.setDistributeType(distributeType);
		bean.imageUrl = bgsku.getImageUrl();
		bean.setStorageId(bgsku.getStorageId());
		return bean;
	}
}
