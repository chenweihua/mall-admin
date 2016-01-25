package com.mall.admin.vo.merchant;

import java.sql.Timestamp;

/**
 * 商户汇总订单信息
 * 
 * @author Administrator
 *
 */
public class MerchantSummaryOrder {

	/**
	 * 序号
	 */
	private long summaryId;
	/**
	 * 商户编号
	 */
	private String merchantNo;
	/**
	 * 记录时间
	 */
	private Timestamp recordDate;
	/**
	 * 总金额
	 */
	private long sumTotalPay;
	/**
	 * 在线支付金额
	 */
	private long sumOnlinePay;
	/**
	 * 总数量
	 */
	private long sumCount;
	/**
	 * 商户名称
	 */
	private String merchantName;

	public long getSummaryId() {
		return summaryId;
	}

	public void setSummaryId(long summaryId) {
		this.summaryId = summaryId;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Timestamp getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}

	public long getSumTotalPay() {
		return sumTotalPay;
	}

	public void setSumTotalPay(long sumTotalPay) {
		this.sumTotalPay = sumTotalPay;
	}

	public long getSumOnlinePay() {
		return sumOnlinePay;
	}

	public void setSumOnlinePay(long sumOnlinePay) {
		this.sumOnlinePay = sumOnlinePay;
	}

	public long getSumCount() {
		return sumCount;
	}

	public void setSumCount(long sumCount) {
		this.sumCount = sumCount;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "merchantNo:" + merchantNo + ",merchantName:" + merchantName + ",recordDate:" + recordDate
				+ ",sumTotalPay:" + sumTotalPay + ",sumOnlinePay:" + sumOnlinePay + ",sumCount:"
				+ sumCount;
	}

}
