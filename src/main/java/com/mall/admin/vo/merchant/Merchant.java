package com.mall.admin.vo.merchant;

public class Merchant {

	/**
	 * 账户前缀
	 */
	public static final String ACOUNT_PRE = "XMS";
	/**
	 * 门店id
	 */
	private long merchantId;
	/**
	 * 用户id
	 */
	private long userId;
	/**
	 * 门店名称
	 */
	private String merchantName;
	/**
	 * 所在城市id
	 */
	private long merchantCityId;
	/**
	 * 所在区id
	 */
	private long merchantAreaId;
	/**
	 * 商户序号
	 */
	private String merchantNo;
	/**
	 * 商户登陆账号
	 */
	private String merchantAccount;
	/**
	 * 用户支付地址
	 */
	private String mechantPayAddr;
	/**
	 * 门店地址
	 */
	private String merchantAddr;
	/**
	 * 店长
	 */
	private String shopOwner;

	/**
	 * 店长联系方式
	 */
	private String ownerPhone;
	/**
	 * 门店email
	 */
	private String merchantEmail;
	/**
	 * 门店类型 1：便利店；2：餐饮
	 */
	private int merchantType;
	/**
	 * 门店类型 1：便利店；2：餐饮
	 */
	private String merchantTypeStr;
	/**
	 * 门店状态 1：使用者；2：停止使用
	 */
	private int status;
	/**
	 * 银行账户
	 */
	private String bankAccount;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 银行支行名称
	 */
	private String branchName;
	/**
	 * 开户人姓名
	 */
	private String bankUserName;
	/**
	 * 创建时间
	 */
	private long createTime;
	/**
	 * 更新时间
	 */
	private long updateTime;
	/**
	 * 创建人
	 */
	private long creator;
	/**
	 * 操作人
	 */
	private long operator;

	public long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantAccount() {
		return merchantAccount;
	}

	public void setMerchantAccount(String merchantAccount) {
		this.merchantAccount = merchantAccount;
	}

	public String getMechantPayAddr() {
		return mechantPayAddr;
	}

	public void setMechantPayAddr(String mechantPayAddr) {
		this.mechantPayAddr = mechantPayAddr;
	}

	public String getMerchantAddr() {
		return merchantAddr;
	}

	public void setMerchantAddr(String merchantAddr) {
		this.merchantAddr = merchantAddr;
	}

	public String getShopOwner() {
		return shopOwner;
	}

	public void setShopOwner(String shopOwner) {
		this.shopOwner = shopOwner;
	}

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public String getMerchantEmail() {
		return merchantEmail;
	}

	public void setMerchantEmail(String merchantEmail) {
		this.merchantEmail = merchantEmail;
	}

	public int getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(int merchantType) {
		this.merchantType = merchantType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankUserName() {
		return bankUserName;
	}

	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
	}

	public long getMerchantCityId() {
		return merchantCityId;
	}

	public void setMerchantCityId(long merchantCityId) {
		this.merchantCityId = merchantCityId;
	}

	public long getMerchantAreaId() {
		return merchantAreaId;
	}

	public void setMerchantAreaId(long merchantAreaId) {
		this.merchantAreaId = merchantAreaId;
	}

	public String getMerchantTypeStr() {
		return merchantTypeStr;
	}

	public void setMerchantTypeStr(String merchantTypeStr) {
		this.merchantTypeStr = merchantTypeStr;
	}

}
