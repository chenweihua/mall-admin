package com.mall.admin.enumdata;

/**
 * 到对象操作
 * 
 * @author Administrator
 *
 */
public enum DaoBeanNameEnum {

	GOODSDAO("goods"), ACTIVITYGOODSDAO("activitygoods");

	private String name;

	public String getName() {
		return name;
	}

	DaoBeanNameEnum(String name)
	{
		this.name = name;
	}

}
