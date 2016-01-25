package com.mall.admin.enumdata;

public enum NavigationType {

	CHAOJIFAN(1, "超级返"), TAOJINGPIN(2, "淘精品");

	private int type;
	private String name;

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	NavigationType(int type, String name)
	{
		this.type = type;
		this.name = name;
	}

}
