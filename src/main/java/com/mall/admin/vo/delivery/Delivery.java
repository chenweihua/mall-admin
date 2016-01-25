package com.mall.admin.vo.delivery;

import java.util.Date;

public class Delivery {
	
	/**
	 * 快递信息来源 
	 */
	public static final int SOURCE_KUAIDI100 = 1; // 1：快递100
	
	
	/**
	 * 快递信息状态
	 */
	public static final int STATUS_NO_INFO = 0; //新建状态
	public static final int STATUS_IN_DELIVERY = 1; //在途
	public static final int STATUS_RECEIVED = 2; //揽件
	public static final int STATUS_PROBLEM = 3; //疑难
	public static final int STATUS_SIGN_IN = 4; //签收
	
	
	/**
	 * abort状态表示
	 */
	public static final int NORMAL_STATUS_NORMAL = 0;  //正常
	public static final int NORMAL_STAUTS_ABORT = 1;  //abort
	
	
	/**
	*  物流公司编号
	**/
	private String deliveryCompanyCode;
	
	/**
	*  物流单号
	**/
	private String deliverySheetCode;
	
	/**
	*  经转换后的JSON格式的具体信息
	**/
	private String deliveryInfo;
	
	/**
	*  0 新建无具体配送状态（默认）  1.在途  2揽件  3疑难  4签收
	**/
	private Integer status;
	
	/**
	*  创建该记录时间
	**/
	private Date createTime;
	
	/**
	*  最近一次更新该记录时间
	**/
	private Date updateTime;
	
	/**
	*  1 快递100接口 该接口信息来源，以作将来可能扩展备用
	**/
	private Integer source;
	
	
	
	public Delivery() {
	
	}
	
	
	public String getDeliveryCompanyCode() {
		return deliveryCompanyCode;
	}
	
	public void setDeliveryCompanyCode(String deliveryCompanyCode) {
		this.deliveryCompanyCode = deliveryCompanyCode;
	}
	
	
	public String getDeliverySheetCode() {
		return deliverySheetCode;
	}
	
	public void setDeliverySheetCode(String deliverySheetCode) {
		this.deliverySheetCode = deliverySheetCode;
	}
	
	
	public String getDeliveryInfo() {
		return deliveryInfo;
	}
	
	public void setDeliveryInfo(String deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}
	
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
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
	
	
	public Integer getSource() {
		return source;
	}
	
	public void setSource(Integer source) {
		this.source = source;
	}
	
}