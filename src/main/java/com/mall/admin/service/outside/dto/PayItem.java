package com.mall.admin.service.outside.dto;

public class PayItem {
	private long orderId;
	private int type;
	public static final int TYPE_H5 = 0;
	public static final int TYPE_APP = 1;
	private int appType;
	public static final int APP_TYPE_BAO = 0;
	public static final int APP_TYPE_WEIXIN = 1;
	private String payId;
	private long fee;
	
	public PayItem() {
	}
	public PayItem(long orderId, int type, int appType, String payId, long fee) {
		super();
		this.orderId = orderId;
		this.type = type;
		this.appType = appType;
		this.payId = payId;
		this.fee = fee;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAppType() {
		return appType;
	}
	public void setAppType(int appType) {
		this.appType = appType;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public long getFee() {
		return fee;
	}
	public void setFee(long fee) {
		this.fee = fee;
	}
}
