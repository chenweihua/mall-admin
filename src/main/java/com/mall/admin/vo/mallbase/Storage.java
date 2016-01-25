package com.mall.admin.vo.mallbase;

import java.util.Date;

public class Storage {

	private long storageId;

	private String storageName;

	private int storageType;
	public static final int ALL_STORAGE = -1;
	public static final int RDC_STORAGE = 0;
	public static final int LDC_STORAGE = 1;
	public static final int VM_STORAGE = 2;
	public static final int O2O_STORAGE = 3;
	public static final int EC_STORAGE = 4; 

	private int ldcType;
	public static final int SHOW_LDC_TIME = 0;// LDC显示29分钟达
	public static final int HIDE_LDC_TIME = 1;// 隐藏29分钟达

	private int ldcOwnerType = 0;
	/** 小麦的ldc仓 */
	public static final int XM_LDC = 1;
	/** 商超ldc */
	public static final int SUPER_LDC = 2;

	private long cityId;

	private Date createTime;

	private Date updateTime;

	private long operatorId;

	private int isClose;

	private int isDel;

	private String cityName;

	private int vmCollegeId;

	private long freight;

	private long freightSub;

	private int freightType;
	public static final int FREIGHT_TYPE_NONE = 1;
	public static final int FREIGHT_TYPE_PRICE = 2;

	private int vmStoreId;
	/** 推送类型 0:向第三位推送 1：向小麦wms系统推送 */
	private int pushType;

	/** 推送第三方的仓储系统 */
	public static final int pushToWms = 0;
	/** 推送到小麦的仓储系统 */
	public static final int pushToXMWms = 1;

	public int getPushType() {
		return pushType;
	}

	public void setPushType(int pushType) {
		this.pushType = pushType;
	}

	public long getStorageId() {
		return storageId;
	}

	public void setStorageId(long storageId) {
		this.storageId = storageId;
	}

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public int getStorageType() {
		return storageType;
	}

	public void setStorageType(int storageType) {
		this.storageType = storageType;
	}

	public int getLdcType() {
		return ldcType;
	}

	public void setLdcType(int ldcType) {
		this.ldcType = ldcType;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	public int getIsClose() {
		return isClose;
	}

	public void setIsClose(int isClose) {
		this.isClose = isClose;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getVmCollegeId() {
		return vmCollegeId;
	}

	public void setVmCollegeId(int vmCollegeId) {
		this.vmCollegeId = vmCollegeId;
	}

	public int getVmStoreId() {
		return vmStoreId;
	}

	public void setVmStoreId(int vmStoreId) {
		this.vmStoreId = vmStoreId;
	}

	public long getFreight() {
		return freight;
	}

	public void setFreight(long freight) {
		this.freight = freight;
	}

	public long getFreightSub() {
		return freightSub;
	}

	public void setFreightSub(long freightSub) {
		this.freightSub = freightSub;
	}

	public int getFreightType() {
		return freightType;
	}

	public void setFreightType(int freightType) {
		this.freightType = freightType;
	}

	public int getLdcOwnerType() {
		return ldcOwnerType;
	}

	public void setLdcOwnerType(int ldcOwnerType) {
		this.ldcOwnerType = ldcOwnerType;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Storage) {
			Storage sObj = (Storage) obj;
			if (this.storageId == sObj.storageId) {
				return true;
			}

		}
		return false;
	}
}
