package com.mall.admin.service.delivery.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.delivery.DeliverCompanyDao;
import com.mall.admin.service.delivery.DeliverCompanyService;
import com.mall.admin.vo.delivery.DeliverCompany;

@Service
public class DeliverCompanyServiceImpl implements DeliverCompanyService {
	
	@Autowired
	private DeliverCompanyDao deliverCompanyDao;
	
	public DeliverCompany queryDeliverCompany(String deliverCompanyCode) {
		return deliverCompanyDao.queryDeliverCompany(deliverCompanyCode);
	}
	
	@Override
	public List<DeliverCompany> queryAllDeliverCompanys() {
		
		return deliverCompanyDao.queryAllDeliverCompanys();
		
	}
	

}
