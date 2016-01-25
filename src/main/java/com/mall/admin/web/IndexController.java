package com.mall.admin.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mall.admin.base.BaseController;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.vo.mallbase.College;

@Controller
public class IndexController extends BaseController {

	@Autowired
	private CollegeService collegeService;

	@RequestMapping("/test")
	public Object index(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException {
		// log.info("welcome to IndexController!");
		LogConstant.mallLog.info("IndexController index");
		College college = CollegeConstant.getCollegeById(1L);
		request.setAttribute("College", college);
		ModelAndView mav = new ModelAndView("indexDemo");
		return mav;
	}

	@RequestMapping("/test/searchdemo")
	public Object searchindex(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		ModelAndView mav = new ModelAndView("searchDemo");
		return mav;
	}

	@RequestMapping("/test/searchlikedemo")
	public Object searchlikeindex(HttpServletRequest request,
			HttpServletResponse response) throws IOException, SQLException {
		ModelAndView mav = new ModelAndView("searchLikeDemo");
		return mav;
	}

	@RequestMapping("/test/search")
	public Object searchCollege(HttpServletRequest request,
			HttpServletResponse response, String name) throws IOException,
			SQLException {
		// log.info("welcome to IndexController!");
		College college = CollegeConstant.getCollegeByName(name);
		ArrayList<College> collegeList = new ArrayList<College>();
		collegeList.add(college);
		request.setAttribute("collegeList", collegeList);
		ModelAndView mav = new ModelAndView("searchResultDemo");
		return mav;
	}

	@RequestMapping("/test/searchlike")
	public Object searchLikeCollege(HttpServletRequest request,
			HttpServletResponse response, String name) throws IOException,
			SQLException {
		// log.info("welcome to IndexController!");
		Map<String, College> collegeNameMap = CollegeConstant
				.getCollegeNameMap();
		ArrayList<College> collegeList = new ArrayList<College>();
		for (Entry<String, College> entry : collegeNameMap.entrySet()) {
			String collegeName = entry.getKey();
			College college = entry.getValue();
			if (collegeName.contains(name)) {
				collegeList.add(college);
			}
		}
		request.setAttribute("collegeList", collegeList);
		ModelAndView mav = new ModelAndView("searchResultDemo");
		return mav;
	}

	@RequestMapping("/test/searchpage")
	public Object searchPageCollege(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue = "1") Integer currentPage)
			throws IOException, SQLException {
		// log.info("welcome to IndexController!");
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPage(currentPage);
		List<College> collegeList = collegeService
				.getPageCollege(paginationInfo);
		request.setAttribute("collegeList", collegeList);
		ModelAndView mav = new ModelAndView("searchResultDemo");
		return mav;
	}
}
