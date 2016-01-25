package com.mall.admin.vo.seller;

import java.sql.Timestamp;

public class Seller {
	public long sellerId;
	public String sellerName;
	public Timestamp createTime; 
	public Timestamp updateTime;
	public long operator;
    public long isDel;
	
	public void setSellerId(long sellerId){
		this.sellerId = sellerId;
	}
	
	public long getSellerId(){
		return sellerId;
	}
	
	public void setSellerName(String sellerName){
		this.sellerName = sellerName;
	}
	
	public String getSellerName(){
		return sellerName;
	}
	
	public Long getIsDel(){
		return isDel;
	}
	public void setIsDel(long isDel){
		this.isDel = isDel;
	}
	
}
