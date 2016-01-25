package com.mall.admin.web.ump.coupon;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.mall.admin.base.BaseController;
import com.mall.admin.enumdata.CouponSwitchType;
import com.mall.admin.service.ump.coupon.CouponService;
import com.mall.admin.vo.ump.MallIni;



@Controller
@RequestMapping("/coupon/switch")
public class CouponSwitchController extends BaseController {
	
	@Autowired
	private CouponService couponService;
	
	@RequestMapping("/list")
	public Object couponSwitchList(HttpServletRequest request, HttpServletResponse response){
		ModelAndView model = new ModelAndView("ump/coupon/switch");
		return model;
	}
	
	
	@RequestMapping("/ajaxList")
	@ResponseBody
	public Object couponList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam int draw, 
			@RequestParam int start, 
			@RequestParam(value = "length") int numPerPage){
		try{
			Pair<Integer, List<MallIni>> mallIniList = couponService.getSwitchFromMallIni(CouponSwitchType.FISSION_SWITCH.getValue(),start, numPerPage);
			 return buildDataTableResult(draw, 0, mallIniList.getLeft(), mallIniList.getRight());
		}catch(Exception e){
			return buildDataTableResult(draw, 0, 0, Lists.newArrayList());
		}
	}
	
	/**
	 * 修改流程
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value ="change")
	@ResponseBody
	public Object endCouponBatch(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String switchName,
			@RequestParam String value){
			Integer st = couponService.updateSwitchFromMallIni(switchName,"0".equals(value)?"1":"0");
			return buildJson(st, "");
	}
	
}
