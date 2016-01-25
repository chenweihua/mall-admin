package com.mall.admin.model.mybatis.activitytemplate;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.mall.admin.vo.activitytemplate.ActivityTemplate;
import com.mall.admin.model.dao.activitytemplate.ActivityTemplateDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import org.springframework.stereotype.Repository;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;

@Repository
public class ActivityTemplateDaoImpl extends BaseMallDaoImpl implements ActivityTemplateDao {

	public int insert(ActivityTemplate activityTemplate) {
		return this.getSqlSession().insert("ActivityTemplate.insertActivityTemplate",activityTemplate);
	}
	
	public int update(ActivityTemplate activityTemplate) {
		return this.getSqlSession().update("ActivityTemplate.updateActivityTemplateByPrimaryKey",activityTemplate);
	}
	
	public List<ActivityTemplate> query(ActivityTemplate activityTemplate) {
		return this.getSqlSession().selectList("ActivityTemplate.selectActivityTemplate", activityTemplate);
	}
	
	public int delete(Long activityTemplateId) {
		return this.getSqlSession().delete("ActivityTemplate.deleteActivityTemplateByPrimaryKey", activityTemplateId);
	}
	
	public ActivityTemplate queryById(Long activityTemplateId) {
		return this.getSqlSession().selectOne("ActivityTemplate.selectActivityTemplateByPrimaryKey", activityTemplateId);
	}
	
	public List<ActivityTemplate> getList(Map<String,Object> param,PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<ActivityTemplate> activityTemplateList = selectPaginationList("ActivityTemplate.getPageActivityTemplateByPage", param, paginationInfo);
		return activityTemplateList;
	}
	
	public List<ActivityTemplate> getAllActivityTemplate() {
		return this.getSqlSession().selectList("ActivityTemplate.selectAllActivityTemplate");
	}

}