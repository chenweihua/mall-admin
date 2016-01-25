package com.mall.admin.vo.order;

import java.sql.Timestamp;
import java.util.List;

import com.google.common.collect.Lists;
import com.mall.admin.vo.category.ThirdCategory;
import com.mall.admin.vo.wms.WmsGoods4BgSkuInfo;

public class ChildOrderDetail {

	private long childOrderDetailId;
	private long childOrderId;
	private long orderId;
	private long userId;
	private long collegeId;
	private long goodsId;
	private long skuId;
	private long bgSkuId;
	private String skuName;
	private String skuSubName;
	private int skuNum;
	private int skuOriginPrice;
	private int skuPrice;
	private int status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private int skuWithdrawedNum;
	/** 对应供应链侧商品名称 */
	private List<WmsGoods4BgSkuInfo> wmsGoods4BgSkuList;
	
	private ThirdCategory thirdCategory;
	
	private List<String> skuPropertySpecList = Lists.newArrayList();

	public List<String> getSkuPropertySpecList() {
		return skuPropertySpecList;
	}

	public void setSkuPropertySpecList(List<String> skuPropertySpecList) {
		this.skuPropertySpecList = skuPropertySpecList;
	}

	public ThirdCategory getThirdCategory() {
		return thirdCategory;
	}

	public void setThirdCategory(ThirdCategory thirdCategory) {
		this.thirdCategory = thirdCategory;
	}

	public long getChildOrderDetailId() {
		return childOrderDetailId;
	}

	public void setChildOrderDetailId(long childOrderDetailId) {
		this.childOrderDetailId = childOrderDetailId;
	}

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

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public long getSkuId() {
		return skuId;
	}

	public void setSkuId(long skuId) {
		this.skuId = skuId;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuSubName() {
		return skuSubName;
	}

	public void setSkuSubName(String skuSubName) {
		this.skuSubName = skuSubName;
	}

	public int getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(int skuNum) {
		this.skuNum = skuNum;
	}

	public int getSkuOriginPrice() {
		return skuOriginPrice;
	}

	public void setSkuOriginPrice(int skuOriginPrice) {
		this.skuOriginPrice = skuOriginPrice;
	}

	public int getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(int skuPrice) {
		this.skuPrice = skuPrice;
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

	public long getChildOrderId() {
		return childOrderId;
	}

	public void setChildOrderId(long childOrderId) {
		this.childOrderId = childOrderId;
	}

	public int getSkuWithdrawedNum() {
		return skuWithdrawedNum;
	}

	public void setSkuWithdrawedNum(int skuWithdrawedNum) {
		this.skuWithdrawedNum = skuWithdrawedNum;
	}

	public List<WmsGoods4BgSkuInfo> getWmsGoods4BgSkuList() {
		return wmsGoods4BgSkuList;
	}

	public void setWmsGoods4BgSkuList(List<WmsGoods4BgSkuInfo> wmsGoods4BgSkuList) {
		this.wmsGoods4BgSkuList = wmsGoods4BgSkuList;
	}

	public long getBgSkuId() {
		return bgSkuId;
	}

	public void setBgSkuId(long bgSkuId) {
		this.bgSkuId = bgSkuId;
	}

}
