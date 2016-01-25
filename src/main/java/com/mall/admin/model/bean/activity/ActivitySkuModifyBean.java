package com.mall.admin.model.bean.activity;

/**
 * 活动商品对应的sku修改对象类
 * 
 * @author Administrator
 *
 */
public class ActivitySkuModifyBean {

	/** 对应的活动商品sku的id值 */
	private long activityBgSkuId;
	/** 原价 */
	private String originPrice;
	/** 活动价 */
	private String activityPrice;
	/** 限售数量 */
	private int stock;

	public long getActivityBgSkuId() {
		return activityBgSkuId;
	}

	public void setActivityBgSkuId(long activityBgSkuId) {
		this.activityBgSkuId = activityBgSkuId;
	}

	public String getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(String originPrice) {
		this.originPrice = originPrice;
	}

	public String getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(String activityPrice) {
		this.activityPrice = activityPrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
