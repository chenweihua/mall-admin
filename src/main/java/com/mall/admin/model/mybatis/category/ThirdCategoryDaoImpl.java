package com.mall.admin.model.mybatis.category;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.category.ThirdCategoryDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.category.ThirdCategory;

@Repository
public class ThirdCategoryDaoImpl extends BaseMallDaoImpl implements ThirdCategoryDao {

	@Override
	@SuppressWarnings("unchecked")
	public Pair<Long, PaginationList<ThirdCategory>> getPageCategory(PaginationInfo paginationInfo, Map<String, Object> paramMap) {
		PaginationList<ThirdCategory> categoryList = selectPaginationList("ThirdCategory.getPageCategoryByPage", paramMap,
				paginationInfo);
		long totalCount = paginationInfo.getTotalRecord();
		return Pair.of(totalCount, categoryList);
	}

	@Override
	public long addCategory(ThirdCategory category) {
		return this.getSqlSession().insert("ThirdCategory.addCategory", category);
	}

	@Override
	public long updateCategory(ThirdCategory category) {
		return this.getSqlSession().update("ThirdCategory.updateCategory", category);

	}

	@Override
	public ThirdCategory getCategoryById(long categoryId) {
		return this.getSqlSession().selectOne("ThirdCategory.getCategoryById", categoryId);
	}

	@Override
	public int deleteCategory(long categoryId) {
		return this.getSqlSession().update("ThirdCategory.deleteCategory", categoryId);
	}
	
	/**
	 * 外来物种，暂时放着吧
	 */
	@Override
	public List<String> getSkuPropertyBySkuIdAndBgGoodsId(Long bgSkuId) {
		return this.getSqlSession().selectList("ThirdCategory.getSkuPropertyBySkuIdAndBgGoodsId", bgSkuId);
	}

}
