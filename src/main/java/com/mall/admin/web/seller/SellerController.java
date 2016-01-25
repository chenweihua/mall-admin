package com.mall.admin.web.seller;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mall.admin.base.BaseController;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.SellerConstant;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.service.seller.SellerService;
import com.mall.admin.vo.seller.Seller;


@Controller
@RequestMapping("/seller/")
public class SellerController extends BaseController{

	@Autowired
	private SellerService sellerService;
	@Autowired
	private SellerConstant sellerConstant;
	
	@RequestMapping("view")
	public Object view(HttpServletRequest request, HttpServletResponse response) throws SQLException,
	IOException{
		LogConstant.mallLog.info("SellerController seller");
		ModelAndView mav = new ModelAndView("seller/sellerlist");
		return new ModelAndView("seller/sellerlist");
	}
	
	@RequestMapping("querySeller")
	@ResponseBody
	public Object querySeller(HttpServletRequest request,HttpServletResponse response,
		@RequestParam int draw,
		@RequestParam(value = "start",required = false) Integer start, 
		@RequestParam(value = "length",required = false) Integer numPerPage,
		@RequestParam(value = "searchStr",required = false) String searchStr) throws SQLException,IOException{	
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setRecordPerPage(numPerPage);
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		List<Seller> sellerList;
		Map param = new HashMap();		
		param.put("seller_id_like", searchStr);
		param.put("seller_name_like", searchStr);
		try{
			Pair<Long, PaginationList<Seller>> p = sellerService.getPageSeller(paginationInfo,param);
			sellerList = p.getRight();
			long filteredTotal = p.getLeft();
			long total = 0;
			return gson.toJson(buildDataTableResult(draw, total, filteredTotal, sellerList));
		}catch(Exception e){
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"添加供应商失败","searchStr"+searchStr);
			return buildJson(1,"获取供应商失败");
		}
	}
	
	@RequestMapping("addSeller")
	@ResponseBody
	public Object addSeller(HttpServletRequest request,HttpServletResponse response,
		@RequestParam String sellerName) throws SQLException,IOException{
		
 
		Seller seller = new Seller();
		seller.sellerName = sellerName.trim();
		Map<String,Seller> sellerNameMap = sellerConstant.getSellerNameMap();
		if(sellerNameMap.get(sellerName.trim()) != null){
			return buildJson(1,"供应商名字重复");
		}
		
		try{
			sellerService.addSeller(seller);
			sellerConstant.refresh();
			return buildJson(0,"添加商家成功");
		}catch(Exception e){
			e.printStackTrace();
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"添加供应商失败","&sellerName:"+sellerName);
			return buildJson(1,"添加供应商失败");
		}
	} 
	
	@RequestMapping("editSeller")
	@ResponseBody
	public Object editSeller(HttpServletRequest request,HttpServletResponse response,
			@RequestParam long sellerId,
			@RequestParam String sellerName) throws SQLException,IOException{
		
		if(sellerId == 0){
			return buildJson(1,"卖家名称不能为空");
		}
		
		if(sellerName == null){
			return buildJson(1,"卖家名称不能为空");
		} 
		
		Seller seller = new Seller();
		seller.sellerId = sellerId;
		seller.sellerName = sellerName.trim();
		try{
			sellerService.updateSeller(seller);
			sellerConstant.refresh();
			return buildJson(0,"编辑商家成功");
		}catch(Exception e){
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"编辑供应商失败","sellerId:"+sellerId+"&sellerName:"+sellerName);
			return buildJson(1,"编辑供应商失败");
		}
		
	}
	
	@RequestMapping("deleteseller")
	@ResponseBody
	public Object deleteseller(HttpServletRequest request,HttpServletResponse response,
			@RequestParam Long sellerId) throws SQLException,IOException{
		
		if(sellerId == null){
			return buildJson(1,"供应商编码不能为空");
		}
		try{
			sellerService.deleteSeller(sellerId);
			sellerConstant.refresh();
			return buildJson(0,"删除供应商成功");
		}catch(Exception e){
			LogConstant.mallLog.error("code:{},msg:{},params:{}",1,"编辑供应商失败","sellerId:"+sellerId);
			return buildJson(1,"编辑供应商失败");
		}
		
	}
		
}
