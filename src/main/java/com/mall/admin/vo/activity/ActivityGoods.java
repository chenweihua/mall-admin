package com.mall.admin.vo.activity;

import java.util.Date;

/**
 * 活动下学校的商品
 * 
 * @author Administrator
 *
 */
public class ActivityGoods {
	private long activityGoodsId;
	private long bgGoodsId;
	private long activityBgGoodsId;
	private String activityGoodsName;
	private String activityGoodsSubname;
	private long collegeId;
	private long activityId;
	private String description;
	private String remark;
	private String unit;
	private String imageUrl;
	private int weight;
	private int maxNum;
	private int goodsType;
	private int goodsStatus;
	private int isDel;
	private Date beginTime;
	private Date endTime;
	private Date showTime;
	private int isSeckill;
	/**
	 * 产品包装
	 */
	private String packageSpec;
	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 保质期
	 */
	private String shelfLife;
	private String saleSpec;
	private String originPlace;
	private long storageId;

	public int getIsSeckill() {
		return isSeckill;
	}

	public void setIsSeckill(int isSeckill) {
		this.isSeckill = isSeckill;
	}

	public long getActivityBgGoodsId() {
		return activityBgGoodsId;
	}

	public void setActivityBgGoodsId(long activityBgGoodsId) {
		this.activityBgGoodsId = activityBgGoodsId;
	}

	public long getActivityGoodsId() {
		return activityGoodsId;
	}

	public void setActivityGoodsId(long activityGoodsId) {
		this.activityGoodsId = activityGoodsId;
	}

	public long getBgGoodsId() {
		return bgGoodsId;
	}

	public String getPackageSpec() {
		return packageSpec;
	}

	public void setPackageSpec(String packageSpec) {
		this.packageSpec = packageSpec;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}

	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
	}

	public String getActivityGoodsName() {
		return activityGoodsName;
	}

	public void setActivityGoodsName(String activityGoodsName) {
		this.activityGoodsName = activityGoodsName;
	}

	public String getActivityGoodsSubname() {
		return activityGoodsSubname;
	}

	public void setActivityGoodsSubname(String activityGoodsSubname) {
		this.activityGoodsSubname = activityGoodsSubname;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public int getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(int goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getShowTime() {
		return showTime;
	}

	public void setShowTime(Date showTime) {
		this.showTime = showTime;
	}

	public String getSaleSpec() {
		return saleSpec;
	}

	public void setSaleSpec(String saleSpec) {
		this.saleSpec = saleSpec;
	}

	public String getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}

	public long getStorageId() {
		return storageId;
	}

	public void setStorageId(long storageId) {
		this.storageId = storageId;
	}

	/**
	 * 根据后台活动商品模板，设置活动商品id
	 * 
	 * @param bean
	 * @return
	 */
	public static ActivityGoods initActivityGoods(BgGoodsForActivity bean, long collegeId) {
		ActivityGoods activityGoods = new ActivityGoods();
		activityGoods.setBgGoodsId(bean.getBgGoodsId());
		activityGoods.setActivityId(bean.getActivityId());
		activityGoods.setCollegeId(collegeId);
		activityGoods.setActivityBgGoodsId(bean.getActivityBgGoodsId());
		activityGoods.setActivityGoodsName(bean.getBgGoodsName());
		activityGoods.setActivityGoodsSubname(bean.getBgGoodsSubname());
		activityGoods.setRemark(bean.getRemark());
		activityGoods.setDescription(bean.getDescription());
		activityGoods.setUnit(bean.getUnit());
		activityGoods.setImageUrl(bean.getImageUrl());
		activityGoods.setMaxNum(bean.getMaxNum());
		activityGoods.setWeight(bean.getWeight());
		activityGoods.setShowTime(bean.getShowTime());
		activityGoods.setBeginTime(bean.getBeginTime());
		activityGoods.setEndTime(bean.getEndTime());
		activityGoods.setGoodsStatus(bean.getGoodsStatus());
		activityGoods.setGoodsType(bean.getGoodsType());
		activityGoods.setIsDel(bean.getIsDel());
		activityGoods.setIsSeckill(bean.getIsSeckill());
		
		activityGoods.setPackageSpec(bean.getPackageSpec());
		activityGoods.setBrand(bean.getBrand());
		activityGoods.setShelfLife(bean.getShelfLife());
		activityGoods.setSaleSpec(bean.getSaleSpec());
		activityGoods.setOriginPlace(bean.getOriginPlace());
		activityGoods.setStorageId(bean.getStorageId());
		return activityGoods;
	}

}
