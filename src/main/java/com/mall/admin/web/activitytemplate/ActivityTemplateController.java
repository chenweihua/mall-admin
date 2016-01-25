package com.mall.admin.web.activitytemplate;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.activitytemplate.ActivityTemplateService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.util._;
import com.mall.admin.vo.activitytemplate.ActivityTemplate;



@Controller
@RequestMapping("/activitytemplate")
public class ActivityTemplateController extends BaseController {
	
	private static final Logger log = LogConstant.mallLog;
	
	@Autowired
	private ActivityTemplateService activityTemplateService;
	
	
	
	
	@RequestMapping("/list")
	public Object list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("activitytemplate/list");
		return mav;
	}
	
	
	
	@RequestMapping("/query")
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response,
		@RequestParam int draw, @RequestParam int start,@RequestParam(value = "length") int numPerPage,
		String templateName
		) {
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);

		Map<String,Object> param = Maps.newHashMap();
		param.put("templateName",templateName);
		
		List<ActivityTemplate> activityTemplateList = activityTemplateService.getActivityTemplateList(param, paginationInfo);

		return gson.toJson(buildDataTableResult(draw, 0, paginationInfo.getTotalRecord(), activityTemplateList));
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response,@RequestParam(required=false) Long activityTemplateId,
			 @RequestParam(required=false)String templateName
			 ,@RequestParam(required=false)Integer templateType
			 ,@RequestParam(required=false)String colorValue
			 ,@RequestParam(required=false)String headerImageUrl
			 ,@RequestParam(required=false)String imageUrl
			
			
		) {

		try {
			//检查入参
			//checkArgument(!_.isEmpty() && name.length() <= 100, "某某字段不能为空或者超过100字符");
			
			ActivityTemplate activityTemplate = new ActivityTemplate();
			activityTemplate.setTemplateName(templateName);
			activityTemplate.setTemplateType(templateType);
			activityTemplate.setColorValue(colorValue);
			activityTemplate.setHeaderImageUrl(headerImageUrl);
			activityTemplate.setImageUrl(imageUrl);
			
			
			
			int affectNum = 0;
			if(activityTemplateId == null) {  // 添加
				GroupSequence groupSequence = new GroupSequence();
				activityTemplate.setActivityTemplateId(groupSequence.nextValue());
				affectNum = activityTemplateService.addActivityTemplate(activityTemplate);
			} else {  //修改
				activityTemplate.setActivityTemplateId(activityTemplateId);
				affectNum = activityTemplateService.updateActivityTemplate(activityTemplate);
			}
			if(affectNum == 0) {
				return buildJson(1, "操作失败~");
			}
			return buildJson(0, "操作成功~");
		} catch (Exception e) {
			log.error("insert activityTemplate：", e);
			return buildJson(1, "操作失败：" + e.getMessage());
		}
	}
	

}
