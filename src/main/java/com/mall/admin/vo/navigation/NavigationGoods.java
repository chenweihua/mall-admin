package com.mall.admin.vo.navigation;

import java.sql.Timestamp;

public class NavigationGoods {

	/** 商品id */
	private long navGoodsId;
	/** 运营填写的商品名称 */
	private String navGoodsName;
	/** 所属菜单id */
	private long navMenuId;
	/** 所属菜单名称 */
	private String navMenuName;
	/** 淘宝商品ID */
	private String goodsId;
	/** 淘宝商品名称 */
	private String goodsName;
	/** 商品描述 */
	private String goodsDesc;
	/** 备注 */
	private String remark;
	/** 商品价格 */
	private long reservePrice;
	/** 商品折扣价格 */
	private long zkFinalPrice;
	/** 图片地址 */
	private String imageUrl;
	/** 商品链接 */
	private String itemUrl;
	/** 推广链接 */
	private String clickUrl;
	/** 权重 */
	private int weight;
	/** 是否删除 0：可用 1：不可用 */
	private int isDel;
	/** 原产地 */
	private String originPlace;
	/** 创建时间 */
	private Timestamp createTime;
	/** 更新时间 */
	private Timestamp updateTime;
	/** 创建人 */
	private long creator;
	/** 操作人 */
	private long operator;
	/** 商品状态 1：选品池 2：售卖池 */
	private int goodsStatus;
	/** 是否可见 0:不可见 1：可见 */
	private int isOpen;

	public long getNavGoodsId() {
		return navGoodsId;
	}

	public void setNavGoodsId(long navGoodsId) {
		this.navGoodsId = navGoodsId;
	}

	public String getNavGoodsName() {
		return navGoodsName;
	}

	public void setNavGoodsName(String navGoodsName) {
		this.navGoodsName = navGoodsName;
	}

	public long getNavMenuId() {
		return navMenuId;
	}

	public void setNavMenuId(long navMenuId) {
		this.navMenuId = navMenuId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(long reservePrice) {
		this.reservePrice = reservePrice;
	}

	public long getZkFinalPrice() {
		return zkFinalPrice;
	}

	public void setZkFinalPrice(long zkFinalPrice) {
		this.zkFinalPrice = zkFinalPrice;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public String getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
	}

	public int getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(int goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public String getNavMenuName() {
		return navMenuName;
	}

	public void setNavMenuName(String navMenuName) {
		this.navMenuName = navMenuName;
	}

	public int getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}

}
