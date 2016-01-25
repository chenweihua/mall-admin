package com.mall.admin.service.activity;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.activity.TemplateActivity;

public interface TemplateActivityService {
	
	
	public List<TemplateActivity> getTemplateActivityList(Map<String,Object> param,PaginationInfo paginationInfo);
	
	public int addTemplateActivity(TemplateActivity templateActivity) throws Exception;
	
	public int updateTemplateActivity(TemplateActivity templateActivity) throws Exception ;

	public TemplateActivity getTemplateActivityById(Long activityId);
}
