package com.mall.admin.vo.activity.dto;

import com.mall.admin.vo.goods.BgGoods;

/**
 * 活动页查询到的后台goods
 * 
 * @author Administrator
 *
 */
public class BgGoodsInActivityView {

	private long bgGoodsId;
	private String bgGoodsName;
	private String bgGoodsRemark;
	private String bgGoodsDesc;
	private int bgGoodsType;
	private String imageUrl;
	private long originPrice;

	public long getBgGoodsId() {
		return bgGoodsId;
	}

	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
	}

	public String getBgGoodsName() {
		return bgGoodsName;
	}

	public void setBgGoodsName(String bgGoodsName) {
		this.bgGoodsName = bgGoodsName;
	}

	public String getBgGoodsRemark() {
		return bgGoodsRemark;
	}

	public void setBgGoodsRemark(String bgGoodsRemark) {
		this.bgGoodsRemark = bgGoodsRemark;
	}

	public int getBgGoodsType() {
		return bgGoodsType;
	}

	public void setBgGoodsType(int bgGoodsType) {
		this.bgGoodsType = bgGoodsType;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public long getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(long originPrice) {
		this.originPrice = originPrice;
	}

	public String getBgGoodsDesc() {
		return bgGoodsDesc;
	}

	public void setBgGoodsDesc(String bgGoodsDesc) {
		this.bgGoodsDesc = bgGoodsDesc;
	}

	public static BgGoodsInActivityView initBgGoodsInActivityView(BgGoods bgGoods) {
		BgGoodsInActivityView bean = new BgGoodsInActivityView();
		bean.bgGoodsId = bgGoods.getBgGoodsId();
		bean.bgGoodsName = bgGoods.getBgGoodsName();
		bean.bgGoodsRemark = bgGoods.getRemark();
		bean.bgGoodsType = bgGoods.getGoodsType();
		bean.imageUrl = bgGoods.getImageUrl();
		bean.bgGoodsDesc = bgGoods.getDescription();
		return bean;
	}
}
