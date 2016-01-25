package com.mall.admin.enumdata;



/**
 * 优惠活动方案
 * @author  温德亮
 *
 */
public enum CouponActivityPlan {
	

	
	/**
	 * 优惠卷
	 */
	PREFERENTIAL_PLAN(0,"优惠券"),
	
	/**
	 * 积分
	 */
	INTEGRAL_PLAN(1,"积分（未开放）"),
	
	/**
	 * 实物产品
	 */
	ACTUAL_PLAN(2,"实物产品"),
	
	/**
	 * 虚拟产品
	 */
	VM_PLAN(3,"虚拟产品（未开放）"),

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

	private CouponActivityPlan(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
