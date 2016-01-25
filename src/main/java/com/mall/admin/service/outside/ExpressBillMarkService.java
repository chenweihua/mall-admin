package com.mall.admin.service.outside;

import java.util.Date;
import java.util.Map;

import com.mall.admin.vo.express.ExpressBill;

public interface ExpressBillMarkService {
	public Map<String, Object> getListByTime(Date day, int hour,int start,int numPerPage);
	
	public int markBill(ExpressBill expressBill);
}
