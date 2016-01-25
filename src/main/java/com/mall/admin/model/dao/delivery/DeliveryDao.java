package com.mall.admin.model.dao.delivery;

import java.util.List;
import java.util.Map;

import com.mall.admin.vo.delivery.Delivery;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.model.yshard.TableShard;

public interface DeliveryDao {

	int insert(Delivery delivery);
	
	int update(Delivery delivery);
	
	List<Delivery> query(Delivery delivery);
	
	Delivery queryById(String deliveryCompanyCode, String deliverySheetCode);
	

}