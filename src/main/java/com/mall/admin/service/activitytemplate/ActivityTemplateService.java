package com.mall.admin.service.activitytemplate;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.activitytemplate.ActivityTemplate;

public interface ActivityTemplateService {
	
	
	public List<ActivityTemplate> getActivityTemplateList(Map<String,Object> param,PaginationInfo paginationInfo);
	
	public int addActivityTemplate(ActivityTemplate activityTemplate) throws Exception;
	
	public int updateActivityTemplate(ActivityTemplate activityTemplate) throws Exception ;

	public ActivityTemplate getActivityTemplateById(Long activityTemplateId);
	
	public List<ActivityTemplate> getAllActivityTemplate();
}
