package com.mall.admin.model.dao.activitytemplate;

import java.util.List;
import java.util.Map;
import com.mall.admin.vo.activitytemplate.ActivityTemplate;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;


public interface ActivityTemplateDao {

	int insert(ActivityTemplate activityTemplate);
	
	int update(ActivityTemplate activityTemplate);
	
	List<ActivityTemplate> query(ActivityTemplate activityTemplate);
	
	int delete(Long activityTemplateId);
	
	ActivityTemplate queryById(Long activityTemplateId);
	
	public List<ActivityTemplate> getList(Map<String,Object> param,PaginationInfo paginationInfo);
	
	public List<ActivityTemplate> getAllActivityTemplate();

}