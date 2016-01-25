package com.mall.admin.model.bean.activity;

import java.util.List;

public class ActivityGoodsModifyBean {
	/** 后台id */
	private long activityBgGoodsId;
	/** 权重 */
	private int weight;
	/** 限购数量 */
	private int maxNum;
	/**
	 * 售卖状态 1：待售 2：在售 3：售罄
	 */
	private int status;
	/** 对应的sku修改数据 */
	private List<ActivitySkuModifyBean> modifySkuList;

	public long getActivityBgGoodsId() {
		return activityBgGoodsId;
	}

	public void setActivityBgGoodsId(long activityBgGoodsId) {
		this.activityBgGoodsId = activityBgGoodsId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<ActivitySkuModifyBean> getModifySkuList() {
		return modifySkuList;
	}

	public void setModifySkuList(List<ActivitySkuModifyBean> modifySkuList) {
		this.modifySkuList = modifySkuList;
	}

}
