package com.mall.admin.model.bean.synchwmsgoods;

import java.util.List;

/**
 * "goodsCode":"100001","goodsName":"暖壶","saleUnit":"袋","selfLife":14,
 * "brandName":"品牌1","yn":"true",
 * "gcateName":"笔记本电脑","packSizeName":"1X2X3","totalWeight"
 * :40kg,"packLength":"1米",
 * "packWidth":"0.5米","packHeight":"0.5米","saleSize":"1个/箱",
 * "gbkList":[{"cgbk":"4103","cgbk":"4104"}]
 * 
 * @author Administrator
 *
 */
public class SynchWmsGoodsBean {

	private String goodsCode;
	private String goodsName;
	private String saleUnit;
	private String selfLife;
	private String brandName;
	private boolean yn;
	private String gcateName;
	private String packSizeName;
	private String totalWeight;
	private String packLength;
	private String packWidth;
	private String packHeight;
	private String saleSize;
	private List<GBKBean> gbkList;

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSaleUnit() {
		return saleUnit;
	}

	public void setSaleUnit(String saleUnit) {
		this.saleUnit = saleUnit;
	}

	public String getSelfLife() {
		return selfLife;
	}

	public void setSelfLife(String selfLife) {
		this.selfLife = selfLife;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public boolean isYn() {
		return yn;
	}

	public void setYn(boolean yn) {
		this.yn = yn;
	}

	public String getGcateName() {
		return gcateName;
	}

	public void setGcateName(String gcateName) {
		this.gcateName = gcateName;
	}

	public String getPackSizeName() {
		return packSizeName;
	}

	public void setPackSizeName(String packSizeName) {
		this.packSizeName = packSizeName;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getPackLength() {
		return packLength;
	}

	public void setPackLength(String packLength) {
		this.packLength = packLength;
	}

	public String getPackWidth() {
		return packWidth;
	}

	public void setPackWidth(String packWidth) {
		this.packWidth = packWidth;
	}

	public String getPackHeight() {
		return packHeight;
	}

	public void setPackHeight(String packHeight) {
		this.packHeight = packHeight;
	}

	public String getSaleSize() {
		return saleSize;
	}

	public void setSaleSize(String saleSize) {
		this.saleSize = saleSize;
	}

	public List<GBKBean> getGbkList() {
		return gbkList;
	}

	public void setGbkList(List<GBKBean> gbkList) {
		this.gbkList = gbkList;
	}

}
