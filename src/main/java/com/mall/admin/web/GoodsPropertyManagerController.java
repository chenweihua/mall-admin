package com.mall.admin.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.admin.base.BaseController;
import com.mall.admin.base._;
import com.mall.admin.service.goods.PropertyCategoryService;
import com.mall.admin.service.goods.PropertyNameService;
import com.mall.admin.service.goods.PropertyValueService;
import com.mall.admin.util.RequestUtil;
import com.mall.admin.vo.goods.PropertyCategory;
import com.mall.admin.vo.goods.PropertyName;
import com.mall.admin.vo.goods.PropertyValue;
import com.mall.admin.vo.user.User;

/**
 * 用户登录控制对象
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/goods/property")
public class GoodsPropertyManagerController extends BaseController {
	@Autowired
	PropertyCategoryService propertyCategoryService;
	@Autowired
	PropertyNameService propertyNameService;
	@Autowired
	PropertyValueService propertyValueService;
	
	@ResponseBody
	@RequestMapping("/category/getByPid")
	public Object getByPid(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		String pidStr = request.getParameter("pid");
		if(pidStr == null || !pidStr.matches("[0-9]{1,}")){
			return buildJson(-1, "请求失败，请重试~");
		}
		long pid = _.toLong(pidStr);
		
		List<PropertyCategory> pCategoryList = propertyCategoryService.getByPid(pid);
		return buildSuccJson(pCategoryList);
	}
	
	@ResponseBody
	@RequestMapping("/category/add")
	public Object categoryAdd(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		User user = (User)request.getAttribute("user");
		PropertyCategory pCategory = RequestUtil.toBean(request, PropertyCategory.class);
		if(pCategory.getPid() == -1){
			return buildJson(-1, "请首先选择父类值，再尝试添加！");
		}
		if(pCategory.getPropertyCategoryName().equals("")){
			return buildJson(-1, "属性值必须填写！");
		}
		if(pCategory.getWeight() == -1){
			pCategory.setWeight(1);
		}
		
		pCategory.setIsDel(0);
		pCategory.setOperator(user.getUser_id());
		long pcId = propertyCategoryService.insert(pCategory);
		if(pcId < 0){
			return buildJson(-1, "创建商品属性失败~");
		}
		
		return buildSuccJson(propertyCategoryService.getById(pcId));
	}
	
	
	@ResponseBody
	@RequestMapping("/name/getByCategoryId")
	public Object getByCategoryId(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		
		String categoryIdStr = request.getParameter("categoryId");
		if(categoryIdStr == null || !categoryIdStr.matches("[0-9]{1,}")){
			return buildJson(-1, "请求失败，请重试~");
		}
		long categoryId = _.toLong(categoryIdStr);
		
		List<PropertyName> pNameList = propertyNameService.getByCategoryId(categoryId);
		return buildSuccJson(pNameList);
	}
	
	@ResponseBody
	@RequestMapping("/name/add")
	public Object nameAdd(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		PropertyName pName = RequestUtil.toBean(request, PropertyName.class);
		
		if(pName.getPropertyCategoryId() == -1){
			return buildJson(-1, "添加失败，请先选择商品类型~");
		}
		if(pName.getPropertyName().equals("")){
			return buildJson(-1, "规格名称必须填写！");
		}
		if(pName.getShowOrder() == -1){
			return buildJson(-1,"展示顺序必须为数字");
		}
		
		pName.setIsDel(0);
		//目前isSku,needPic默认为否
		pName.setNeedPic(0);
		
		long pnId = propertyNameService.insert(pName);
		if(pnId < 0){
			return buildJson(-1, "创建商品属性失败~");
		}
		
		return buildSuccJson(propertyNameService.getById(pnId));
	}
	
	@ResponseBody
	@RequestMapping("/value/getByNameId")
	public Object getByNameId(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		
		String nameIdStr = request.getParameter("nameId");
		if(nameIdStr == null || !nameIdStr.matches("[0-9]{1,}")){
			return buildJson(-1, "请求失败，请重试~");
		}
		long nameId = _.toLong(nameIdStr);
		
		List<PropertyValue> pValueList = propertyValueService.getByNameId(nameId);
		return buildSuccJson(pValueList);
	}
	
	@ResponseBody
	@RequestMapping("/value/add")
	public Object valueAdd(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		
		PropertyValue pValue = RequestUtil.toBean(request, PropertyValue.class);
		
		if(pValue.getPropertyNameId() == -1){
			return buildJson(-1, "添加失败，请先选择规格属性~");
		}
		if(pValue.getPropertyValue().equals("")){
			return buildJson(-1, "属性值必须填写！");
		}
		if(pValue.getShowOrder() == -1){
			return buildJson(-1,"展示顺序必须为数字");
		}
		pValue.setIsDel(0);
		pValue.setImageUrl("");
		
		long pvId = propertyValueService.insert(pValue);
		if(pvId < 0){
			return buildJson(-1, "创建商品属性失败~");
		}
		
		return buildSuccJson(propertyValueService.getById(pvId));
	}
}
