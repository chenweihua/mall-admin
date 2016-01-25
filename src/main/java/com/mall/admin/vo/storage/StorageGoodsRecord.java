package com.mall.admin.vo.storage;

import java.util.Date;

/**
 * 库存
 * 
 * @author Administrator
 *
 */
public class StorageGoodsRecord {

	public long id;
	public long storageId;
	public long sellerId;
	public long goodsId;
	public long price;
	public int recordtype;
	public String recordcode;
	public long operator;
	public int num;
	public int pay_status;
	public int account_num;
	public int account_price;
	public int account_money;
	public int lock_status;
	public int lock_user;
	public String apply_pay_code;
	public String modify_remark;
	public Date record_time;
	public Date update_time;
	public long creator;
	public String wmsgoodsname;
	public String wmsgoodsgbm;
	public String sellerName;
	public String lock_userName;
	
	public String getSellerName(){
		return sellerName;
	}
	
	public void setSellerName(String sellerName){
		this.sellerName = sellerName; 
	}
	
	
	public int getPay_status() {
		return pay_status;
	}
	public void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}
	public int getAccount_num() {
		return account_num;
	}
	public void setAccount_num(int account_num) {
		this.account_num = account_num;
	}
	public int getAccount_price() {
		return account_price;
	}
	public void setAccount_price(int account_price) {
		this.account_price = account_price;
	}
	public int getAccount_money() {
		return account_money;
	}
	public void setAccount_money(int account_money) {
		this.account_money = account_money;
	}
	public int getLock_status() {
		return lock_status;
	}
	public void setLock_status(int lock_status) {
		this.lock_status = lock_status;
	}
	public int getLock_user() {
		return lock_user;
	}
	public void setLock_user(int lock_user) {
		this.lock_user = lock_user;
	}
	public String getApply_pay_code() {
		return apply_pay_code;
	}
	public void setApply_pay_code(String apply_pay_code) {
		this.apply_pay_code = apply_pay_code;
	}
	public String getModify_remark() {
		return modify_remark;
	}
	public void setModify_remark(String modify_remark) {
		this.modify_remark = modify_remark;
	}
	public Date getRecord_time() {
		return record_time;
	}
	public void setRecord_time(Date record_time) {
		this.record_time = record_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getStorageId() {
		return storageId;
	}
	public void setStorageId(long storageId) {
		this.storageId = storageId;
	}
	public long getSellerId() {
		return sellerId;
	}
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getRecordtype() {
		return recordtype;
	}
	public void setRecordtype(int recordtype) {
		this.recordtype = recordtype;
	}
	public String getRecordcode() {
		return recordcode;
	}
	public void setRecordcode(String recordcode) {
		this.recordcode = recordcode;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWmsgoodsname(){
		return wmsgoodsname;
	}
	public void setWmsgoodsname(String wmsgoodsname){
		this.wmsgoodsname = wmsgoodsname;
	}
	public String getWmsgoodsgbm(){
		return wmsgoodsgbm;
	}
	public void setWmsgoodsgbm(String wmsgoodsgbm){
		this.wmsgoodsgbm = wmsgoodsgbm;
	}
	public void setLock_userName(String lock_userName) {
		// TODO Auto-generated method stub
		this.lock_userName = lock_userName;
	}
	
	public String getLock_userName(){
		return lock_userName;
	}

}
