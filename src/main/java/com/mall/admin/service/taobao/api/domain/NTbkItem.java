package com.mall.admin.service.taobao.api.domain;

import java.util.List;

import com.mall.admin.service.taobao.api.TaobaoObject;
import com.mall.admin.service.taobao.api.internal.mapping.ApiField;
import com.mall.admin.service.taobao.api.internal.mapping.ApiListField;

/**
 * 淘宝客商品
 *
 * @author top auto create
 * @since 1.0, null
 */
public class NTbkItem extends TaobaoObject {

	private static final long serialVersionUID = 3522953928378649121L;

	/**
	 * 淘客地址
	 */
	@ApiField("click_url")
	private String clickUrl;

	/**
	 * 商品地址
	 */
	@ApiField("item_url")
	private String itemUrl;

	/**
	 * 商品ID
	 */
	@ApiField("num_iid")
	private Long numIid;

	/**
	 * 商品主图
	 */
	@ApiField("pict_url")
	private String pictUrl;

	/**
	 * 宝贝所在地
	 */
	@ApiField("provcity")
	private String provcity;

	/**
	 * 商品一口价格
	 */
	@ApiField("reserve_price")
	private String reservePrice;

	/**
	 * 商品小图列表
	 */
	@ApiListField("small_images")
	@ApiField("string")
	private List<String> smallImages;

	/**
	 * 商品标题
	 */
	@ApiField("title")
	private String title;

	/**
	 * 卖家类型，0表示集市，1表示商城
	 */
	@ApiField("user_type")
	private Long userType;

	/**
	 * 商品折扣价格
	 */
	@ApiField("zk_final_price")
	private String zkFinalPrice;

	public String getClickUrl() {
		return this.clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}

	public String getItemUrl() {
		return this.itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public Long getNumIid() {
		return this.numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public String getPictUrl() {
		return this.pictUrl;
	}

	public void setPictUrl(String pictUrl) {
		this.pictUrl = pictUrl;
	}

	public String getProvcity() {
		return this.provcity;
	}

	public void setProvcity(String provcity) {
		this.provcity = provcity;
	}

	public String getReservePrice() {
		return this.reservePrice;
	}

	public void setReservePrice(String reservePrice) {
		this.reservePrice = reservePrice;
	}

	public List<String> getSmallImages() {
		return this.smallImages;
	}

	public void setSmallImages(List<String> smallImages) {
		this.smallImages = smallImages;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUserType() {
		return this.userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public String getZkFinalPrice() {
		return this.zkFinalPrice;
	}

	public void setZkFinalPrice(String zkFinalPrice) {
		this.zkFinalPrice = zkFinalPrice;
	}

}
