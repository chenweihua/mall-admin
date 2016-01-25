package com.mall.admin.enumdata;


/**
 * 优惠活动类型
 * @author 温德亮
 */
public enum CouponActivityType {
	
	
	/**
	 * 邀请红包活动
	 */
	INVITE_ENVELOPE_ACTIVITY(3,"邀请红包"),
	
	/**
	 * 裂变红包活动
	 */
	FISSION_ENVELOPE_ACTIVITY(2,"裂变红包"),
	
	/**
	 * 首减红包活动
	 */
	REDUCTION_ACTIVITY(4,"首减活动"),
	
	/**
	 * 推广红包
	 */
	PROMOTE_ACTIVITY(6,"推广红包"),
	
	/**
	 * 推广文章红包
	 */
	PROMOTE_ARTICLE_ACTIVITY(7,"推广文章红包")
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

	private CouponActivityType(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	

}
