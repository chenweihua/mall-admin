package com.mall.admin.vo.storage.ps;

public class ApplyPayRecordDetailBean {

	public long id;
	public String goodsName;
	public String goodsGbm;
	public String goodsUnit;
	// 支付单价
	public long recordPrice;
	// 支付数量
	public int recordNum;
	// 应付金额
	public long recordMoney;
	// 实付金额
	public long recordRealMoney;

	// 调整金额

	public long adjustMoney;

	public long getAdjustMoney() {
		return adjustMoney;
	}

	public void setAdjustMoney(long adjustMoney) {
		this.adjustMoney = adjustMoney;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsGbm() {
		return goodsGbm;
	}

	public void setGoodsGbm(String goodsGbm) {
		this.goodsGbm = goodsGbm;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public long getRecordPrice() {
		return recordPrice;
	}

	public void setRecordPrice(long recordPrice) {
		this.recordPrice = recordPrice;
	}

	public int getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}

	public long getRecordMoney() {
		return recordMoney;
	}

	public void setRecordMoney(long recordMoney) {
		this.recordMoney = recordMoney;
	}

	public long getRecordRealMoney() {
		return recordRealMoney;
	}

	public void setRecordRealMoney(long recordRealMoney) {
		this.recordRealMoney = recordRealMoney;
	}

}
