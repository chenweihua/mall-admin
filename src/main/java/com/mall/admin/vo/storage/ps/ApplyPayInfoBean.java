package com.mall.admin.vo.storage.ps;

public class ApplyPayInfoBean {

	public String storageName;
	public String sellerName;
	public String applyCode;
	public String status;
	public String applyTime;
	public String payTime;
	public String applyUser;
	public long payUserId;
	public String payUserName;
	public long summoney;
	public long accountmoney;
	public long adjustmoney;// 调整金额字段

	public long getSummoney() {
		return summoney;
	}

	public void setSummoney(long summoney) {
		this.summoney = summoney;
	}

	public long getAccountmoney() {
		return accountmoney;
	}

	public void setAccountmoney(long accountmoney) {
		this.accountmoney = accountmoney;
	}

	public String getPayUserName() {
		return payUserName;
	}

	public void setPayUserName(String payUserName) {
		this.payUserName = payUserName;
	}

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public long getPayUserId() {
		return payUserId;
	}

	public void setPayUserId(long payUserId) {
		this.payUserId = payUserId;
	}

}
