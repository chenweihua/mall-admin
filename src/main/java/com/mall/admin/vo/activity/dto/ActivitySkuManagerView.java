package com.mall.admin.vo.activity.dto;

import com.mall.admin.vo.activity.BgSkuForActivity;
import com.mall.admin.vo.goods.BgGoods;

public class ActivitySkuManagerView {

	private long activityBgSkuId;
	private long bgSkuId;
	private long originPrice;
	private long activityPrice;
	private int stock;
	private String goodsName;
	private String describe;
	private String remark;
	private String unit;

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

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public static ActivitySkuManagerView initBean(BgSkuForActivity bgSkuForActivity, BgGoods bgGoods) {
		ActivitySkuManagerView bean = new ActivitySkuManagerView();
		bean.activityBgSkuId = bgSkuForActivity.getActivityBgSkuId();
		bean.bgSkuId = bgSkuForActivity.getBgSkuId();
		bean.originPrice = bgSkuForActivity.getOriginPrice();
		bean.activityPrice = bgSkuForActivity.getActivityPrice();
		bean.stock = bgSkuForActivity.getStock();
		bean.goodsName = bgGoods.getBgGoodsName();
		bean.describe = bgGoods.getDescription();
		bean.remark = bgGoods.getRemark();
		bean.unit = bgGoods.getUnit();
		return bean;

	}
}
