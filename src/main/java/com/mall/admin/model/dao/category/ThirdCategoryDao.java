package com.mall.admin.model.dao.category;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.category.ThirdCategory;


public interface ThirdCategoryDao {

	public Pair<Long,PaginationList<ThirdCategory>> getPageCategory(PaginationInfo paginationInfo, Map<String, Object> map);
	
	public long addCategory(ThirdCategory category);
	
	public long updateCategory(ThirdCategory category);
	
	public ThirdCategory getCategoryById(long id);

	public int deleteCategory(long id);

	public List<String> getSkuPropertyBySkuIdAndBgGoodsId(Long bgSkuId);

}
