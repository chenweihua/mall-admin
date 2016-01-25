package com.mall.admin.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.vo.category.Category;

/**
 * @author Singal
 *
 */
public class CategoryConstant {
	private static Map<Long, Category> categoryMap = new HashMap<Long, Category>();

	@Autowired
	private CategoryService categoryService;

	private CategoryConstant() {
	}

	/**
	 * 初始化函数
	 */
	public void init() {
		LogConstant.mallLog.info("init category");
		refresh();
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		Map<Long, Category> tempCategoryMap = new HashMap<Long, Category>();
		List<Category> categoryList = categoryService.getAllCategories();
		if (!categoryList.isEmpty()) {
			for (Category category : categoryList) {
				tempCategoryMap.put(category.getCategoryId(), category);
			}
		}
		categoryMap = tempCategoryMap;
		LogConstant.mallLog.info("refresh "
				+ (categoryList.isEmpty() ? 0 : categoryList.size())
				+ " category");
	}

	/**
	 * 根据id获得整个College对象
	 * 
	 * @param key
	 * @return
	 */
	public static Category getCategoryById(Long id) {
		return categoryMap.get(id);
	}

	public static Map<Long, Category> getCategoryMap() {
		return categoryMap;
	}
}
