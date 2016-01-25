package com.mall.admin.enumdata;

public enum DaoMethodEnum {

	SELECTMTHOD(0), ADDMETHOD(1), UPDATEMETHOD(2), DELETEMETHOD(3);

	private int methodFlag;

	public int getMethodFlag() {
		return methodFlag;
	}

	DaoMethodEnum(int methodFlag)
	{
		this.methodFlag = methodFlag;
	}

}
