package com.mall.admin.service.mallbase;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.category.Category;

public interface CategoryService {
	/**
	 * 分页查询类目
	 * 
	 * @param paginationInfo
	 * @return
	 */
	public List<Category> getPageCategories(Map<String, Object> params,
			PaginationInfo paginationInfo);

	/**
	 * 添加类目
	 * 
	 * @param category
	 * @return
	 */
	public int add(Category category);

	/**
	 * 根据id删除类目
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(long id, long operator);

	/**
	 * 编辑类目
	 * 
	 * @param category
	 * @return
	 */
	public int edit(Category category);

	/**
	 * 根据条件查询
	 * 
	 * @param params
	 * @param showIndex
	 * @return
	 */
	public List<Category> getByName(Map<String, Object> params);

	public List<Category> getByName(String name);

	/**
	 * 根据id获得类目
	 * 
	 * @param id
	 * @return
	 */
	public Category getById(long id);

	/**
	 * 获取所有类目
	 * 
	 * @return
	 */
	public List<Category> getAllCategories();

	/**
	 * 根据用户id获得用户的类目
	 * 
	 * @param userId
	 * @return
	 */
	public List<Category> getCategoryByUserId(long userId);
}
