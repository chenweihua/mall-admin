package com.mall.admin.vo.storage.ps;

import java.util.ArrayList;
import java.util.List;

public class ApplyPayDetailBean {

	public int count;
	public String applyCode;
	public String statusStr;
	public int status;
	public String applyTime;
	public String payTime;
	public String applyUser;
	public String storageName;
	public String sellerName;
	public int recordNum;
	public String remark;
	public List<ApplyPayRecordDetailBean> recordDetailList = new ArrayList<ApplyPayRecordDetailBean>();

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
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

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<ApplyPayRecordDetailBean> getRecordDetailList() {
		return recordDetailList;
	}

	public void setRecordDetailList(
			List<ApplyPayRecordDetailBean> recordDetailList) {
		this.recordDetailList = recordDetailList;
	}

	public int getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
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

}
