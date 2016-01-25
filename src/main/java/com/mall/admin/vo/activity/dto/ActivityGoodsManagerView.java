package com.mall.admin.vo.activity.dto;

import java.text.SimpleDateFormat;

import com.mall.admin.vo.activity.BgGoodsForActivity;

/**
 * 活动商品管理视图对象
 * 
 * @author Administrator
 *
 */
public class ActivityGoodsManagerView {

	private long activityBgGoodsId;
	private String goodsName;
	private String activityName;
	private String describe;
	private String remark;
	private String uint;
	private String beginTime;
	private String endTime;
	private String showTime;
	private int status;
	private int weight;
	private int maxNum;
	private int storageType;
	private int goodsType;

	public long getActivityBgGoodsId() {
		return activityBgGoodsId;
	}

	public void setActivityBgGoodsId(long activityBgGoodsId) {
		this.activityBgGoodsId = activityBgGoodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
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

	public String getUint() {
		return uint;
	}

	public void setUint(String uint) {
		this.uint = uint;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	
	public int getStorageType() {
		return storageType;
	}

	public void setStorageType(int storageType) {
		this.storageType = storageType;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public static ActivityGoodsManagerView initViewBean(BgGoodsForActivity bean) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ActivityGoodsManagerView viewBean = new ActivityGoodsManagerView();
		viewBean.activityBgGoodsId = bean.getActivityBgGoodsId();
		viewBean.goodsName = bean.getBgGoodsName();
		viewBean.describe = bean.getDescription();
		viewBean.remark = bean.getRemark();
		viewBean.uint = bean.getUnit();
		if (bean.getBeginTime() != null) {
			viewBean.beginTime = format.format(bean.getBeginTime());
		}
		if (bean.getEndTime() != null) {
			viewBean.endTime = format.format(bean.getEndTime());
		}
		if (bean.getShowTime() != null) {
			viewBean.showTime = format.format(bean.getShowTime());
		}
		viewBean.weight = bean.getWeight();
		viewBean.maxNum = bean.getMaxNum();
		viewBean.status = bean.getGoodsStatus();
		viewBean.storageType = bean.getStorageType();
		viewBean.goodsType = bean.getGoodsType();
		return viewBean;
	}
}
