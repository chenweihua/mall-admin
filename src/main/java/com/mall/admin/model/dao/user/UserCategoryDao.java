package com.mall.admin.model.dao.user;

import com.mall.admin.vo.user.UserAndCategory;

public interface UserCategoryDao {
	/**
	 * 根据用户id删除该用户的所有类目
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteUserCatetory(long userId);

	/**
	 * 插入用户和类目关系表
	 * 
	 * @param userCategory
	 * @return
	 */
	public int insert(UserAndCategory userCategory);

}
