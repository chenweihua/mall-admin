package com.mall.admin.service.mallbase.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.mallbase.CategoryDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.vo.category.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Override
	public List<Category> getPageCategories(Map<String, Object> params,
			PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		List<Category> categories = categoryDao.getPageCategories(params,
				paginationInfo);

		return categories;
	}

	@Override
	public int add(Category category) {
		return categoryDao.add(category);
	}

	@Override
	public int deleteById(long id, long operator) {
		return categoryDao.deleteById(id, operator);
	}

	@Override
	public int edit(Category category) {
		return categoryDao.edit(category);
	}

	@Override
	public List<Category> getByName(Map<String, Object> params) {
		return categoryDao.getByName(params);
	}

	@Override
	public Category getById(long id) {
		return categoryDao.getById(id);
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryDao.getAllCategories();
	}

	@Override
	public List<Category> getByName(String name) {
		// TODO Auto-generated method stub
		List<Category> categories = new ArrayList<Category>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("showIndex", "*");
		categories.addAll(categoryDao.getByName(params));
		return categories;
	}

	@Override
	public List<Category> getCategoryByUserId(long userId) {
		// TODO Auto-generated method stub
		return categoryDao.getCateGoryListByUserId(userId);
	}

}
