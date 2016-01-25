package com.mall.admin.model.bean.activity;

/**
 * 申请提交时的数据
 * 
 * @author Administrator
 *
 */
public class ActivitySkuApplyBean {

	private long skuId;
	private int stock;
	private String activityPrice;
	private String originPrice;

	public long getSkuId() {
		return skuId;
	}

	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(String activityPrice) {
		this.activityPrice = activityPrice;
	}

	public String getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(String originPrice) {
		this.originPrice = originPrice;
	}

}
