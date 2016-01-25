package com.mall.admin.vo.order;

/**
 * 商品打印信息
 * 
 * @author Administrator
 *
 */
public class PrintInfo {
	/** 商品名称 */
	private String wmsGoodsName;
	/** 商品条码（前期使用国标码，后期可能使用商品编码） */
	private String wmsGoodsNo;
	/** 组合数量 */
	private int num;
	/** 购买数量 */
	private int skuNum;
	/** skuId */
	private int skuId;

	public int getSkuId() {
		return skuId;
	}

	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}

	public String getWmsGoodsName() {
		return wmsGoodsName;
	}

	public void setWmsGoodsName(String wmsGoodsName) {
		this.wmsGoodsName = wmsGoodsName;
	}

	public String getWmsGoodsNo() {
		return wmsGoodsNo;
	}

	public void setWmsGoodsNo(String wmsGoodsNo) {
		this.wmsGoodsNo = wmsGoodsNo;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(int skuNum) {
		this.skuNum = skuNum;
	}

}
