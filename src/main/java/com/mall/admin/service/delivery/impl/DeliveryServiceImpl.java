package com.mall.admin.service.delivery.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.dao.delivery.DeliveryDao;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.delivery.DeliveryService;
import com.mall.admin.vo.delivery.Delivery;


@Service
public class DeliveryServiceImpl implements DeliveryService {
	
	private Logger logger = LogConstant.mallLog;

	@Autowired
	private DeliveryDao deliveryDao;
	
	
	
	public int addDelivery(Delivery delivery) throws Exception {
		return deliveryDao.insert(delivery);
	}
	
	public int updateDelivery(Delivery delivery) throws Exception {
		return deliveryDao.update(delivery);
	}
	
	public Delivery getDeliveryById(String deliveryCompanyCode, String deliverySheetCode) {
		return deliveryDao.queryById(deliveryCompanyCode,deliverySheetCode);
	}
	
}
