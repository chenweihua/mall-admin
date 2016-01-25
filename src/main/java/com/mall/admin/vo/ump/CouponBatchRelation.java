package com.mall.admin.vo.ump;

/**
 * 批次表和多个面值的关联关系
 * @author liqiang
 *
 */
public class CouponBatchRelation  extends  Feature{

	private static final long serialVersionUID = 148015629616994487L;

	/**
     * 关联id
     */
    private Long couponRelationId;

    /**
     * 批次表id
     */
    private Long batchId;

    /**
     * 单个面值
     */
    private Long money;


    /**
     * 每个面值的总张数
     */
    private Integer totalNumber;

	/**
	 * @return the couponRelationId
	 */
	public Long getCouponRelationId() {
		return couponRelationId;
	}

	/**
	 * @param couponRelationId the couponRelationId to set
	 */
	public void setCouponRelationId(Long couponRelationId) {
		this.couponRelationId = couponRelationId;
	}

	/**
	 * @return the batchId
	 */
	public Long getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the money
	 */
	public Long getMoney() {
		return money;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(Long money) {
		this.money = money;
	}

	/**
	 * @return the totalNumber
	 */
	public Integer getTotalNumber() {
		return totalNumber;
	}

	/**
	 * @param totalNumber the totalNumber to set
	 */
	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

}