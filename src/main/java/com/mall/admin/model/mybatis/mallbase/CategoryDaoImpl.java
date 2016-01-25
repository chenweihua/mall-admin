package com.mall.admin.model.mybatis.mallbase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.mallbase.CategoryDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.category.Category;

@Repository
public class CategoryDaoImpl extends BaseMallDaoImpl implements CategoryDao {

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = this.getSqlSession().selectList(
				"Category.getAllCategories");
		return categories;
	}

	@Override
	public List<Category> getPageCategories(Map<String, Object> params,
			PaginationInfo paginationInfo) {
		// Map<String, PaginationInfo> parameterMap = new
		// HashMap<String,
		// PaginationInfo>();
		// parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<Category> categoriesList = selectPaginationList(
				"Category.getPageCategories", params, paginationInfo);
		return categoriesList;
	}

	@Override
	public int add(Category category) {
		// TODO Auto-generated method stub
		int i = this.getSqlSession().insert("Category.add", category);
		return i;
	}

	@Override
	public int deleteById(long id, long operator) {
		// TODO Auto-generated method stub

		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("categoryId", id);
		params.put("operator", operator);

		int i = this.getSqlSession().update("Category.deleteById", params);
		return i;
	}

	@Override
	public int edit(Category category) {
		int i = this.getSqlSession()
				.update("Category.updateCategory", category);
		return i;
	}

	@Override
	public List<Category> getByName(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("Category.getByName", params);
	}

	@Override
	public Category getById(long id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("Category.getById", id);
	}

	@Override
	public List<Category> getCateGoryListByUserId(long userId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("Category.getCateGoryByUserId",
				userId);
	}

}
