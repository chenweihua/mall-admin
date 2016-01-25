package com.mall.admin.model.dao.navigation;

import java.util.List;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.navigation.NavigationGoods;

public interface NavigationGoodsDao {

	/**
	 * 查询商品的信息
	 * 
	 * @param goodsStatus
	 *                1：选品池，2：售卖池
	 * @param navGoodsName
	 *                商品名称
	 * @param navMenuId
	 *                所属菜单
	 * @param paginationInfo
	 *                分页信息
	 * @return
	 */
	public List<NavigationGoods> queryNavGoods(Integer goodsStatus, String navGoodsName, long navMenuId,
			Integer isOpen, PaginationInfo paginationInfo);

	/**
	 * 添加商品信息
	 * 
	 * @param navGoods
	 * @return
	 */
	public int insert(NavigationGoods navGoods);

	/**
	 * 更新商品信息
	 * 
	 * @param navGoods
	 * @return
	 */
	public int update(NavigationGoods navGoods);

	/**
	 * 删除商品
	 * 
	 * @param navGoodsId
	 * @return
	 */
	public int deleteNavGoods(long navGoodsId);

	/**
	 * 设置菜单是否可见
	 * 
	 * @param navGoodsId
	 * @param isOpen
	 *                0：不可见 1：可见
	 * @return
	 */
	public int setOpenStatus(long navGoodsId, int isOpen);

	/**
	 * 将选品池中的商品添加到售卖池中
	 * 
	 * @param navGoodsId
	 * @return
	 */
	public int putNavGoodsToSalePool(long navGoodsId);

	/**
	 * 将选品池中的商品添加到选品池中
	 * 
	 * @param navGoodsId
	 * @return
	 */
	public int putNavGoodsToSelectPool(long navGoodsId);

	/**
	 * 根据菜单Id获得该菜单下所有的商品
	 * 
	 * @param menuId
	 * @return
	 */
	public List<NavigationGoods> getGoodsByMenuId(long menuId);

	/**
	 * 删除菜单下所有的商品
	 * 
	 * @param menuId
	 * @return
	 */
	public int deleteGoodsByMenuId(long menuId);

}
