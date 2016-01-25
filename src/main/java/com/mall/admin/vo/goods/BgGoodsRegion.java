package com.mall.admin.vo.goods;
/**
 * 后台标识goods与在不同范围的价格，主要用于统一定价
 *
 * @date 2015年7月14日 下午3:44:27
 * @author zhangshuai
 */
public class BgGoodsRegion {
	private long bgGoodsRegionId;

	private long bgGoodsId;

	private long regionId;

	private int regionType;
	public final static int REGION_TYPE_ALL = 0;
	public final static int REGION_TYPE_STORAGE = 1;
	public final static int REGION_TYPE_CITY = 2;

	private long originPrice;

	private long wapPrice;

	private long appPrice;

	private long maxNum;

	private long stock;

	private int status;

	private long weight;

	private int isDel;

	public long getBgGoodsRegionId() {
		return bgGoodsRegionId;
	}

	public void setBgGoodsRegionId(long bgGoodsRegionId) {
		this.bgGoodsRegionId = bgGoodsRegionId;
	}

	public long getBgGoodsId() {
		return bgGoodsId;
	}

	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
	}

	public long getRegionId() {
		return regionId;
	}

	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}

	public int getRegionType() {
		return regionType;
	}

	public void setRegionType(int regionType) {
		this.regionType = regionType;
	}

	public long getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(long originPrice) {
		this.originPrice = originPrice;
	}

	public long getWapPrice() {
		return wapPrice;
	}

	public void setWapPrice(long wapPrice) {
		this.wapPrice = wapPrice;
	}

	public long getAppPrice() {
		return appPrice;
	}

	public void setAppPrice(long appPrice) {
		this.appPrice = appPrice;
	}

	public long getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(long maxNum) {
		this.maxNum = maxNum;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
}