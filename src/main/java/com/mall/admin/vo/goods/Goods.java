package com.mall.admin.vo.goods;

/**
 * 前台goods，是后台goods加入了学校的维度
 *
 * @date 2015年7月14日 下午4:15:28
 * @author zhangshuai
 */
public class Goods {
	
	public Goods(){}
	
	public Goods(BgGoods bgGoods) {
		this.bgGoodsId = bgGoods.getBgGoodsId();
		this.bgGoodsName = bgGoods.getBgGoodsName();
		this.bgGoodsSubname = bgGoods.getBgGoodsSubname();
		this.description = bgGoods.getDescription();
		this.remark = bgGoods.getRemark();
		this.categoryId = bgGoods.getCategoryId();
		this.unit = bgGoods.getUnit();
		this.imageUrl = bgGoods.getImageUrl();
		this.weight = bgGoods.getWeight();
		this.maxNum = bgGoods.getMaxNum();
		this.goodsType = bgGoods.getGoodsType();
		this.status = bgGoods.getGoodsStatus();
		this.isDel = 0;
		this.saleSpec = bgGoods.getSaleSpec();
		this.originPlace = bgGoods.getOriginPlace();
		this.packageSpec = bgGoods.getPackageSpec();
		this.brand = bgGoods.getBrand();
		this.shelfLife = bgGoods.getShelfLife();
		this.storageId = bgGoods.getStorageId();
	}

	private long goodsId;

	private long bgGoodsId;

	private long collegeId;

	private String bgGoodsName;

	private String bgGoodsSubname;

	private String description;

	private String remark;

	private long categoryId;

	private String unit;

	private String imageUrl;

	private long weight;

	private long maxNum;

	private int goodsType;

	private int status;

	private int isDel;
	
	private String saleSpec;
	
	private String originPlace;
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

	private long storageId;

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public long getBgGoodsId() {
		return bgGoodsId;
	}

	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
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

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
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

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public long getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(long maxNum) {
		this.maxNum = maxNum;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
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

	public long getStorageId() {
		return storageId;
	}

	public void setStorageId(long storageId) {
		this.storageId = storageId;
	}
}