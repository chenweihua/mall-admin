package com.mall.admin.web;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.ImmutableMap;
import com.mall.admin.base.BaseController;
import com.mall.admin.constant.CityConstant;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.user.User;

@Controller
@RequestMapping("/city")
public class CityManagerController extends BaseController {
	@Autowired
	private CityService cityService;
	
	@Autowired
	private CityConstant cityConstant;

	@RequestMapping("/list")
	public Object list(HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("name");
		return new ModelAndView("city/list", CommonUtil.asMap("name", name));
	}

	@RequestMapping("/json")
	@ResponseBody
	public Object list(HttpServletRequest request,
			HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start,
			@RequestParam(value = "length") int numPerPage)
			throws SQLException, IOException, ParseException {
		String nameLike = request.getParameter("name").trim();

		if (nameLike != null) {
			nameLike = nameLike.replaceAll("[省,市,县,地级市,区,直辖市]", "");
		}

		Map params = new HashMap();
		if (nameLike != null && !nameLike.equals("")) {
			nameLike = nameLike.trim();
			params.put("nameLike", nameLike);
		}
		params.put("isDel", '0');
		params.put("pid",'0');

		List<City> cities = cityService.getCitiesByParams(params);

		int realStart = start;
		int total = cities.size();
		int realEnd = start + numPerPage;

		if (realEnd > total) {
			realEnd = total;
		}

		return gson.toJson(buildDataTableResult(draw, cities.size(),
				cities.size(), cities.subList(realStart, realEnd)));
	}

	@RequestMapping("/json/secCityList")
	@ResponseBody
	public Object list4SecCity(HttpServletRequest request,
			HttpServletResponse response, @RequestParam long cityId)
			throws SQLException, IOException, ParseException {

		List<City> cities = new ArrayList<City>();
		List<City> tempcity = cityService.getCityListByPid(cityId);
		for(City city : tempcity){
			if(city.getIsDel() == 0){
				cities.add(city);
			}
		}	
		return buildJson(0, "查询成功~",
				ImmutableMap.of("secCityListInCity", cities));
	}

	@RequestMapping("/add")
	@ResponseBody
	public Object add(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String cityName, @RequestParam int weight,
			@RequestParam long level, @RequestParam int isShow,
			@RequestParam(required = false, defaultValue = "0") long cityId,
			@RequestParam int isStop, @RequestParam(required = false) Long ucId) throws SQLException {

//		if (cityName != null) {
//			cityName = cityName.replaceAll("[省,市,县,地级市,区,直辖市]", "");
//		}
		User user = (User) request.getAttribute("user");

		City cityTmp = cityService.getCityByName(cityName);
		if (cityTmp != null && cityTmp.getPid() == 0 && cityTmp.getIsDel() == 0) {
			return buildJson(1, "城市信息已存在，请确认后重试");
		}
		
		if(ucId != null) {
			City cityTmp2 = cityService.getCityByUcId(ucId, level);
			if (cityTmp2 != null  && cityTmp2.getIsDel() == 0) {
				return buildJson(1, "UC ID信息已存在，请确认后重试");
			}
		}

		City city = new City();

		if (level == 2) {
			city.setPid(cityId);
		} else if (level == 1) {
			city.setPid(0);
		}

		city.setLevel(level);
		city.setCityName(cityName);
		city.setIsShow(isShow);
		city.setIsStop(isStop);
		city.setOperatorId(user.getUser_id());
		city.setWeight(weight);
		city.setUcId(ucId);
		cityService.add(city);
		cityConstant.refresh();
		return buildJson(0, "城市添加成功");
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Object edit(HttpServletRequest request,
			HttpServletResponse response, City city) throws SQLException {
		User user = (User) request.getAttribute("user");

		if(city.getUcId() != null) {
			City cityTmp2 = cityService.getCityByUcId(city.getUcId(),city.getLevel());
			if (cityTmp2 != null  && cityTmp2.getIsDel() == 0) {
				return buildJson(1, "UC ID信息已存在，请确认后重试");
			}
		}
		
		city.setOperatorId(user.user_id);
		cityService.edit(city);
		cityConstant.refresh();
		return buildJson(0, "修改成功");
	}

	@RequestMapping("/del")
	@ResponseBody
	public Object del(HttpServletRequest request, HttpServletResponse response,
			@RequestParam long cityId) throws SQLException {

		User user = (User) request.getAttribute("user");
		City cityTemp = cityService.getCityById(cityId);
		if (cityTemp == null || cityTemp.getIsDel() == 1) {
			return buildJson(1, "城市不存在或者已删除");
		}

		long operatorId = user.user_id;
		cityService.deleteById(cityId, operatorId);
		
		List<City> areaList = cityService.getCityListByPid(cityId);
		for(City area : areaList){
			cityService.deleteById(area.getCityId(), operatorId);
		}
		
		cityConstant.refresh();
		return buildJson(0, "软删除成功");
	}
}
