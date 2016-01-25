package com.mall.admin.model.dao.goods;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.dto.BgGoods4Manager;

public interface BgGoodsDao {

	public BgGoods getById(long bgGoodsId);

	public BgGoods getSingByBgSkuId(long bgSkuId);

	public boolean isExist(long bgGoodsId);

	public int deleteById(long id);

	public int updateByObject(BgGoods bgGoods);
	
	public int updateStatusByBgGoodsId(Long bgGoodsId,int goodsStatus);

	public long insert(BgGoods bgGoods);

	/**
	 * 返回总页数total和结果集List<BgGoods>
	 * 
	 * @param start
	 * @param numPerPage
	 * @param params
	 * @return
	 */
	public Map<String, Object> selectSingleByPage(int start, int numPerPage, long categoryId, long collegeId,
			String name);

	public Map<String, Object> selectSingleByPage(int start, int numPerPage, String searchStr);

	public List<BgGoods> selectSingleByPage(String searchStr, PaginationInfo paginationInfo);

	public List<BgGoods4Manager> getGoodsInfoByPage(Map<String, Object> map, PaginationInfo paginationInfo);
	
	public List<BgGoods4Manager> getGoods4VmStorageInfoByPage(Map<String, Object> map, PaginationInfo paginationInfo);

	public List<BgGoods4Manager> getBgGoodsInfoByPage(Map<String, Object> map, PaginationInfo paginationInfo);
	
	public List<BgGoods> getBgGoodsList(String searchStr, PaginationInfo paginationInfo);

	/**
	 * 根据供应链商品id获得后台单品商品的id
	 * 
	 * @param wmsGoodsId
	 * @return
	 */
	public List<BgGoods> getSingleBgGoodsByWmsGoodsId(long wmsGoodsId);

	/**
	 * 根据供应链商品id获得聚合商品的id
	 * 
	 * @param wmsGoodsId
	 * @return
	 */
	public List<BgGoods> getMultBgGoodsByWmsGoodsId(long wmsGoodsId);
	
	public int updateUpdateTimeByBgGoodsId(long bgGoodsId);
	
	public List<BgGoods4Manager> getBgGoodsDtoByPage(Map<String, Object> map, PaginationInfo paginationInfo);
}
