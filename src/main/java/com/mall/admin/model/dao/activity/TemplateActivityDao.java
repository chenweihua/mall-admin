package com.mall.admin.model.dao.activity;

import java.util.List;
import java.util.Map;
import com.mall.admin.vo.activity.TemplateActivity;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;


public interface TemplateActivityDao {

	int insert(TemplateActivity templateActivity);
	
	int update(TemplateActivity templateActivity);
	
	List<TemplateActivity> query(TemplateActivity templateActivity);
	
	int delete(Long activityId);
	
	TemplateActivity queryById(Long activityId);
	
	public List<TemplateActivity> getList(Map<String,Object> param,PaginationInfo paginationInfo);

}