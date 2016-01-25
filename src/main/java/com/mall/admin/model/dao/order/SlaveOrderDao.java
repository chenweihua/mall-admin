package com.mall.admin.model.dao.order;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.order.ChildOrder;

public interface SlaveOrderDao {
	public List<ChildOrder> queryThirdChildOrderListByPage(PaginationInfo paginationInfo,Map<String, Object> params,Integer masterSlaveFlag);
	
	public List<ChildOrder> queryThirdChildOrderList(Map<String, Object> params,Integer masterSlaveFlag);
	
	public double queryThirdChildOrderTotalAmount(Map<String, Object> params,Integer masterSlaveFlag);
}
