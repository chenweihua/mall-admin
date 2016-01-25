package com.mall.admin.model.mybatis.activity;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.mall.admin.vo.activity.TemplateActivity;
import com.mall.admin.model.dao.activity.TemplateActivityDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import org.springframework.stereotype.Repository;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;

@Repository
public class TemplateActivityDaoImpl extends BaseMallDaoImpl implements TemplateActivityDao {

	public int insert(TemplateActivity templateActivity) {
		return this.getSqlSession().insert("TemplateActivity.insertTemplateActivity",templateActivity);
	}
	
	public int update(TemplateActivity templateActivity) {
		return this.getSqlSession().update("TemplateActivity.updateTemplateActivityByPrimaryKey",templateActivity);
	}
	
	public List<TemplateActivity> query(TemplateActivity templateActivity) {
		return this.getSqlSession().selectList("TemplateActivity.selectTemplateActivity", templateActivity);
	}
	
	public int delete(Long activityId) {
		return this.getSqlSession().delete("TemplateActivity.deleteTemplateActivityByPrimaryKey", activityId);
	}
	
	public TemplateActivity queryById(Long activityId) {
		return this.getSqlSession().selectOne("TemplateActivity.selectTemplateActivityByPrimaryKey", activityId);
	}
	
	public List<TemplateActivity> getList(Map<String,Object> param,PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<TemplateActivity> templateActivityList = selectPaginationList("TemplateActivity.getPageTemplateActivityByPage", param, paginationInfo);
		return templateActivityList;
	}

}