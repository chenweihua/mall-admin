package com.mall.admin.enumdata;

public enum MerchantStatus {
	CONVENIENCESTORE(1, "使用中"), RESTAURANT(2, "停用中");
	private int type;
	private String name;

	MerchantStatus(int type, String name)
	{
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 根据type获取对应的name
	 * 
	 * @param type
	 * @return
	 */
	public static String getNameByType(int type) {
		for (MerchantStatus menchantType : MerchantStatus.values()) {
			if (menchantType.getType() == type) {
				return menchantType.getName();
			}
		}
		return "未知";
	}

}
