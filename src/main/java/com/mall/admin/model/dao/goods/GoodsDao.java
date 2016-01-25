package com.mall.admin.model.dao.goods;

import java.util.List;
import java.util.Map;

import com.mall.admin.vo.goods.BgGoods;
import com.mall.admin.vo.goods.Goods;

public interface GoodsDao {

	public Goods getById(long goodsId);

	public List<Long> getBgGoodsIdByCollegeId(long collegeId);

	public Goods getByBgGoodsIdAndCollegeId(long bgGoodsId, long collegeId);

	public List<Long> getGoodsIdsByBgGoodsId(long bgGoodsId);

	public int deleteByBgGoodsId(long bgGoodsId);
	
	public int deleteGoodsAndSkuByBgGoodsId(long bgGoodsId);

	public int deleteByBgGoodsIdAndCollegeId(long bgGoodsId, long collegeId);

	public int updateStatus(long bgGoodsId, long collegeId, int status);

	public int updateMaxNum(long goodsId, long maxNum);

	public int batchUpdateMaxNum(long bgGoodsId, long maxNum);

	/**
	 * 字段为-1时，不起作用
	 * 
	 * @param bgGoodsId
	 * @param collegeId
	 * @param maxNum
	 * @return
	 */
	public int updateMaxNum(long bgGoodsId, long collegeId, long maxNum);

	public int updateWeightStatus(long goodsId, long weight, int status);

	public int updateByObject(Goods goods);

	public int updateByBgGoods(BgGoods bgGoods);

	public long insert(Goods goods);

	public long insertOrUpdate(Goods goods, boolean isOld);

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

	public List<Long> getCollegesByBgGoodsId(long bgGoodsId);
	
	public List<Long> getCollegeIdListByBgGoodsIdAndDistributeType(Long bgGoodsId,Integer distributeType);

	public List<Long> getRdcCollegesByBgGoodsId(long bgGoodsId);

	public List<Long> getLdcCollegesByBgGoodsId(long bgGoodsId);

	/**
	 * 获得需要删除的goods，删除条件是goods下的sku都不可用
	 * 
	 * @return
	 */
	public List<Long> getNeedDelGoods();

	/**
	 * 根据要删除的goodsIds修改goods的is_del状态为1
	 * 
	 * @param goodsIds
	 * @return
	 */
	public int deleteGoodsByIds(List<Long> goodsIds);

}
