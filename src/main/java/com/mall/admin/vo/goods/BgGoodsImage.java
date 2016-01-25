package com.mall.admin.vo.goods;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BgGoodsImage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1617341393223518268L;
	
	private long goodsImgId;
	
	private long bgGoodsId;
	
	private String imageUrl;
	/**
	 * 图片类型 0：商品大图，1：商品详情图片
	 */
	private int imageType;
	public static final int GOODS_IMAGE_TYPE_COMMERCE = 0;
	public static final int GOODS_IMAGE_TYPE_DETAIL = 1;
	/**
	 * 商品图片排序 
	 */
	private int imageOrder;
	
	public BgGoodsImage() {
	}
	
	public BgGoodsImage(long bgGoodsId, String imageUrl, int imageType,
			int imageOrder) {
		this.bgGoodsId = bgGoodsId;
		this.imageUrl = imageUrl;
		this.imageType = imageType;
		this.imageOrder = imageOrder;
	}

	public long getGoodsImgId() {
		return goodsImgId;
	}

	public void setGoodsImgId(long goodsImgId) {
		this.goodsImgId = goodsImgId;
	}

	public long getBgGoodsId() {
		return bgGoodsId;
	}

	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getImageType() {
		return imageType;
	}

	public void setImageType(int imageType) {
		this.imageType = imageType;
	}

	public int getImageOrder() {
		return imageOrder;
	}

	public void setImageOrder(int imageOrder) {
		this.imageOrder = imageOrder;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
