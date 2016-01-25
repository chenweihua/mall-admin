package com.mall.admin.web.supplier;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.service.supplier.SupplierService;
import com.mall.admin.vo.supplier.Suppiler;
import com.mall.admin.vo.user.User;


@Controller
@RequestMapping("/supplier/")
public class SupplierController extends BaseController{
	private static final Logger LOGGER = LogConstant.mallLog;
	@Autowired
	private SupplierService supplierService;
	
	@RequestMapping("view")
	public Object view(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		return new ModelAndView("supplier/supplierList");
	}
	
	@RequestMapping("querySupplier")
	@ResponseBody
	public Object querySupplier(HttpServletRequest request,HttpServletResponse response,
		@RequestParam int draw,
		@RequestParam(value = "start",required = false) Integer start, 
		@RequestParam(value = "length",required = false) Integer numPerPage,
		@RequestParam(value = "searchStr",required = false) String searchStr) throws Exception{	
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setRecordPerPage(numPerPage);
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		List<Suppiler> supplilerList = Lists.newArrayList();
		Map<String, Object> param = new HashMap<String, Object>();	
		String shopTypeStr = request.getParameter("shopType");
		int shopType = Integer.parseInt(shopTypeStr);
		String shopIsOpenStr = request.getParameter("shopIsOpen");
		int shopIsOpen = Integer.parseInt(shopIsOpenStr);
		try{
			param.put("shop_name_like",new String(searchStr.getBytes("ISO-8859-1"),"UTF-8"));
			if(shopType != -1) {
				param.put("shopType", shopType);
			}
			if(shopIsOpen != -1){
				param.put("shopIsOpen", shopIsOpen);
			}
			Pair<Long, PaginationList<Suppiler>> p = supplierService.getPageSupplier(paginationInfo, param);
			supplilerList = p.getRight();
			long filteredTotal = p.getLeft();
			return gson.toJson(buildDataTableResult(draw, 0, filteredTotal, supplilerList));
		}catch(Exception e){
			LOGGER.error("查询供应商失败", e);
			return buildJson(1,"查询供应商失败");
		}
	}
	
	@RequestMapping("querySupplierById")
	@ResponseBody
	public Object querySupplierById(HttpServletRequest request,HttpServletResponse response,
		@RequestParam long shopId) throws Exception{	
		
		try{
			Suppiler suppiler = supplierService.getSupplierById(shopId);
			
			return buildSuccJson(suppiler);
		}catch(Exception e){
			LOGGER.error("查询供应商失败", e);
			return buildJson(1,"查询供应商失败");
		}
	}
	
	@RequestMapping("addEditSupplier")
	@ResponseBody
	public Object addSupplier(HttpServletRequest request,HttpServletResponse response,
			Suppiler suppiler) throws Exception{
		
		try{
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			suppiler.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			suppiler.setCreateTime(new Timestamp(System.currentTimeMillis()));
			suppiler.setOperateId((int)user.getUser_id());
			if(suppiler.getShopId() == 0) {
				supplierService.addSupplier(suppiler);
			}
			else {
				boolean result = supplierService.updateSupplier(suppiler);
				if(!result) {
					return buildJson(0,"编辑商家失败");
				}
			}
			return buildJson(0,"添加商家成功");
		}catch(Exception e){
			LOGGER.error("添加供应商失败", e);
			return buildJson(1,"添加供应商失败");
		}
	} 
	
	@RequestMapping("editSupplier")
	@ResponseBody
	public Object editSeller(HttpServletRequest request,HttpServletResponse response,
			Suppiler suppiler) throws Exception{
		
		if(suppiler.getShopId() == 0){
			return buildJson(1,"卖家名称不能为空");
		}
		if(suppiler.getShopName() == null){
			return buildJson(1,"卖家名称不能为空");
		} 
		
		try{
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			suppiler.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			suppiler.setCreateTime(new Timestamp(System.currentTimeMillis()));
			suppiler.setOperateId((int)user.getUser_id());
			supplierService.updateSupplier(suppiler);
			return buildJson(0,"编辑商家成功");
		}catch(Exception e){
			LOGGER.error("编辑供应商失败");
			return buildJson(1,"编辑供应商失败");
		}
		
	}
	
	@RequestMapping("editSupplierStatus")
	@ResponseBody
	public Object editSupplierStatus(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(defaultValue = "0") int shopId, @RequestParam int shopIsOpen) throws Exception{

		if(shopId == 0L) {
			return buildJson(1,"卖家ID为空");
		}
		try{
			User user = (User) request.getAttribute(Constants.MALLADMIN_USER);
			Suppiler suppiler = new Suppiler();
			suppiler.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			suppiler.setOperateId((int)user.getUser_id());
			suppiler.setShopIsOpen(shopIsOpen);
			suppiler.setShopId(shopId);
			supplierService.updateSuppilerStatus(suppiler);
			return buildJson(0,"操作成功");
		}catch(Exception e){
			LOGGER.error("操作失败", e);
			return buildJson(1,"操作失败");
		}
		
	}
	
}
