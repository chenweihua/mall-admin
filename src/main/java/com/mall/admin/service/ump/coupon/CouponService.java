package com.mall.admin.service.ump.coupon;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.dao.ump.base.SequenceException;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.ump.Coupon;
import com.mall.admin.vo.ump.CouponBatch;
import com.mall.admin.vo.ump.CouponGive;
import com.mall.admin.vo.ump.CouponGiveLog;
import com.mall.admin.vo.ump.CouponRewardsInfo;
import com.mall.admin.vo.ump.MallIni;


public interface CouponService {

	/**
	 * @param startTime
	 * @param endTime
	 * @param batchId 
	 * @param name
	 * @param money 
	 * @param type
	 * @param platformType
	 * @param status
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	Pair<Integer, List<CouponBatch>> getCouponList(String startTime, String endTime,
			Long batchId, String batchName, String money, int platformType,int deliverType, int status, int start,
			int numPerPage);
	
	
	
	void addCouponGive(CouponGive couponGive) throws Exception ;
	
	CouponBatch queryCouponBatch(Long couponBatchId);



	/**
	 * @param couponBatch
	 * @throws SequenceException 
	 */
	void insertCoupon(CouponBatch couponBatch) throws SequenceException;

	/**
	 * @param couponBatch
	 */
	void updateCoupon(CouponBatch couponBatch);
	
	CouponGive queryGouponGive(Long couponGiveId);
	
	int sendCoupon(Long couponBatchId,Long userId, Long couponGiveId) throws Exception;
	
	public boolean updateCouponBatchStatus(Long couponBatchId,int originStatus,int changeStatus) throws Exception ;
	
	public boolean lockCouponBatchByActiveSend(Long couponBatchId);



	/**
	 * @param couponBatchId
	 */
	public int endCouponBatch(Long couponBatchId,Integer status);
	
	/**
	 * 查询指定批次下所有每种面额的数量
	 */
	Map<Long,Integer> queryMoneyNum(Long couponBatchId);
	
	/**
	 * 分页查询couponGive
	 * @param paginationInfo
	 * @return
	 */
	public List<CouponGive> getCouponGiveList(Map<String,String> param,PaginationInfo paginationInfo);
	
	
	/**
	 * 分页查询couponGiveLog
	 * @param paginationInfo
	 * @return
	 */
	public List<CouponGiveLog> getCouponGiveLogList(Map<String,String> param,PaginationInfo paginationInfo);
	
	
	/**
	 * 将指定的couponGive记录状态从orginStatus变更为status
	 */
	public boolean updateCouponGiveStatus(Long couponGiveId,String orginStatus,String status,String msg);
	
	
	/**
	 * 清除所有coupon_batch中有activityId为指定值的标记
	 * @param activityId
	 */
	public void clearCouponBatchActivityId(Long activityId);
	
	/**
	 * 作废一条couponGive记录，同时更新对应的couponBatch状态
	 * @param couponGiveId
	 */
	public void delCouponGive(Long couponGiveId) throws Exception;
	
	/**
	 * 主动批量发送优惠券
	 * @param couponGive
	 * @param phoneNoUserIdList
	 */
	public void sendCouponBatch(CouponGive couponGive,List<Pair<Long,Long>> phoneNoUserIdList) throws Exception;
	
	/**
	 * 主动发送优惠券后再批量发送短信通知
	 * @param couponGiveId
	 */
	public void sendMsgBatchAfterSending(Long couponGiveId);
	
	
	/**
	 * 从文件流中读取手机号码放入couponGiveLog中
	 */
	public void addCouponGiveLogFromFile(InputStream in, Long couponBatchId, Long couponGiveId);



	/**
	 * @param id
	 */
	public List<Coupon> getUserAllByPhone(Integer id);
	
	public List<Coupon> getCouponRewardsAllByPhone(Integer id);



	/**
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	Pair<Integer, List<CouponRewardsInfo>> getPushRewardList(Integer userId,String phone,
			Integer source,String startDate, String endDate, int start, int numPerPage)throws Exception;
	
	List<CouponRewardsInfo> getPushRewardExportList(Integer userId,String phone,
			Integer source,Date startDate, Date endDate)throws Exception;



	/**
	 * @param value
	 * @return
	 */
	Pair<Integer, List<MallIni>> getSwitchFromMallIni(String value,int start, int numPerPage);



	/**
	 * @param value
	 * @param value2
	 * @return
	 */
	Integer updateSwitchFromMallIni(String name, String value);
}
