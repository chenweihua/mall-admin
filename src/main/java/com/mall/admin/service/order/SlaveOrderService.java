package com.mall.admin.service.order;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.order.ChildOrder;
import com.mall.admin.vo.order.ChildOrderDetail;
import com.mall.admin.vo.order.Order;

public interface SlaveOrderService {
	
	public List<ChildOrder> queryThirdChildOrderListByPage(PaginationInfo paginationInfo,Map<String, Object> params);
	
	public List<ChildOrder> queryThirdChildOrderList(Map<String, Object> params);

	public boolean hasWithdraw(Long childOrderId);
	
	public Order getOrderById(Long orderId);
	
	public List<ChildOrderDetail> getChildOrderDetialByChildOrderId(Long childOrderId);
	
	public double queryThirdChildOrderTotalAmount(Map<String, Object> params);
}
