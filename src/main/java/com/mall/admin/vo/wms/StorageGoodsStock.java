package com.mall.admin.vo.wms;

/**
 * 库存
 * 
 * @author Administrator
 *
 */
public class StorageGoodsStock {

	public long storage_goods_id;
	public long wms_goods_id;
	public long storage_id;
	public long operator;
	public long create_time;
	public long update_time;
	public int is_del = 0;
	public long stock;
	public int storage_type;
	public long creator;
	
	//wms_goods表
	public String wms_goods_name;
	public String wms_goods_gbm;
	public String shelf_life;
	public String package_spec;
	public String unit;



	public long getWms_goods_id() {
		return wms_goods_id;
	}

	public void setWms_goods_id(long wms_goods_id) {
		this.wms_goods_id = wms_goods_id;
	}

	public long getStorage_goods_id() {
		return storage_goods_id;
	}

	public void setStorage_goods_id(long storage_goods_id) {
		this.storage_goods_id = storage_goods_id;
	}

	public long getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(long storage_id) {
		this.storage_id = storage_id;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
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

	public int getIs_del() {
		return is_del;
	}

	public void setIs_del(int is_del) {
		this.is_del = is_del;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public int getStorage_type() {
		return storage_type;
	}

	public void setStorage_type(int storage_type) {
		this.storage_type = storage_type;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	public void setStorage_Id(long storageId) {
		// TODO Auto-generated method stub
		this.storage_id = storageId;
	}

	public String  getWms_goods_name(){
		return wms_goods_name;
	}
	public String getWms_goods_gbm(){
		return wms_goods_gbm;  
	}
	public String getPackage_spec(){
		return package_spec;
	}
	public String getShelf_life(){
		return shelf_life;
	}
	
	public String getUnit(){
		return unit;
	}
	
	public void setWms_goods_name(String wms_goods_name){
		this.wms_goods_name = wms_goods_name;
	}
	public void setWms_goods_gbm(String wms_goods_gbm){
		this.wms_goods_gbm = wms_goods_gbm;  
	}
	public void setPackage_spec(String package_spec){
		this.package_spec = package_spec;
	}
	public void setShelf_life(String shelf_life){
		this.shelf_life = shelf_life;
	}
	public void setUnit(String unit){
		this.unit = unit;
	}
	

}
