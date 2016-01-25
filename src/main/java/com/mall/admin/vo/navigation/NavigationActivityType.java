package com.mall.admin.vo.navigation;

public enum NavigationActivityType {
	
	STORE_ACTIVITY(1,"店铺活动"),
	OFFICIAL_ACTIVITY(2,"官方活动");
	
	private int code;
	
	private String name;
	
	private NavigationActivityType(){}
	
	private NavigationActivityType(int code, String name){
		this.code = code;
		this.name = name;		
	}
	
	@Override
	public String toString() {
		return name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
