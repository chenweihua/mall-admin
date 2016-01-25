package com.mall.admin.model.dao.delivery;

import java.util.List;

import com.mall.admin.vo.delivery.DeliverCompany;

public interface DeliverCompanyDao {
	
	List<DeliverCompany> queryAllDeliverCompanys();
	
	DeliverCompany queryDeliverCompany(String deliverCompanyCode);

}
