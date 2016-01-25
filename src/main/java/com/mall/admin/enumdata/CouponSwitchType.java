package com.mall.admin.enumdata;



/**
 * 红包开关类型
 * @author liqiang
 *
 */
public enum CouponSwitchType {
	
	/**
	 * 裂变红包开关
	 */
	FISSION_SWITCH(0,"裂变红包开关","fissionSwitch")

	;
	private int	key;
	private String value;
	private String	desc;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private CouponSwitchType(int key,String desc,String value) {
		this.key = key;
		this.desc = desc;
		this.value = value;
	}
	
	public static String getValueByKey(int key){
		String value="";
		for(CouponSwitchType t:CouponSwitchType.values()){
			if(t.getKey()==key){
				value =t.getValue();
			}
		}
		return value;
	}

}
