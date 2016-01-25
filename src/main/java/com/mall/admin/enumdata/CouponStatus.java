package com.mall.admin.enumdata;

/**
 *优惠券状态
 * @author liqiang
 *
 */
public enum CouponStatus {
	STATUS_0(0,"全部"),
	STATUS_1(1,"新建"),
	STATUS_2(2,"领取中"),
	STATUS_3(3,"领取完"),
	STATUS_4(4,"领取结束"),
	STATUS_5(5,"锁定");
	private CouponStatus(int key,String value){
		this.key = key;
		this.value = value;
	}
	private int key;
	
	private String value;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
