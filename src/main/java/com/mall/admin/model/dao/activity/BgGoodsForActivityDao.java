package com.mall.admin.model.dao.activity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.activity.BgGoodsForActivity;

public interface BgGoodsForActivityDao {

	public BgGoodsForActivity getById(long activityBgGoodsId);

	/**
	 * 根据活动id和商品名称查询符合条件的列表
	 * 
	 * @param activityIds
	 * @return
	 */
	public List<BgGoodsForActivity> getListByActivityIdAndGondsName(Long[] activityIds, String goodsName);

	public int insert(BgGoodsForActivity bean);

	public int update(BgGoodsForActivity bean);

	/**
	 * 
	 * @param activityId
	 * @param bgGoodsId
	 * @param storageType
	 * @return
	 */
	public BgGoodsForActivity queryByActivityIdAndBgGoodsId(long activityId, long bgGoodsId, int storageType);

	/**
	 * 根据活动模板商品id （activityBgGoodsId），批量修改权重，限售值，状态
	 * 
	 * @param weight
	 * @param maxNum
	 * @param status
	 * @param activityBgGoodsId
	 * @return
	 */
	public int update(long weight, long maxNum, long status, long activityBgGoodsId);
	
	public int updateGoodsStatus(long goodsStatus, long activityBgGoodsId);

	/**
	 * 根据活动模板商品id （activityBgGoodsId），批量修改开始时间，结束时间和显示时间
	 * 
	 * @param beginTime
	 * @param endTime
	 * @param showTime
	 * @param activityBgGoodsId
	 * @return
	 */
	public int updateTime(Date beginTime, Date endTime, Date showTime, long activityBgGoodsId);

	/**
	 * 更新商品的可用状态
	 * 
	 * @param activityBgGoodsId
	 *                活动模板商品id
	 * @param isDel
	 *                0：可用 1：不可用
	 * @return
	 */
	public int updateIsDelStatus(long activityBgGoodsId, int isDel);

	/**
	 * 查询满足条件的商品
	 * 
	 * @param beginDate
	 * @param endDate
	 *                beginDate-endDate 活动商品开始时间在这个范围内的商品
	 * @param activityId
	 *                所属活动id
	 * @param goodsName
	 *                商品名称
	 * @param status
	 *                状态 0：全部；1：待售；2：在售；3：售罄
	 * @param isSeckill
	 *                是否秒杀商品 0：是；1：否
	 * @param paginationInfo
	 *                分页信息
	 * @return
	 */
	public List<BgGoodsForActivity> queryBeGoodsForActivityByQuery(Date beginDate, Date endDate, long activityId,
			String goodsName, int status, int isSeckill, PaginationInfo paginationInfo);
	

	/**
	 * 获得活动下所售卖的商品
	 * 
	 * @param activityId
	 * @return
	 */
	public List<BgGoodsForActivity> getBeanListByActivityId(long activityId);
	
	public List<BgGoodsForActivity> getBeanListByActivityId(long activityId,int isDel);

	/**
	 * 获得添加过该商品的活动
	 * 
	 * @param bgGoodsId
	 * @return
	 */
	public List<BgGoodsForActivity> getBeanListByBgGoodsId(long bgGoodsId);
	
	public BgGoodsForActivity queryBgGoodsForActivity(long activityGoodsId);

	public List<BgGoodsForActivity> getThirdBgGoods4Activity(Map<String, Object> params,PaginationInfo paginationInfo);
}
