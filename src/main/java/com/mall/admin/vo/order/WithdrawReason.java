package com.mall.admin.vo.order;

import java.sql.Timestamp;

public class WithdrawReason {
    private Long withdrawReasonId;

    private Long withdrawReasonPid;
    
    private Integer withdrawReasonLevel;

    private String withdrawReasonName;

    private Timestamp createTime;
    
    private Integer isDel;

    public Long getWithdrawReasonId() {
        return withdrawReasonId;
    }

    public void setWithdrawReasonId(Long withdrawReasonId) {
        this.withdrawReasonId = withdrawReasonId;
    }

    public Long getWithdrawReasonPid() {
        return withdrawReasonPid;
    }

    public void setWithdrawReasonPid(Long withdrawReasonPid) {
        this.withdrawReasonPid = withdrawReasonPid;
    }

    public Integer getWithdrawReasonLevel() {
		return withdrawReasonLevel;
	}

	public void setWithdrawReasonLevel(Integer withdrawReasonLevel) {
		this.withdrawReasonLevel = withdrawReasonLevel;
	}

	public String getWithdrawReasonName() {
        return withdrawReasonName;
    }

    public void setWithdrawReasonName(String withdrawReasonName) {
        this.withdrawReasonName = withdrawReasonName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}