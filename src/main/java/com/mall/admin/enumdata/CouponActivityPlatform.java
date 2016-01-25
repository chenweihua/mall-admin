package com.mall.admin.enumdata;

public enum CouponActivityPlatform {
	/**
	 * APP+HTML5
	 */
	APPH5_PLATFORM(0,"APP+HTML5"),
	
	/**
	 * APP
	 */
	APP_PLATFORM(1,"APP"),
	
	/**
	 * Html5
	 */
	H5_PLATFORM(2,"HTML5"),
	
	/**
	 * PC
	 */
	PC_PLATFORM(3,"PC(未开放)"),

	;
	
	private int	code;
	private String	msg;

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return code + ":" + msg;
	}

	private CouponActivityPlatform(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
