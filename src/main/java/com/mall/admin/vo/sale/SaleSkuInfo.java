package com.mall.admin.vo.sale;

public class SaleSkuInfo {

	private long bgSkuId;
	private long originPrice;
	private String bg_goods_name;
	private String description;
	private String unit;

	public long getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(long originPrice) {
		this.originPrice = originPrice;
	}

	public long getBgSkuId() {
		return bgSkuId;
	}

	public void setBgSkuId(long bgSkuId) {
		this.bgSkuId = bgSkuId;
	}

	public String getBg_goods_name() {
		return bg_goods_name;
	}

	public void setBg_goods_name(String bg_goods_name) {
		this.bg_goods_name = bg_goods_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
