package com.mall.admin.model.mybatis.navigation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;
import com.mall.admin.model.dao.navigation.NavigationGoodsDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.navigation.NavigationGoods;

@Repository
public class NavigationGoodsDaoImpl extends BaseMallDaoImpl implements NavigationGoodsDao {

	@Override
	public List<NavigationGoods> queryNavGoods(Integer goodsStatus, String navGoodsName, long navMenuId,
			Integer isOpen, PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("goodsStatus", goodsStatus);
		if (!Strings.isNullOrEmpty(navGoodsName)) {
			paramMap.put("navGoodsName", navGoodsName);
		}
		if (navMenuId > 0) {
			paramMap.put("navMenuId", navMenuId);
		}

		if (isOpen != null && (isOpen == 0 || isOpen == 1)) {
			paramMap.put("isOpen", isOpen);
		}

		return selectPaginationList("NavigationGoods.selectNavGoodsByPage", paramMap, paginationInfo);
	}

	@Override
	public int insert(NavigationGoods navGoods) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("NavigationGoods.insertNavigationGoods", navGoods);
	}

	@Override
	public int update(NavigationGoods navGoods) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("NavigationGoods.updateNavigationGoods", navGoods);
	}

	@Override
	public int deleteNavGoods(long navGoodsId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("NavigationGoods.delNavigationGoodsById", navGoodsId);
	}

	@Override
	public int setOpenStatus(long navGoodsId, int isOpen) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("navGoodsId", navGoodsId);
		paramMap.put("isOpen", isOpen);
		return this.getSqlSession().update("NavigationGoods.setOpenStatusById", paramMap);
	}

	@Override
	public int putNavGoodsToSalePool(long navGoodsId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("NavigationGoods.putNavGoodsToSalePool", navGoodsId);
	}

	/**
	 * 将选品池中的商品添加到选品池中
	 * 
	 * @param navGoodsId
	 * @return
	 */
	@Override
	public int putNavGoodsToSelectPool(long navGoodsId) {
		return this.getSqlSession().update("NavigationGoods.putNavGoodsToSelectPool", navGoodsId);
	}

	@Override
	public List<NavigationGoods> getGoodsByMenuId(long menuId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("NavigationGoods.queryGoodsByMenuId", menuId);
	}

	@Override
	public int deleteGoodsByMenuId(long menuId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("NavigationGoods.deleteGoodsByMenuId", menuId);
	}

}
