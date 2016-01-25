package com.mall.admin.vo.delivery;

/**
 * 物流公司
 */
public class DeliverCompany {
	
	/**
	 * 主键ID
	 */
	private Long deliverCompanyId;
	
	
	/**
	 * 物流公司编码
	 */
	private String deliverCompanyCode;
	
	/**
	 * 物流公司名称
	 */
	private String deliverCompanyName;
	
	
	/**
	 * 状态 1为生效 0为失效
	 */
	private Integer status;


	public Long getDeliverCompanyId() {
		return deliverCompanyId;
	}


	public void setDeliverCompanyId(Long deliverCompanyId) {
		this.deliverCompanyId = deliverCompanyId;
	}


	public String getDeliverCompanyCode() {
		return deliverCompanyCode;
	}


	public void setDeliverCompanyCode(String deliverCompanyCode) {
		this.deliverCompanyCode = deliverCompanyCode;
	}


	public String getDeliverCompanyName() {
		return deliverCompanyName;
	}


	public void setDeliverCompanyName(String deliverCompanyName) {
		this.deliverCompanyName = deliverCompanyName;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}

	

}
