package com.mall.admin.service.goods;

import java.util.List;
import java.util.Map;

import com.mall.admin.vo.goods.Goods;

public interface GoodsService {

	public Goods getById(long goodsId);

	// 插入
	public Map<String, Object> addGoods(Goods goods, Map<String, Integer> skuParam);

	/**
	 * 获取所有的goodsId
	 * 
	 * @param categoryId
	 * @param collegeId
	 * @return
	 */
	public List<Long> selectSingleGoods(long categoryId, long collegeId);

	/**
	 * 获取多个bgGoodsId对应的学校
	 * 
	 * @param bgGoodsId
	 * @return
	 */
	public List<Long> getCollegeIds(long bgGoodsId);

	public List<Long> getRdcCollegeIds(long bgGoodsId);

	public List<Long> getLdcCollegeIds(long bgGoodsId);
	
	public List<Long> getVmCollegeIds(long bgGoodsId);

	/**
	 * 根据bgGoods和storageId,更新前台数据
	 * 
	 * @param bgGoodsId
	 * @param storageId
	 * @param weight
	 * @param stock
	 * @param status
	 * @param isLdc
	 *                0:RDC；1:LDC
	 * @return -1：失败；1：成功；2：失败，表示需要先设置价格
	 */
	public String updateWeightStockStatus(long bgGoodsId, long storageId, long weight, long stock, int status,
			int isLdc);

	public String updateStatus(long bgGoodsId, long storageId, int status, int isLdc);

	public boolean updateWeightStockStatus(long goodsId, long weight, long stock, int status, int isLdc);

	/**
	 * 字段为-1时，不起作用
	 * 
	 * @param bgGoodsId
	 * @param collegeId
	 * @param maxNum
	 * @return
	 */
	public int updateMaxNum(long bgGoodsId, long collegeId, long maxNum);

	/**
	 * 删除有问题的goods
	 * 
	 * @return
	 */
	public int deleteUnusedGoods();
}
