package com.mall.admin.vo.merchant;

import java.sql.Timestamp;

public class ThirdPartyOrder {

	private long orderId;
	private String orderCode;
	private long userId;
	private long collegeId;
	private String merchantNo;
	private String merchantName;
	private int merchantType;
	private long merchantAreaId;
	private long totalPay;
	private long onlinePay;
	private String onlinePayId;
	private int onlinePayType;
	private long couponId;
	private long couponPay;
	private long firstSub;
	private long fullSub;
	private int status;
	private int device;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Timestamp payTime;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public int getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(int merchantType) {
		this.merchantType = merchantType;
	}

	public long getMerchantAreaId() {
		return merchantAreaId;
	}

	public void setMerchantAreaId(long merchantAreaId) {
		this.merchantAreaId = merchantAreaId;
	}

	public long getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(long totalPay) {
		this.totalPay = totalPay;
	}

	public long getOnlinePay() {
		return onlinePay;
	}

	public void setOnlinePay(long onlinePay) {
		this.onlinePay = onlinePay;
	}

	public String getOnlinePayId() {
		return onlinePayId;
	}

	public void setOnlinePayId(String onlinePayId) {
		this.onlinePayId = onlinePayId;
	}

	public int getOnlinePayType() {
		return onlinePayType;
	}

	public void setOnlinePayType(int onlinePayType) {
		this.onlinePayType = onlinePayType;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public long getCouponPay() {
		return couponPay;
	}

	public void setCouponPay(long couponPay) {
		this.couponPay = couponPay;
	}

	public long getFirstSub() {
		return firstSub;
	}

	public void setFirstSub(long firstSub) {
		this.firstSub = firstSub;
	}

	public long getFullSub() {
		return fullSub;
	}

	public void setFullSub(long fullSub) {
		this.fullSub = fullSub;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDevice() {
		return device;
	}

	public void setDevice(int device) {
		this.device = device;
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

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

}
