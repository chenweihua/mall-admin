package com.mall.admin.enumdata;


public enum BannerTypeEnum {
	BANNER_HREF_TYPE_ACTIVITY(1,"activity"),
	BANNER_HREF_TYPE_CATEGORY(2,"category"),
	BANNER_HREF_TYPE_WEBVIEW(3,"webview"),
	BANNER_HREF_TYPE_GOODSDETAIL(4,"goodsdetail"),
	BANNER_HREF_TYPE_SCORE(5,"score"),
	BANNER_HREF_TYPE_USERCENTER(6,"usercenter"),
	BANNER_HREF_TYPE_DISCOVERY(7,"discovery");
	
	private int value;
	private String name;
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	BannerTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static BannerTypeEnum getEnum(String name) {
		for (BannerTypeEnum temp : BannerTypeEnum.values()) {
			if (temp.getName().equals(name))
				return temp;
		}
		return null;
	}
	
	public static BannerTypeEnum getEnum(Integer value) {
		for (BannerTypeEnum temp : BannerTypeEnum.values()) {
			if (temp.getValue() == value)
				return temp;
		}
		return null;
	}
}
