package com.mall.admin.vo.goods.dto;

import com.mall.admin.vo.goods.BgGoods;

public class BgGoodsDto {
	private BgGoods bgGoods;
	private long bgSkuId;
	private String wmsGoodsGbms;

	public BgGoods getBgGoods() {
		return bgGoods;
	}

	public void setBgGoods(BgGoods bgGoods) {
		this.bgGoods = bgGoods;
	}

	public long getBgSkuId() {
		return bgSkuId;
	}

	public void setBgSkuId(long bgSkuId) {
		this.bgSkuId = bgSkuId;
	}

	public String getWmsGoodsGbms() {
		return wmsGoodsGbms;
	}

	public void setWmsGoodsGbms(String wmsGoodsGbms) {
		this.wmsGoodsGbms = wmsGoodsGbms;
	}

}
