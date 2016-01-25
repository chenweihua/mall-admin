package com.mall.admin.vo.order.dto;

import com.mall.admin.vo.order.PrintInfo;

public class ChildOrderDetailDto {

	private int skuNum;
	private int num;
	private String skuName;
	private String limitedGbm;
	private String skuSubName;
	private int skuId;

	public int getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(int skuNum) {
		this.skuNum = skuNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getLimitedGbm() {
		return limitedGbm;
	}

	public void setLimitedGbm(String limitedGbm) {
		this.limitedGbm = limitedGbm;
	}

	public String getSkuSubName() {
		return skuSubName;
	}

	public void setSkuSubName(String skuSubName) {
		this.skuSubName = skuSubName;
	}

	public int getSkuId() {
		return skuId;
	}

	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}

	public static ChildOrderDetailDto init(PrintInfo printInfo) {
		ChildOrderDetailDto detailDto = new ChildOrderDetailDto();
		detailDto.num = printInfo.getNum();
		detailDto.skuNum = printInfo.getSkuNum() * printInfo.getNum();
		detailDto.skuName = printInfo.getWmsGoodsName();
		detailDto.skuId = printInfo.getSkuId();
		detailDto.limitedGbm = printInfo.getWmsGoodsNo().substring(printInfo.getWmsGoodsNo().length() - 3);
		return detailDto;
	}
}
