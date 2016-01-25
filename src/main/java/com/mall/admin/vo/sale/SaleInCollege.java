package com.mall.admin.vo.sale;

public class SaleInCollege {

	/**
	 * 前台台商品id
	 */
	public long goods_id;
	/**
	 * 商品名称
	 */
	public String goods_name;
	/** 归属仓库 */
	public long storageId;
	/**
	 * 学校id
	 */
	public long college_id;
	/**
	 * 学校名称
	 */
	public String college_name;
	/**
	 * 权重
	 */
	public int weight;
	/**
	 * 前端库存
	 */
	public int stock;
	/**
	 * 前端库存说明 stock>999999则显示不限制
	 */
	public String stockStr;
	/**
	 * 售卖状态
	 */
	public int status;
	/**
	 * 售卖状态
	 */
	public String statusStr;

	public long getStorageId() {
		return storageId;
	}

	public void setStorageId(long storageId) {
		this.storageId = storageId;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public long getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(long goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public long getCollege_id() {
		return college_id;
	}

	public void setCollege_id(long college_id) {
		this.college_id = college_id;
	}

	public String getCollege_name() {
		return college_name;
	}

	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStockStr() {
		return stockStr;
	}

	public void setStockStr(String stockStr) {
		this.stockStr = stockStr;
	}

}
