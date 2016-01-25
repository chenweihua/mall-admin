package com.mall.admin.vo.order;

import java.sql.Timestamp;

public class Order {

	private long orderId;
	private long userId;
	private long collegeId;
	private String reciverName;
	private String reciverPhone;
	private int totalPay;
	private int onlinePay;
	private String onlinePayId;
	private int onlinePayType;
	private long couponId;
	private int couponPay;
	private int firstSub;
	private int fullSub;
	private int freight;
	private int fillPay;
	private String orderCode;
	private String remark;
	private int status;
	private int device;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int freightSub;
	private Timestamp payTime;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
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

	public String getReciverName() {
		return reciverName;
	}

	public void setReciverName(String reciverName) {
		this.reciverName = reciverName;
	}

	public String getReciverPhone() {
		return reciverPhone;
	}

	public void setReciverPhone(String reciverPhone) {
		this.reciverPhone = reciverPhone;
	}

	public int getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(int totalPay) {
		this.totalPay = totalPay;
	}

	public int getOnlinePay() {
		return onlinePay;
	}

	public void setOnlinePay(int onlinePay) {
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

	public int getCouponPay() {
		return couponPay;
	}

	public void setCouponPay(int couponPay) {
		this.couponPay = couponPay;
	}

	public int getFirstSub() {
		return firstSub;
	}

	public void setFirstSub(int firstSub) {
		this.firstSub = firstSub;
	}

	public int getFullSub() {
		return fullSub;
	}

	public void setFullSub(int fullSub) {
		this.fullSub = fullSub;
	}

	public int getFreight() {
		return freight;
	}

	public void setFreight(int freight) {
		this.freight = freight;
	}

	public int getFillPay() {
		return fillPay;
	}

	public void setFillPay(int fillPay) {
		this.fillPay = fillPay;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public int getDevice() {
		return device;
	}

	public void setDevice(int device) {
		this.device = device;
	}

	public int getFreightSub() {
		return freightSub;
	}

	public void setFreightSub(int freightSub) {
		this.freightSub = freightSub;
	}

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}
}
