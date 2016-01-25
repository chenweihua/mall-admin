package com.mall.admin.model.dao.order;

public interface WithdrawDao {
	public Long countByChildOrderId(Long childOrderId,Integer masterSlaveFlag);
}
