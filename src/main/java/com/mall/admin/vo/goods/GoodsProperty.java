package com.mall.admin.vo.goods;

/**
 * goods的属性集，聚合商品属性，例如颜色：红色、绿色
 *
 * @date 2015年7月14日 下午4:19:04
 * @author zhangshuai
 */
public class GoodsProperty {

	/*
	 * Field Type Collation Null Key Default Extra Privileges Comment
	 * ----------------- ------------ ------------------ ------ ------ -------
	 * -------------- ------------------------------- -------------------
	 * goods_property_id int(11) (NULL) NO PRI (NULL) auto_increment
	 * select,insert,update,references 商品的属性id bg_goods_id int(11) (NULL) NO
	 * (NULL) select,insert,update,references 后台goodsid property_value_id
	 * int(11) (NULL) NO (NULL) select,insert,update,references 属性值 image_url
	 * varchar(128) utf8mb4_unicode_ci NO select,insert,update,references 图片地址
	 * is_del tinyint(3) (NULL) NO 0 select,insert,update,references 软删除
	 */
	private long goodsPropertyId;

	private long bgGoodsId;

	private long propertyValueId;

	private String imageUrl;

	private int isDel;

	public long getGoodsPropertyId() {
		return goodsPropertyId;
	}

	public void setGoodsPropertyId(long goodsPropertyId) {
		this.goodsPropertyId = goodsPropertyId;
	}

	public long getBgGoodsId() {
		return bgGoodsId;
	}

	public void setBgGoodsId(long bgGoodsId) {
		this.bgGoodsId = bgGoodsId;
	}

	public long getPropertyValueId() {
		return propertyValueId;
	}

	public void setPropertyValueId(long propertyValueId) {
		this.propertyValueId = propertyValueId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
}