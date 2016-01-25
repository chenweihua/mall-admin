package com.mall.admin.model.bean.activity;

import java.util.List;

/**
 * 添加商品时，提交的数据。
 * 
 * @author Administrator
 *
 */
public class ActivityGoodsApplyBean {

	private long bgGoodsId;
	private int weight;
	private int stock;
	private int maxNum;
	private String activityPrice;
	private String originPrice;
	private List<ActivitySkuApplyBean> skuListBean;
	private String storageType;
	private String goodsStatus;

	public long getBgGoodsId() {
		return bgGoodsId;
	}

	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public String getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(String activityPrice) {
		this.activityPrice = activityPrice;
	}

	public List<ActivitySkuApplyBean> getSkuListBean() {
		return skuListBean;
	}

	public void setSkuListBean(List<ActivitySkuApplyBean> skuListBean) {
		this.skuListBean = skuListBean;
	}

	public String getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(String originPrice) {
		this.originPrice = originPrice;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	

}
