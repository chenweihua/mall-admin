package com.mall.admin.vo.delivery;

import java.util.Date;

public class DeliverDetailInfo {
	
	
	private String date;
	
	private String context;
	
	public DeliverDetailInfo(){}
	
	public DeliverDetailInfo(String date,String context){
		
		this.date = date;
		this.context = context;
		
	}

	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	

}
