package com.mall.admin.web.category;


import static com.google.common.base.Preconditions.checkState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mall.admin.base.BaseController;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.service.category.ThirdCategoryService;
import com.mall.admin.vo.category.ThirdCategory;
import com.mall.admin.vo.user.User;

@Controller
@RequestMapping("/category/")
public class CategoryController extends BaseController{
	
	private static final Logger LOGGER = LogConstant.mallLog;

	@Autowired
	private ThirdCategoryService categoryService;
	
	@RequestMapping("view")
	public Object view(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return new ModelAndView("thirdCategory/categoryList");
	}
	
	@RequestMapping("secondCategoryList")
	public Object secondCategoryList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int categoryId) throws Exception{
		ModelAndView model = new ModelAndView("thirdCategory/secondCategoryList");
		ThirdCategory thirdCategory = categoryService.getCategoryById(categoryId);
		model.addObject("categoryId", categoryId);
		model.addObject("thirdCategory", thirdCategory);
		return model;
	}
	
	@RequestMapping("queryCategory")
	@ResponseBody
	public Object queryCategory(HttpServletRequest request,HttpServletResponse response,
		@RequestParam int draw,
		@RequestParam(value = "start",required = false) Integer start, 
		@RequestParam(value = "length",required = false) Integer numPerPage,
		@RequestParam(defaultValue = "1") Integer level,
		@RequestParam(defaultValue = "0") Integer categoryId,
		@RequestParam(value = "searchStr",required = false) String searchStr) throws Exception{	
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setRecordPerPage(numPerPage);
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		Map<String, Object> param = new HashMap<String, Object>();	
		if(StringUtils.isNotEmpty(searchStr)) {
			param.put("propertyCategoryName", new String(searchStr.getBytes("ISO-8859-1"),"UTF-8"));
		}
		param.put("level", level);
		if(categoryId != 0) {
			param.put("pid", categoryId);
		}
		try{
			Pair<Long, PaginationList<ThirdCategory>> p = categoryService.getPageCategory(paginationInfo, param);
			List<ThirdCategory> categoryList = p.getRight();
			return gson.toJson(buildDataTableResult(draw, 0, p.getLeft(), categoryList));
		}catch(Exception e){
			LOGGER.error("获取类目失败", e);
			return buildJson(1,"获取类目失败");
		}
	}
	
	@RequestMapping("addEditCategory")
	@ResponseBody
	public Object addEditCategory(HttpServletRequest request,HttpServletResponse response,
			ThirdCategory category) throws Exception{
		
		try{
			if(StringUtils.isEmpty(category.getPropertyCategoryName())){
				return buildJson(1,"类目名称不能为空");
			} 
			else {
				category.setPropertyCategoryName(category.getPropertyCategoryName().trim());
			}
			if(category.getLevel() == 0){
				return buildJson(1,"参数有误");
			} 
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			checkState(user != null, "请先登录");
			
			category.setOperator((int)user.getUser_id());
			category.setModifyUser(user.getUser_name());
			if(category.getPropertyCategoryId() == 0L) {
				category.setCreator(user.getUser_name());
				categoryService.addCategory(category);
			}
			else {
				categoryService.updateCategory(category);
			}
			return buildJson(0,"操作类目成功");
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("操作类目失败", e);
			return buildJson(1,"操作类目失败");
		}
	} 
	
	@RequestMapping("getCategoryById")
	@ResponseBody
	public Object getCategoryById(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue = "0") long categoryId) throws Exception{
		
		try{
			if(categoryId == 0){
				return buildJson(1,"类目ID为空");
			}
			
			ThirdCategory ThirdCategory = categoryService.getCategoryById(categoryId);
			return buildSuccJson(ThirdCategory);
		}catch(Exception e){
			LOGGER.error("获取类目失败", e);
			return buildJson(1,"获取类目失败");
		}
		
	}
	
	@RequestMapping("deleteCategory")
	@ResponseBody
	public Object deleteCategory(HttpServletRequest request,HttpServletResponse response,
			@RequestParam Long categoryId) throws Exception{
		
		if(categoryId == null || categoryId == 0){
			return buildJson(1,"类目ID为空");
		}
		try{
			categoryService.deleteCategory(categoryId);
			return buildJson(0,"删除类目成功");
		}catch(Exception e){
			LOGGER.error("删除类目失败", e);
			return buildJson(1,"删除类目失败");
		}
		
	}
		
}
