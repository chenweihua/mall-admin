package com.mall.admin.vo.wms;

/**
 * sku 对应的wmsGoods的信息
 * 
 * @author Administrator
 *
 */
public class WmsGoods4BgSkuInfo {
	//绑定的bgSkuId
	private long bgSkuId;

	/**
	 * 商品名称
	 */
	private String wmsGoodsName;
	/** 商品条码 */
	private String wmsGoodsGbm;
	/** 打包数量 */
	private int skuNum;

	public long getBgSkuId() {
		return bgSkuId;
	}

	public void setBgSkuId(long bgSkuId) {
		this.bgSkuId = bgSkuId;
	}

	public String getWmsGoodsName() {
		return wmsGoodsName;
	}

	public void setWmsGoodsName(String wmsGoodsName) {
		this.wmsGoodsName = wmsGoodsName;
	}

	public String getWmsGoodsGbm() {
		return wmsGoodsGbm;
	}

	public void setWmsGoodsGbm(String wmsGoodsGbm) {
		this.wmsGoodsGbm = wmsGoodsGbm;
	}

	public int getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(int skuNum) {
		this.skuNum = skuNum;
	}

}
