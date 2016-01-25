package com.mall.admin.vo.sale;

public class SaleInStorage {

	/**
	 * 后台商品id
	 */
	public long bg_goods_id;
	/**
	 * 商品名称
	 */
	public String bg_goods_name;
	/**
	 * 商品描述
	 */
	public String bg_goods_describ;
	/**
	 * 商品类型 1：单品; 2：组合；3：聚合
	 */
	public int goods_type;
	public String goods_type_str;
	/**
	 * 仓库id
	 */
	public long storage_id;
	/**
	 * 仓库名称
	 */
	public String storage_name;
	/**
	 * 类目id
	 */
	public long category_id;
	/**
	 * 类目名称
	 */
	public String category_name;
	/**
	 * 权重
	 */
	public long weight;
	/**
	 * 前端库存
	 */
	public long stock;
	/**
	 * 售卖状态
	 */
	public int status;

	/**
	 * status=1 待售； status=2 在售； status=3 售罄
	 * 
	 */
	public String statusStr = "未知";
	/**
	 * 库存显示，如果大于999999，则显示不限制
	 */
	public String stockStr = "未知";

	/**
	 * 如果是单品，则查询该商品所在仓库的库存
	 */
	public String storageStock = "--";

	public String getStorageStock() {
		return storageStock;
	}

	public void setStorageStock(String storageStock) {
		this.storageStock = storageStock;
	}

	public long getBg_goods_id() {
		return bg_goods_id;
	}

	public void setBg_goods_id(long bg_goods_id) {
		this.bg_goods_id = bg_goods_id;
	}

	public String getBg_goods_name() {
		return bg_goods_name;
	}

	public void setBg_goods_name(String bg_goods_name) {
		this.bg_goods_name = bg_goods_name;
	}

	public String getBg_goods_describ() {
		return bg_goods_describ;
	}

	public void setBg_goods_describ(String bg_goods_describ) {
		this.bg_goods_describ = bg_goods_describ;
	}

	public long getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(long storage_id) {
		this.storage_id = storage_id;
	}

	public String getStorage_name() {
		return storage_name;
	}

	public void setStorage_name(String storage_name) {
		this.storage_name = storage_name;
	}

	public long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getStockStr() {
		return stockStr;
	}

	public void setStockStr(String stockStr) {
		this.stockStr = stockStr;
	}

	public int getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(int goods_type) {
		this.goods_type = goods_type;
	}

	public String getGoods_type_str() {
		return goods_type_str;
	}

	public void setGoods_type_str(String goods_type_str) {
		this.goods_type_str = goods_type_str;
	}

	public void setGoodsType() {
		if (goods_type == 1) {
			goods_type_str = "单品";
		} else if (goods_type == 2) {
			goods_type_str = "组合品";
		} else if (goods_type == 3) {
			goods_type_str = "聚合品";
		} else {
			goods_type_str = "未知";
		}
	}
}
