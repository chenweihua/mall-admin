package com.mall.admin.vo.mallbase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class City {
	
	/**
	 * Level 1城市  2区域
	 */
	public static final Long LEVEL_CITY = 1L;
	public static final Long LEVEL_AREA = 2l;
	
	private long cityId;
	private String cityName;
	private long pid;
	private long level;
	private Timestamp createTime;
	private Timestamp updateTime;
	private int weight;
	private int isShow;
	private int isDel;
	private int isStop;
	private long operatorId;
	private List<City> sons;
	private Long ucId;
	
	
	public void addSon(City city) {
		if(sons == null) {
			sons = new ArrayList<City>();
		}
		sons.add(city);
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public int getIsStop() {
		return isStop;
	}

	public void setIsStop(int isStop) {
		this.isStop = isStop;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}
	

	public Long getUcId() {
		return ucId;
	}

	public void setUcId(Long ucId) {
		this.ucId = ucId;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", cityName=" + cityName + ", pid="
				+ pid + ", level=" + level + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", weight=" + weight
				+ ", isShow=" + isShow + ", isDel=" + isDel + ", isStop="
				+ isStop + ", operatorId=" + operatorId + "]";
	}

	public boolean isCityLevel() {
		return this.level == LEVEL_CITY;
	}
	
	public boolean isAreaLevel() {
		return this.level == LEVEL_AREA;
	}
	
}