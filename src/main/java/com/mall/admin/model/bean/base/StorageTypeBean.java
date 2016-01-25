package com.mall.admin.model.bean.base;

public class StorageTypeBean {

	private String typeId;
	private String typeName;

	public StorageTypeBean(String typeId, String typeName)
	{
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
