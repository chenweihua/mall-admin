package com.mall.admin.vo.wms;

import java.util.ArrayList;
import java.util.List;

import com.mall.admin.model.bean.synchwmsgoods.GBKBean;
import com.mall.admin.model.bean.synchwmsgoods.SynchWmsGoodsBean;
import com.mall.admin.vo.goods.BgGoods;

/**
 * 供应链管理的商品
 * 
 * @author Administrator
 *
 */
public class WmsGoods {

	public long wms_goods_id;
	public String wms_goods_name;
	public String wms_goods_gbm;
	public String short_name;
	public String brand;
	public String catg_name;
	public String sale_spec;
	public String unit;
	public String package_spec;
	public String origin_place;
	public String shelf_life;
	public long operator_id;
	public long create_time;
	public long update_time;
	public int status = 0;
	private String wmsGoodsCode;
	private Long storageId;
	private int storageType;
	private long stock;

	public WmsGoods(){}
	
	public WmsGoods(BgGoods bgGoods) {
		super();
		this.wms_goods_name = bgGoods.getBgGoodsName();
		this.short_name = bgGoods.getBgGoodsSubname();
		this.brand = bgGoods.getBrand();
		this.sale_spec = bgGoods.getSaleSpec();
		this.unit = bgGoods.getUnit();
		this.package_spec = bgGoods.getPackageSpec();
		this.origin_place = bgGoods.getOriginPlace();
		this.shelf_life = bgGoods.getShelfLife();
		this.operator_id = bgGoods.getOperator();
		this.storageId = bgGoods.getStorageId();
	}

	public String getWmsGoodsCode() {
		return wmsGoodsCode;
	}

	public void setWmsGoodsCode(String wmsGoodsCode) {
		this.wmsGoodsCode = wmsGoodsCode;
	}

	public long getWms_goods_id() {
		return wms_goods_id;
	}

	public void setWms_goods_id(long wms_goods_id) {
		this.wms_goods_id = wms_goods_id;
	}

	public String getWms_goods_name() {
		return wms_goods_name;
	}

	public void setWms_goods_name(String wms_goods_name) {
		this.wms_goods_name = wms_goods_name;
	}

	public String getWms_goods_gbm() {
		return wms_goods_gbm;
	}

	public void setWms_goods_gbm(String wms_goods_gbm) {
		this.wms_goods_gbm = wms_goods_gbm;
	}

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCatg_name() {
		return catg_name;
	}

	public void setCatg_name(String catg_name) {
		this.catg_name = catg_name;
	}

	public String getSale_spec() {
		return sale_spec;
	}

	public void setSale_spec(String sale_spec) {
		this.sale_spec = sale_spec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPackage_spec() {
		return package_spec;
	}

	public void setPackage_spec(String package_spec) {
		this.package_spec = package_spec;
	}

	public String getOrigin_place() {
		return origin_place;
	}

	public void setOrigin_place(String origin_place) {
		this.origin_place = origin_place;
	}

	public String getShelf_life() {
		return shelf_life;
	}

	public void setShelf_life(String shelf_life) {
		this.shelf_life = shelf_life;
	}

	public long getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(long operator_id) {
		this.operator_id = operator_id;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getStorageId() {
		return storageId;
	}

	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}

	public int getStorageType() {
		return storageType;
	}

	public void setStorageType(int storageType) {
		this.storageType = storageType;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public static List<WmsGoods> initWmsGoodsBySynchBean(SynchWmsGoodsBean synchBean) {
		List<WmsGoods> wmsGoodsList = new ArrayList<WmsGoods>();
		List<GBKBean> gbkBeanList = synchBean.getGbkList();
		for (GBKBean gbkBean : gbkBeanList) {
			WmsGoods wmsGoods = new WmsGoods();
			wmsGoods.setWms_goods_gbm(gbkBean.getCgbk());
			wmsGoods.setWmsGoodsCode(synchBean.getGoodsCode());
			wmsGoods.setWms_goods_name(synchBean.getGoodsName());
			wmsGoods.setBrand(synchBean.getBrandName());
			wmsGoods.setShelf_life(synchBean.getSelfLife());
			wmsGoods.setUnit(synchBean.getSaleUnit());
			wmsGoods.setCatg_name(synchBean.getGcateName());
			wmsGoods.setSale_spec(synchBean.getSaleSize());
			wmsGoodsList.add(wmsGoods);
		}
		return wmsGoodsList;
	}
}
