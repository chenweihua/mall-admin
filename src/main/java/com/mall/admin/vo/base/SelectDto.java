package com.mall.admin.vo.base;

public class SelectDto {
	private long value;
	private String name;
	private boolean selected;
	
	public SelectDto() {
	}
	
	public SelectDto(long value, String name, boolean selected) {
		super();
		this.value = value;
		this.name = name;
		this.selected = selected;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
