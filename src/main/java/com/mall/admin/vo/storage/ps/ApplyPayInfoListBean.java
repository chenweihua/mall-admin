package com.mall.admin.vo.storage.ps;

import java.util.ArrayList;
import java.util.List;

public class ApplyPayInfoListBean {
	public int sum = 0;
	public int count=0;
	public List<ApplyPayInfoBean> infoList=new ArrayList<ApplyPayInfoBean>();
	
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<ApplyPayInfoBean> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<ApplyPayInfoBean> infoList) {
		this.infoList = infoList;
	};
}
