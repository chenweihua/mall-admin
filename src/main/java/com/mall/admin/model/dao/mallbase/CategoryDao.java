package com.mall.admin.model.dao.mallbase;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.category.Category;

public interface CategoryDao {

	public List<Category> getAllCategories();

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
	 * 根据类目名称和是否展示首页查询
	 * 
	 * @param name
	 * @param showIndex
	 * @return
	 */
	public List<Category> getByName(Map<String, Object> params);

	public Category getById(long id);

	/**
	 * 根据用户id获得用户负责的类目
	 * 
	 * @param userId
	 * @return
	 */
	public List<Category> getCateGoryListByUserId(long userId);
}
