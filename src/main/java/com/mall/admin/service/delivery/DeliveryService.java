package com.mall.admin.service.delivery;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.delivery.Delivery;

public interface DeliveryService {
	
	
	public int addDelivery(Delivery delivery) throws Exception;
	
	public int updateDelivery(Delivery delivery) throws Exception ;

	public Delivery getDeliveryById(String deliveryCompanyCode, String deliverySheetCode);
}
