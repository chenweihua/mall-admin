package com.mall.admin.web.cache;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mall.admin.base.BaseController;
import com.mall.admin.constant.CategoryConstant;
import com.mall.admin.constant.CityConstant;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.DeliverCompanyConstant;
import com.mall.admin.constant.IniBean;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.RefreshConstant;
import com.mall.admin.util.DictionaryUtil;
import com.mall.admin.util.GsonUtil;


@Controller
public class RefreshController extends BaseController {

	@Autowired
	private CategoryConstant categoryConstant;
	@Autowired
	private CollegeConstant collegeConstant;
	@Autowired
	private CityConstant cityConstant;
	@Autowired
	private IniBean iniBean;
	
	@Autowired
	private DeliverCompanyConstant deliverCompanyConstant;

	@RequestMapping("/refresh/memory")
	@ResponseBody
	public Object getActivityGoods(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String action) throws IOException {
		long start = System.currentTimeMillis();
		if (StringUtils.isBlank(action)) {
			return buildJson(-1, "action名称错误！");
		}
		switch (action) {
		
		case RefreshConstant.REFRESH_CATEGORY_BEAN:
			categoryConstant.refresh();
			break;
		case RefreshConstant.REFRESH_CITY_BEAN:
			cityConstant.refresh();
			break;
		case RefreshConstant.REFRESH_COLLEGE_BEAN:
			collegeConstant.refresh();
			break;
		case RefreshConstant.REFRESH_DELIVER_COMPANY_BEAN:
			deliverCompanyConstant.refresh();
			break;
		case RefreshConstant.REFRESH_INI_BEAN:
			iniBean.refresh();
			break;
		default:
			break;
		}
		long end = System.currentTimeMillis();
		LogConstant.mallLog.info("[refresh][memory][action=" + action + "][cost=" + (end - start) + "ms]");

		return buildJson(0, action+"内存刷新成功！");
	}
	
	
	/**
	 *  以json形式直接展示应用服务器中的缓存数据，以备测试和查询问题使用
	 *  为防止该接口在生产中使用，在字典表type=5，name=SHOW_MEMORY中做了一个开关，1为可访问该接口，其余为不可访问
	 */
	@RequestMapping(value="/memory/show",produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object showMemory(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String action) throws IOException {
		
		if(!"1".equals(DictionaryUtil.getValueByTypeIdAndKey(5L, "SHOW_MEMORY"))) {
			return buildJson(1, "无权查看缓存！");
		}
		
		long start = System.currentTimeMillis();
		if (StringUtils.isBlank(action)) {
			return buildJson(1, "action为空！");
		}
		
		Object retObj = null;
		
		switch (action) {
		
			case RefreshConstant.REFRESH_CATEGORY_BEAN:
				retObj = CategoryConstant.getCategoryMap();
				break;
			case RefreshConstant.REFRESH_CITY_BEAN:
				retObj = CityConstant.getcityMap();
				break;
			case RefreshConstant.REFRESH_COLLEGE_BEAN:
				retObj = CollegeConstant.getCollegeMap();
				break;
			case RefreshConstant.REFRESH_DELIVER_COMPANY_BEAN:
				retObj = DeliverCompanyConstant.getDeliverCompanysMap();
				break;
			case RefreshConstant.REFRESH_INI_BEAN:
				retObj = iniBean.getMallIniMap();
				break;
			default:
				break;
		}
		long end = System.currentTimeMillis();
		LogConstant.mallLog.info("[show][memory][action=" + action + "][cost=" + (end - start) + "ms]");
		
		return GsonUtil.toJsonString(retObj);
	}
}
