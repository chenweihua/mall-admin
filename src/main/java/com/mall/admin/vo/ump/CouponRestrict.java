package com.mall.admin.vo.ump;

import java.util.Date;



/**
 * 优惠卷规则表
 * @author 温德亮
 *
 */
public class CouponRestrict extends  Feature{
    
	
	private static final long serialVersionUID = 708872301383096874L;

	/**
	 * 优惠卷规则ID
	 */
    private Long restrictId;

    /**
     * 描述
     */
    private String restrictDec;

    /**
     * 使用限制id(周一到周日有7个不同id)
     */
    private int typeId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 验证值
     */
    private String value;

    /**
     * 数据类型
     */
    private int dataType;

    /**
     * 状态
     */
    private int status;

    /**
     * 创建人
     */
    private Integer creator;

    /**
     * 修改人
     */
    private Integer editor;

	/**
	 * @return the restrictId
	 */
	public Long getRestrictId() {
		return restrictId;
	}

	/**
	 * @param restrictId the restrictId to set
	 */
	public void setRestrictId(Long restrictId) {
		this.restrictId = restrictId;
	}

	/**
	 * @return the restrictDec
	 */
	public String getRestrictDec() {
		return restrictDec;
	}

	/**
	 * @param restrictDec the restrictDec to set
	 */
	public void setRestrictDec(String restrictDec) {
		this.restrictDec = restrictDec;
	}

	/**
	 * @return the typeId
	 */
	public int getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the dataType
	 */
	public int getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the creator
	 */
	public Integer getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	/**
	 * @return the editor
	 */
	public Integer getEditor() {
		return editor;
	}

	/**
	 * @param editor the editor to set
	 */
	public void setEditor(Integer editor) {
		this.editor = editor;
	}

}