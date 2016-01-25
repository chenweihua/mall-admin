package com.mall.admin.web;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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

import com.mall.admin.base.BaseController;
import com.mall.admin.base._;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.banner.BannerService;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.vo.category.Category;
import com.mall.admin.vo.user.User;

@Controller
@RequestMapping("/category")
public class CategoryManagerController extends BaseController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	BannerService bannerService;

	@RequestMapping("/list")
	public Object list(HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("name");
		String isIndex = request.getParameter("isIndex");
		if ("2".equals(isIndex))
			isIndex = "";

		return new ModelAndView("category/list", CommonUtil.asMap("isIndex", isIndex, "name", name));
	}

	@RequestMapping("/json")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response, @RequestParam int draw,
			@RequestParam int start, @RequestParam(value = "length") int numPerPage) throws SQLException,
			IOException, ParseException {
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageByStartNum(start, numPerPage);
		paginationInfo.setRecordPerPage(numPerPage);
		String nameLike = request.getParameter("name");
		if (nameLike != null) {
			nameLike = nameLike.trim();
		}

		String showIndex = request.getParameter("isIndex");
		if (showIndex != null) {
			if(showIndex.equals("2")){
				showIndex = "";
			}
		}

		Map<String, Object> params = new HashMap<String, Object>(2);
		
		
		params.put("nameLike", nameLike);
		params.put("showIndex", showIndex);

		List<Category> categoriesList = categoryService.getPageCategories(params, paginationInfo);

		// System.out.println(categoriesList);

		int realStart = start;
		int total = categoriesList.size();
		int realEnd = start + numPerPage;

		if (realEnd > total) {
			realEnd = total;
		}

		return gson.toJson(buildDataTableResult(draw, categoriesList.size(), categoriesList.size(),
				categoriesList.subList(realStart, realEnd)));
	}

	@RequestMapping("/add")
	@ResponseBody
	public Object add(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "0") String isIndex,
			@RequestParam String categoryName, @RequestParam String description,
			@RequestParam String weight,@RequestParam int labelType,
			@RequestParam(required = false, defaultValue = "") String imageUrl1,
			@RequestParam(required = false, defaultValue = "") String imageUrl2,
			@RequestParam(required = false, defaultValue = "") String imageUrl3,
			@RequestParam(required = false, defaultValue = "") String indexWeight) throws SQLException {

		User user = (User) request.getAttribute("user");
		Category category = new Category();
		category.setCategoryName(categoryName);
		category.setLabelType(labelType);
		category.setWeight(Integer.parseInt(weight));
		category.setShowIndex(_.toInt(isIndex,0));
		category.setDescription(description);
		category.setIconOff(imageUrl2);
		category.setIconOn(imageUrl1);
		category.setAppIcon(imageUrl3);
		category.setIndexWeight(_.toInt(indexWeight,0));
		category.setOperator(user.user_id);

		List<Category> categoryTmp = categoryService.getByName(categoryName);

		if (!categoryTmp.isEmpty()) {
			return buildJson(1, "品类名已存在");
		}
		categoryService.add(category);

		return buildJson(0, "品类添加成功");
	}

	@RequestMapping("/edit")
	@ResponseBody
	public Object edit(HttpServletRequest request, HttpServletResponse response, Category category,
			@RequestParam String imageUrl1, @RequestParam String imageUrl2,@RequestParam String imageUrl3,@RequestParam int showIndex) throws SQLException {
		User user = (User) request.getAttribute("user");

		category.setOperator(user.user_id);
		category.setIconOn(imageUrl1);
		category.setIconOff(imageUrl2);
		category.setAppIcon(imageUrl3);
		category.setShowIndex(showIndex);

		categoryService.edit(category);

		return buildJson(0, "修改成功");
	}

	@RequestMapping("/del")
	@ResponseBody
	public Object del(HttpServletRequest request, HttpServletResponse response, @RequestParam long categoryId)
			throws SQLException {

		User user = (User) request.getAttribute("user");
		Category categoryTemp = categoryService.getById(categoryId);
		if (categoryTemp == null || categoryTemp.getIsDel() == 1) {
			return buildJson(1, "类目不存在或者已删除");
		}

		categoryService.deleteById(categoryId, user.user_id);
		bannerService.batchDeleteByCategoryId(categoryId);

		return buildJson(0, "软删除成功");
	}
}
