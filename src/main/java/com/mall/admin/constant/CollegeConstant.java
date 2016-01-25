package com.mall.admin.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.vo.mallbase.College;

public class CollegeConstant {
	private static Map<Long, College> collegeMap = new HashMap<Long, College>();
	private static Map<String, College> collegeNameMap = new HashMap<String, College>();
	private static List<College> collegeList = new ArrayList<College>();

	@Autowired
	private CollegeService collegeService;

	private CollegeConstant()
	{
	}

	/**
	 * 初始化函数
	 */
	public void init() {
		LogConstant.mallLog.debug("init college");
		refresh();
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		Map<Long, College> tempCollegeMap = new HashMap<Long, College>();
		Map<String, College> tempNameCollegeMap = new HashMap<String, College>();
		List<College> collegeList_temp = collegeService.getAllCollege();
		collegeList = collegeList_temp;
		if (!collegeList_temp.isEmpty()) {
			for (College college : collegeList_temp) {
				tempCollegeMap.put(college.getCollegeId(), college);
				tempNameCollegeMap.put(college.getCollegeName(), college);
			}
		}
		collegeMap = tempCollegeMap;
		collegeNameMap = tempNameCollegeMap;
		LogConstant.mallLog.info("refresh " + (collegeList_temp.isEmpty() ? 0 : collegeList_temp.size())
				+ " college");
	}

	/**
	 * 根据id获得整个College对象
	 * 
	 * @param key
	 * @return
	 */
	public static College getCollegeById(Long id) {
		return collegeMap.get(id);
	}

	/**
	 * 根据name获得整个College对象
	 * 
	 * @param key
	 * @return
	 */
	public static College getCollegeByName(String name) {
		return collegeNameMap.get(name);
	}

	public static Map<Long, College> getCollegeMap() {
		return collegeMap;
	}

	public static Map<String, College> getCollegeNameMap() {
		return collegeNameMap;
	}

	public static List<College> getCollegesByCityId(long cityId) {
		List<College> collegeList = Lists.newArrayList();
		for (College college : collegeMap.values()) {
			if (college.getCityId() == cityId) {
				collegeList.add(college);
			}
		}
		return collegeList;
	}
	
	public static List<College> getCollegesByAreaId(long areaId) {
		List<College> collegeList = Lists.newArrayList();
		for (College college : collegeMap.values()) {
			if (college.getAreaId() == areaId) {
				collegeList.add(college);
			}
		}
		return collegeList;
	}

	public static List<College> getCollegeList() {
		return collegeList;
	}
}
