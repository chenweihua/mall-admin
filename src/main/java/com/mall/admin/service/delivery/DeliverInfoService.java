package com.mall.admin.service.delivery;

import java.util.List;

import com.mall.admin.vo.delivery.DeliverDetailInfo;

public interface DeliverInfoService {
	
	public List<DeliverDetailInfo> queryDeliverInfo(String deliverCompanyCode, String deliverSheetCode);
	
	public boolean registerDeliverInfo(String deliverCompanyCode, String deliverSheetCode);

}
