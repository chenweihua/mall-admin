package com.mall.admin.web.express;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.service.express.ExpressBillService;
import com.mall.admin.service.outside.ExpressBillMarkService;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.util.DateUtil;
import com.mall.admin.util.RequestUtil;
import com.mall.admin.vo.express.ExpressBill;

/**
 * 面单标注
 *@date 2016年1月6日 下午2:16:23  
 *@author zhangshuai
 */
@Controller
@RequestMapping("/express/bill/")
public class ExpressBillMarkController extends BaseController {
	@Autowired
	private ExpressBillMarkService expressBillMarkService;
	@Autowired
	private ExpressBillService expressBillService;
	
	@RequestMapping("list")
	public Object toList(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		logInputData(request, "/express/bill/list", "跳转到面单标注界面...");
		String startDate = DateUtil.dateFormat(new Date());
		return new ModelAndView("expressMark/expressBillList",CommonUtil.asMap("startDate",startDate));
	}
	
	@ResponseBody
	@RequestMapping("query")
	public Object query(HttpServletRequest request,
			HttpServletResponse response,@RequestParam int draw,
			@RequestParam int start,
			@RequestParam(value = "length") int numPerPage,
			@RequestParam(defaultValue = "") String day,
			@RequestParam(defaultValue = "-1") Integer hour
			) throws SQLException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isEmpty(day)){
			return buildJson(-1, "请选择日期（天）");
		}
		Object total = 0;
		Object billList = new ArrayList<ExpressBill>();
		try {
			Date date = sdf.parse(day);
			Map<String, Object> retMap = expressBillMarkService.getListByTime(date, hour, start, numPerPage);
			total = retMap.get("total");
			billList = retMap.get("billList");
		} catch (Exception e) {
			LogConstant.mallLog.error(e);
			return buildJson(-1, "请选择日期（天），且保证格式正确");
		}
		return buildDataTableResult(0,"",draw, 0,total, billList);
	}

	@ResponseBody
	@RequestMapping("mark")
	public Object edit(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		ExpressBill expressBill = RequestUtil.toBean(request, ExpressBill.class,"yyyy-MM-dd hh:mm:ss");
		if(StringUtils.isEmpty(expressBill.getName())){
			return buildJson(-1, "姓名不能为空");
		}
		if(StringUtils.isEmpty(expressBill.getSchoolName())){
			return buildJson(-1, "学校不能为空");
		}
		if(StringUtils.isEmpty(expressBill.getAddress())){
			return buildJson(-1, "地址不能为空");
		}
		expressBill.setMark(JSONArray.toJSONString(CommonUtil.asMap("schoolName",expressBill.getSchoolName())));
		int ret = expressBillMarkService.markBill(expressBill);
		if(ret > -1){
			//本地保存
			expressBillService.insert(expressBill);
			return buildJson(0, "标注成功");
		}else{
			return buildJson(-1, "标注失败，请重试！");
		}
	}
}
