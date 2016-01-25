package com.mall.admin.model.dao.activity;

import java.util.Date;
import java.util.List;

import com.mall.admin.vo.activity.ActivityGoods;

/**
 * 活动商品信息
 * 
 * @author Administrator
 *
 */
public interface ActivityGoodsDao {

	public int insert(ActivityGoods bean);

	public ActivityGoods getByUnionKey(Long activityBgGoodsId, Long collegeId);

	public int update(ActivityGoods bean);

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
	
	public int updateGoodsStatus(Integer goodsStatus, long activityBgGoodsId);

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
	 * 根据活动商品模板id查询售卖的学校（包括可用和不可用的商品，根据可用状态做不同的处理）
	 * 
	 * @param activityBgGoodsId
	 * @return
	 */
	public List<ActivityGoods> getActivitGoodsList(long activityBgGoodsId);

	/**
	 * 更新活动下商品在学校下的售卖状态
	 * 
	 * @param activityBgGoodsId
	 * @param isDel
	 *                0:可用 1：不可用
	 * @param collegeId
	 * @return
	 */
	public int updateIsDelStatusInCollege(long activityBgGoodsId, int isDel, long collegeId);

	/**
	 * 根据学校和后台商品id，更新学校下活动商品的可用状态
	 * 
	 * @param bgGoodsId
	 * @param collegeId
	 * @param isDel
	 *                0:可用 1：不可用
	 * @param distributeType
	 *                0:rdc 1:ldc
	 * @return
	 */
	public int updateStatusByBgGoodsIdAndCollegeID(long bgGoodsId, long collegeId, int isDel, int distributeType);

	/**
	 * 更新活动下商品是否可用
	 * 
	 * @param collegeId
	 *                学校id
	 * @param distributeType
	 *                0：rdc；1：ldc
	 * @param isDel
	 *                是否可用 0：可用；1：不可用
	 * @return
	 */
	public int updateActivityGoodsIsDelInCollege(long collegeId, int distributeType, int isDel);

	/**
	 * 查询某学校下的活动商品 bgGoodsId是选填。
	 * 
	 * @param collegeId
	 * @param bggoodsId
	 * @return
	 */
	public List<ActivityGoods> getActivityGoodsListByCID(long collegeId, long bgGoodsId);
	
	public ActivityGoods queryActivityGoods(long bgGoodsForActivityId, long activityId, long collegeId) ;
}
