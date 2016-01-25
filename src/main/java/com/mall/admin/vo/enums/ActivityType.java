package com.mall.admin.vo.enums;


/**
 * 优惠活动类型
 * @author 温德亮
 */
public enum ActivityType {
	
	/**
	 * 秒杀活动
	 */
	SECONDS_KILL_ACTIVITY(0,"秒杀活动"),
	
	/**
	 * 日常活动
	 */
	DAILY_ACTIVITY(1,"日常活动"),
	
	/**
	 * 系统推荐活动
	 */
	RECOMMEND_ACTIVITY(5,"系统推荐"),

	/**
	 * 聚合活动
	 */
	MULTI_ACTIVITY(6,"聚合活动"),

	
	
	/**
	 * 爆品活动
	 */
	POPULAR_ACTIVITY(8, "爆品活动"),
	
	
	/**
	 * 品牌活动
	 */
	BRAND_ACTIVITY(9, "品牌活动");
	
	/**
	 * 邀请红包活动
	 *//*
	INVITE_ENVELOPE_ACTIVITY(3,"邀请红包活动"),
	
	*//**
	 * 裂变红包活动
	 *//*
	FISSION_ENVELOPE_ACTIVITY(2,"裂变红包活动"),
	
	*//**
	 * 首减红包活动
	 *//*
	REDUCTION_ACTIVITY(4,"首减红包活动"),
	
	*//**
	 * 普通优惠卷活动
	 *//*
	ORDINARY_ENVELOPE_ACTIVITY(5,"普通优惠券领取活动"),*/
	
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

	private ActivityType(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	

}
