package com.mall.admin.service.outside;

import java.util.Map;

import com.mall.admin.service.outside.dto.PayItem;

public interface PayService {
	//根据orderId，获取payId
	public Map<Long,PayItem> getPayId(Map<Long, PayItem> params); 
}
