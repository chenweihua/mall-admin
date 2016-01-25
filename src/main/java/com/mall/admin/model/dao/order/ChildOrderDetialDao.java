package com.mall.admin.model.dao.order;

import java.util.List;

import com.mall.admin.vo.order.ChildOrderDetail;

public interface ChildOrderDetialDao {
	public List<ChildOrderDetail> getChildOrderDetialByChildOrderId(Long childOrderId,Integer masterSlaveFlag);
}
