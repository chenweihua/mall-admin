package com.mall.admin.enumdata;

/**
 *优惠券使用限制
 * @author liqiang
 *
 */
public enum CouponRestrictEnum {
	RESTRICT_1(1,"仅首次购买可用","firstBuy"),
	RESTRICT_2(2,"不可用的活动类目","acctivity_category"),
	RESTRICT_3(3,"满减使用","discountValue"),
	RESTRICT_4(4,"限商品类目","coupon_category"),
	RESTRICT_5(5,"周一","oneTimeStart,oneTimeEnd"),
	RESTRICT_6(6,"周二","secondTimeStart,secondTimeEnd"),
	RESTRICT_7(7,"周三","thirdTimeStart,thirdTimeEnd"),
	RESTRICT_8(8,"周四","fourTimeStart,fourTimeEnd"),
	RESTRICT_9(9,"周五","fiveTimeStart,fiveTimeEnd"),
	RESTRICT_10(10,"周六","sixTimeStart,sixTimeEnd"),
	RESTRICT_11(11,"周日","SunTimeStart,SunTimeEnd");
	
	private CouponRestrictEnum(int key,String desc,String value){
		this.key = key;
		this.desc =desc;
		this.value = value;
	}
	private int key;
	
	private String desc;
	
	private String value;

	public static String getValueByKey(int key){
		String value="";
		for(CouponRestrictEnum t:CouponRestrictEnum.values()){
			if(t.getKey()==key){
				value =t.getValue();
			}
		}
		return value;
	}
	
	public int getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
