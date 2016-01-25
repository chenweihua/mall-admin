package com.mall.admin.vo.activity;

import java.util.Date;
import java.util.List;

import com.mall.admin.vo.goods.BgGoods;

/**
 * 活动后台商品模板
 * 
 * @author Administrator
 *
 */
public class BgGoodsForActivity {
	private long activityBgGoodsId;
	private long activityId;
	private long bgGoodsId;
	private String bgGoodsName;
	private String bgGoodsSubname;
	private String description;
	private String remark;
	private String unit;
	private String imageUrl;
	private int maxNum;
	private int weight;
	private int goodsType;
	private int goodsStatus;
	private Date beginTime;
	private Date endTime;
	private Date showTime;
	private Date createTime;
	private Date updateTime;
	private long operator;
	private int isDel;
	private int isSeckill;
	private int storageType;
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
	
	private List<BgSkuForActivity> bgSkuForActivitys;

	public int getStorageType() {
		return storageType;
	}

	public void setStorageType(int storageType) {
		this.storageType = storageType;
	}

	public int getIsSeckill() {
		return isSeckill;
	}

	public void setIsSeckill(int isSeckill) {
		this.isSeckill = isSeckill;
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

	public long getActivityBgGoodsId() {
		return activityBgGoodsId;
	}

	public void setActivityBgGoodsId(long activityBgGoodsId) {
		this.activityBgGoodsId = activityBgGoodsId;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public long getBgGoodsId() {
		return bgGoodsId;
	}

	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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

	public String getBgGoodsName() {
		return bgGoodsName;
	}

	public void setBgGoodsName(String bgGoodsName) {
		this.bgGoodsName = bgGoodsName;
	}

	public String getBgGoodsSubname() {
		return bgGoodsSubname;
	}

	public void setBgGoodsSubname(String bgGoodsSubname) {
		this.bgGoodsSubname = bgGoodsSubname;
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
	
	public List<BgSkuForActivity> getBgSkuForActivitys() {
		return bgSkuForActivitys;
	}

	public void setBgSkuForActivitys(List<BgSkuForActivity> bgSkuForActivitys) {
		this.bgSkuForActivitys = bgSkuForActivitys;
	}

	/**
	 * 根据后台Goods模板生成活动Goods模板对象
	 * 
	 * @param bgGoods
	 *                后台Goods模板对象
	 * @param activityId
	 *                活动id
	 * @param maxNum
	 *                限购数量
	 * @param weight
	 *                权重
	 * @param activityType
	 *                商品所属活动类型 0：秒杀活动，1：日常活动
	 * @return
	 */
	public static BgGoodsForActivity initByBgGoods(BgGoods bgGoods, long activityId, int maxNum, int weight,
			long userId, Date beginTime, Date endTime, Date showTime, int activityType, int storageType, int goodsStatus) {
		BgGoodsForActivity bean = new BgGoodsForActivity();
		bean.setActivityId(activityId);
		bean.setBgGoodsId(bgGoods.getBgGoodsId());
		bean.setBgGoodsName(bgGoods.getBgGoodsName());
		bean.setBgGoodsSubname(bgGoods.getBgGoodsSubname());
		bean.setDescription(bgGoods.getDescription());
		bean.setRemark(bgGoods.getRemark());
		bean.setUnit(bgGoods.getUnit());
		bean.setImageUrl(bgGoods.getImageUrl());
		bean.setMaxNum(maxNum);
		bean.setWeight(weight);
		bean.setGoodsStatus(goodsStatus);
		bean.setGoodsType(bgGoods.getGoodsType());
		bean.setOperator(userId);
		bean.setBeginTime(beginTime);
		bean.setEndTime(endTime);
		bean.setShowTime(showTime);
		bean.setIsSeckill(activityType);
		bean.storageType = storageType;
		
		bean.setPackageSpec(bgGoods.getPackageSpec());
		bean.setBrand(bgGoods.getBrand());
		bean.setShelfLife(bgGoods.getShelfLife());
		bean.setSaleSpec(bgGoods.getSaleSpec());
		bean.setOriginPlace(bgGoods.getOriginPlace());
		bean.setStorageId(bgGoods.getStorageId());
		return bean;
	}
}
