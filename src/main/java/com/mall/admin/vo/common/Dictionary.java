package com.mall.admin.vo.common;

public class Dictionary {
	
	private Long id;
	
	private Long typeId;
	
	private String sysCode;
	
	private String sysValue;
	
	private int disOrder;
	
	private String msg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getSysValue() {
		return sysValue;
	}

	public void setSysValue(String sysValue) {
		this.sysValue = sysValue;
	}

	public int getDisOrder() {
		return disOrder;
	}

	public void setDisOrder(int disOrder) {
		this.disOrder = disOrder;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
