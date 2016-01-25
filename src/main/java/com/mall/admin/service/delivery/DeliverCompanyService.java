package com.mall.admin.service.delivery;

import java.util.List;

import com.mall.admin.vo.delivery.DeliverCompany;

public interface DeliverCompanyService {
	
	public DeliverCompany queryDeliverCompany(String deliverCompanyCode);
	
	List<DeliverCompany> queryAllDeliverCompanys();

}
