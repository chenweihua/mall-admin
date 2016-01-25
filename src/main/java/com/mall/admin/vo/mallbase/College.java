package com.mall.admin.vo.mallbase;

import java.math.BigDecimal;
import java.util.Date;

public class College {
	
	private String collegeAddr;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.college_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Long collegeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.college_name
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private String collegeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.city_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Long cityId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.rdc_storage_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Long rdcStorageId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.ldc_storage_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Long ldcStorageId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.create_time
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.update_time
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.operator
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Long operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.is_del
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Byte isDel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.is_open
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Byte isOpen;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.is_show
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Byte isShow;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.latitude
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private BigDecimal latitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.longitude
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private BigDecimal longitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.area_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Long areaId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.ldc_delivery_type
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Byte ldcDeliveryType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.ldc_delivery_address
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private String ldcDeliveryAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.rdc_delivery_type
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private Byte rdcDeliveryType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_college.rdc_delivery_address
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    private String rdcDeliveryAddress;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.college_id
     *
     * @return the value of tb_college.college_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Long getCollegeId() {
        return collegeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.college_id
     *
     * @param collegeId the value for tb_college.college_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.college_name
     *
     * @return the value of tb_college.college_name
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.college_name
     *
     * @param collegeName the value for tb_college.college_name
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.city_id
     *
     * @return the value of tb_college.city_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.city_id
     *
     * @param cityId the value for tb_college.city_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.rdc_storage_id
     *
     * @return the value of tb_college.rdc_storage_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Long getRdcStorageId() {
        return rdcStorageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.rdc_storage_id
     *
     * @param rdcStorageId the value for tb_college.rdc_storage_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setRdcStorageId(Long rdcStorageId) {
        this.rdcStorageId = rdcStorageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.ldc_storage_id
     *
     * @return the value of tb_college.ldc_storage_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Long getLdcStorageId() {
        return ldcStorageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.ldc_storage_id
     *
     * @param ldcStorageId the value for tb_college.ldc_storage_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setLdcStorageId(Long ldcStorageId) {
        this.ldcStorageId = ldcStorageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.create_time
     *
     * @return the value of tb_college.create_time
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.create_time
     *
     * @param createTime the value for tb_college.create_time
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.update_time
     *
     * @return the value of tb_college.update_time
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.update_time
     *
     * @param updateTime the value for tb_college.update_time
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.operator
     *
     * @return the value of tb_college.operator
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Long getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.operator
     *
     * @param operator the value for tb_college.operator
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setOperator(Long operator) {
        this.operator = operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.is_del
     *
     * @return the value of tb_college.is_del
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Byte getIsDel() {
        return isDel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.is_del
     *
     * @param isDel the value for tb_college.is_del
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.is_open
     *
     * @return the value of tb_college.is_open
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Byte getIsOpen() {
        return isOpen;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.is_open
     *
     * @param isOpen the value for tb_college.is_open
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setIsOpen(Byte isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.is_show
     *
     * @return the value of tb_college.is_show
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Byte getIsShow() {
        return isShow;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.is_show
     *
     * @param isShow the value for tb_college.is_show
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.latitude
     *
     * @return the value of tb_college.latitude
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.latitude
     *
     * @param latitude the value for tb_college.latitude
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.longitude
     *
     * @return the value of tb_college.longitude
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.longitude
     *
     * @param longitude the value for tb_college.longitude
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.area_id
     *
     * @return the value of tb_college.area_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Long getAreaId() {
        return areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.area_id
     *
     * @param areaId the value for tb_college.area_id
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.ldc_delivery_type
     *
     * @return the value of tb_college.ldc_delivery_type
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Byte getLdcDeliveryType() {
        return ldcDeliveryType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.ldc_delivery_type
     *
     * @param ldcDeliveryType the value for tb_college.ldc_delivery_type
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setLdcDeliveryType(Byte ldcDeliveryType) {
        this.ldcDeliveryType = ldcDeliveryType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.ldc_delivery_address
     *
     * @return the value of tb_college.ldc_delivery_address
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public String getLdcDeliveryAddress() {
        return ldcDeliveryAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.ldc_delivery_address
     *
     * @param ldcDeliveryAddress the value for tb_college.ldc_delivery_address
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setLdcDeliveryAddress(String ldcDeliveryAddress) {
        this.ldcDeliveryAddress = ldcDeliveryAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.rdc_delivery_type
     *
     * @return the value of tb_college.rdc_delivery_type
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public Byte getRdcDeliveryType() {
        return rdcDeliveryType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.rdc_delivery_type
     *
     * @param rdcDeliveryType the value for tb_college.rdc_delivery_type
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setRdcDeliveryType(Byte rdcDeliveryType) {
        this.rdcDeliveryType = rdcDeliveryType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_college.rdc_delivery_address
     *
     * @return the value of tb_college.rdc_delivery_address
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public String getRdcDeliveryAddress() {
        return rdcDeliveryAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_college.rdc_delivery_address
     *
     * @param rdcDeliveryAddress the value for tb_college.rdc_delivery_address
     *
     * @mbggenerated Tue Aug 04 10:01:04 CST 2015
     */
    public void setRdcDeliveryAddress(String rdcDeliveryAddress) {
        this.rdcDeliveryAddress = rdcDeliveryAddress;
    }
    private String cityName;
	public void setCityName(String cityName) {
		// TODO Auto-generated method stub
		this.cityName = cityName;
	}
	
	public String getCityName(){
		return cityName;
	}
	
    private String rdcStorageName;
	public void setRdcStorageName(String rdcStorageName) {
		// TODO Auto-generated method stub
		this.rdcStorageName = rdcStorageName;
		
	}
	
	public String getRdcStorageName(){
		return rdcStorageName;
	}

	private String ldcStorageName;
	public void setLdcStorageName(String ldcStorageName) {
		// TODO Auto-generated method stub
		this.ldcStorageName = ldcStorageName;
	}
	
	public String getLdcStorageName(){
		return ldcStorageName;
	}
	
	private int storeId;
	
	public int getStoreId(){
		return storeId;
	}
	
	public void setStoreId(int storeId){
		this.storeId = storeId;
	}

	public String getCollegeAddr() {
		return collegeAddr;
	}

	public void setCollegeAddr(String collegeAddr) {
		this.collegeAddr = collegeAddr;
	}
}