package com.mall.admin.service.activity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mall.admin.model.bean.activity.ActivityGoodsApplyBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.activity.ActivityGoods;
import com.mall.admin.vo.activity.BgGoodsForActivity;
import com.mall.admin.vo.goods.Goods;
import com.mall.admin.vo.goods.Sku;

public interface BgGoodsForActivityService {

	public BgGoodsForActivity getById(long activityBgGoodsId);
	/**
	 * 查询商品可售卖的学校列表
	 * 
	 * @param bgGoodsId
	 *                后台商品id
	 * @param storageType
	 *                仓库类型 0：rdc仓；1：ldc仓；-1所有仓
	 * @return
	 */
	public List<Long> getCollegeIds(long bgGoodsId, int storageType);
	
	
	public void delBgGoodsForActivity(long activityId);
	

	/**
	 * 在添加活动后台模板goods时，需要同时添加上模板sku和学校下的goods和sku
	 * 
	 * @param activityGoodsApplyBean
	 *                申请添加的对象
	 * @param activityId
	 *                活动id
	 * @param storageType
	 *                类型
	 * @param userId
	 *                用户id
	 * @param beginTime
	 *                开始时间
	 * @param endTime
	 *                结束时间
	 * @param showTime
	 *                显示时间
	 * @param activityType
	 *                活动类型 0：秒杀活动；1：普通活动
	 * @return -1说明活动没有设置范围，-2表示活动范围和商品范围的交集为0 -3表明该活动下已存在该商品
	 */
	public int insertBgGoodsForActivity(ActivityGoodsApplyBean activityGoodsApplyBean, long activityId,
			int storageType, long userId, Date beginTime, Date endTime, Date showTime, int activityType);
	
	public int insertThirdGoods2Activity(ActivityGoodsApplyBean activityGoodsApplyBean, long activityId,
			long userId, int activityType,int storageType,int goodsStatus);
	
	//临时加入批量导入‘麦闪电’活动的LDC商品
	public int batchBgGoodsForActivity(Long activityId,Long collegeId,Goods goods,Sku sku,Long userId);

	/**
	 * 更新活动后台商品模板
	 * 
	 * @param bgGoodsForActivity
	 * @return
	 */
	public int updateBgGoodsForActivity(BgGoodsForActivity bgGoodsForActivity);

	/**
	 * 根据活动id和后台商品id查询是否存在。
	 * 
	 * @param activityId
	 * @param bgGoodsId
	 * @return
	 */
	public BgGoodsForActivity queryByActivityIdAndBgGoodsId(long activityId, long bgGoodsId,int storageType);

	/**
	 * 批量更新商品的权重，限购数量
	 * 
	 * @param weight
	 *                权重
	 * @param maxNum
	 *                限售数量
	 * @param status
	 *                限售状态 1：待售，2：在售，3：售罄
	 * @param activityBgGoodsId
	 *                活动商品模板id
	 * @return
	 */
	public int updateBgGoodsForActivity(long weight, long maxNum, int status, long activityBgGoodsId);

	public int updateStatus4ActivityGoods(int status, long activityBgGoodsId);

	/**
	 * 批量更新商品的时间
	 * 
	 * @param beginTime
	 *                开始时间
	 * @param endTime
	 *                结束时间
	 * @param showTime
	 *                显示时间
	 * @param activityBgGoodsId
	 *                活动商品模板id
	 * @return
	 */
	public int updateBgGoodsForActivityTime(Date beginTime, Date endTime, Date showTime, long activityBgGoodsId);

	/**
	 * 更新商品售卖状态
	 * 
	 * @param isDel
	 *                0：可用 1：不可用
	 * @param activityBgGoodsId
	 *                活动后台商品id
	 * @return
	 */
	public int updateIsDelStatus(long activityBgGoodsId, int isDel);

	/**
	 * 
	 * @param beginDate
	 * @param endDate
	 *                beginDate-endDate时间段内有效的商品
	 * @param activityId
	 *                活动id
	 * @param goodsName
	 *                商品名称（支持模糊查询）
	 * @param status
	 *                状态 0：全部；1：待售；2：在售；3：售罄
	 * @param isSeckill
	 *                是否秒杀商品 0：是；1：否
	 * @param paginationInfo
	 *                分页信息
	 * @return
	 */
	public List<BgGoodsForActivity> getBgGoodsForActivityByQuery(Date beginDate, Date endDate, long activityId,
			String goodsName, int status, int isSeckill, PaginationInfo paginationInfo);

	/**
	 * 活动范围被修改了,新增学校，需要把活动下的商品复制到这个学校下。
	 * 首先获得活动下的商品goods，在根据获得sku。并获得sku的类型获得商品可售卖学校
	 * ，再检查新增学校是否在可售卖学校中。如果在，则把该商品添加到该活动下。
	 * 
	 * @param activityId
	 * @param collegeId
	 * @return
	 */
	public int addCollegeInActivity(long activityId, long[] collegeId);

	/**
	 * 活动范围被修改了,去除了学校，需要把活动下在该学校售卖的商品可用性置为不可用。
	 * 
	 * @param activityId
	 * @param collegeId
	 * @return
	 */
	public int deleteCollegeInActivity(long activityId, long[] collegeId);
	
	
	public List<BgGoodsForActivity> getBgGoodsForActivityByActivityId(long activityId);
	
	public List<BgGoodsForActivity> getBgGoodsForActivityByActivityIdAndIsDel(long activityId,int isDel);
	
	public BgGoodsForActivity queryBgGoodsForActivity(long activityGoodsId);
	
	public List<Long> containsAllActivityCollege(Long bgGoodsId,int storageType,Long activityId);
	
	public ActivityGoods queryActivityGoods(long bgGoodsForActivityId, long activityId, long collegeId);
	
	public List<BgGoodsForActivity> getThirdBgGoods4Activity(Map<String, Object> params,PaginationInfo paginationInfo);

}
