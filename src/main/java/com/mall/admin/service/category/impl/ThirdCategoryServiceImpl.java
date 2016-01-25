package com.mall.admin.service.category.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.category.ThirdCategoryDao;
import com.mall.admin.model.dao.goods.BgGoodsDao;
import com.mall.admin.model.dao.goods.SkuPropertyDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.service.category.ThirdCategoryService;
import com.mall.admin.vo.category.ThirdCategory;
import com.mall.admin.vo.goods.BgGoods;

@Service
public class ThirdCategoryServiceImpl implements ThirdCategoryService{

	@Autowired
	private ThirdCategoryDao categoryDao;
	@Autowired
	BgGoodsDao bgGoodsDao;
	@Autowired
	private SkuPropertyDao skuPropertyDao;

	@Override
	public long addCategory(ThirdCategory category) {
		return categoryDao.addCategory(category);
	}

	@Override
	public long updateCategory(ThirdCategory category) {
		return categoryDao.updateCategory(category);
	}
	
	@Override
	public Pair<Long,PaginationList<ThirdCategory>> getPageCategory(PaginationInfo paginationInfo,
			Map<String, Object> map) {
		return categoryDao.getPageCategory(paginationInfo,map);
	}
	
	@Override
	public ThirdCategory getCategoryById(long id) {
		return categoryDao.getCategoryById(id);
	}

	@Override
	public int deleteCategory(long id) {
		return categoryDao.deleteCategory(id);
	}
	
	@Override
	public ThirdCategory getCategoryBySkuId(long bgSkuId) {
		BgGoods bgGoods = getBgGoods(bgSkuId);
		if(bgGoods == null) {
			long bgGoodsId = skuPropertyDao.getBgGoodsIdBybgSkuId(bgSkuId);
			if(bgGoodsId == -1){
				return null;
			}
			bgGoods = bgGoodsDao.getById(bgGoodsId);
		}
		if(bgGoods == null){
			return null;
		}
		return getCategoryById(bgGoods.getCategoryId());
	}
	
	@Override
	public List<String> getSkuPropertyBySkuIdAndBgGoodsId(long bgSkuId) {
	/*	BgGoods bgGoods = getBgGoods(bgSkuId);
		if(bgGoods == null) {
			return null;
		}
		Map<String, Object> param = Maps.newHashMap();
		param.put("bgSkuId", bgSkuId);
		param.put("bgGoodsId", bgGoods.getBgGoodsId());*/
		return categoryDao.getSkuPropertyBySkuIdAndBgGoodsId(bgSkuId);
	}
	
	private BgGoods getBgGoods(long bgSkuId) {
		return bgGoodsDao.getSingByBgSkuId(bgSkuId);
	}

}
