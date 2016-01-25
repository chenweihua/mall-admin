package com.mall.admin.enumdata;

public enum NavMenuType {

	Activity(1, "活动"), Goods(2, "商品");

	private int type;
	private String name;

	NavMenuType(int type, String name)
	{
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
