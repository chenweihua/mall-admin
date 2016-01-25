/**
 * 
 */
package com.mall.admin.service.ump.coupon.impl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.mall.admin.base._;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.constant.SpringConstant;
import com.mall.admin.enumdata.CouponStatus;
import com.mall.admin.model.dao.ump.UMPCouponBatchDao;
import com.mall.admin.model.dao.ump.UMPCouponDetailDao;
import com.mall.admin.model.dao.ump.UMPCouponGiveDao;
import com.mall.admin.model.dao.ump.UMPCouponGiveLogDao;
import com.mall.admin.model.dao.ump.UMPCouponRelationDao;
import com.mall.admin.model.dao.ump.UMPCouponRestrictDao;
import com.mall.admin.model.dao.ump.base.GroupSequence;
import com.mall.admin.model.dao.ump.base.SequenceException;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.ump.coupon.CouponService;
import com.mall.admin.service.ump.uc.UcUserService;
import com.mall.admin.util.CommonUtil;
import com.mall.admin.util.DateUtil;
import com.mall.admin.util.DictionaryUtil;
import com.mall.admin.util.FileUtils;
import com.mall.admin.util.JsonUtil;
import com.mall.admin.util.MoneyUtils;
import com.mall.admin.util.SMSUtil;
import com.mall.admin.vo.ump.Coupon;
import com.mall.admin.vo.ump.CouponBatch;
import com.mall.admin.vo.ump.CouponBatchRelation;
import com.mall.admin.vo.ump.CouponDetail;
import com.mall.admin.vo.ump.CouponGive;
import com.mall.admin.vo.ump.CouponGiveLog;
import com.mall.admin.vo.ump.CouponRewardsInfo;
import com.mall.admin.vo.ump.MallIni;
import com.mall.admin.vo.ump.UcUser;

/**
 * @author liqiang
 *
 */
@Service
public class CouponServiceImpl implements CouponService {
	
	private static final Logger logger = LogConstant.mallLog;

	@Autowired
	UMPCouponBatchDao uMPCouponBatchDao;
	
	@Autowired
	UMPCouponBatchDao couponBatchDao;
	
	@Autowired
	UMPCouponDetailDao couponDetailDao;
	
	
	@Autowired
	UMPCouponGiveDao couponGiveDao;
	
	@Autowired
	UMPCouponGiveLogDao couponGiveLogDao;
	
	@Autowired
	UMPCouponRestrictDao couponRestrictDao;
	
	
	/* (non-Javadoc)
	 * @see com.mall.admin.service.ump.coupon.CouponService#getCouponList(java.util.Date, java.util.Date, java.lang.String, int, int, int, int, int)
	 */
//	
	@Autowired
	UMPCouponDetailDao uMPCouponDetailDao;
	
	@Autowired
	UMPCouponRelationDao uMPCouponRelationDao;
	
	@Autowired
	UcUserService ucUserService;
	
	GroupSequence groupSequence = new GroupSequence();;


	@Override
	public Pair<Integer, List<CouponBatch>> getCouponList(String startDate,
			String endDate, Long batchId, String batchName, String money, int platformType,
			int deliverType, int status, int start, int numPerPage) {

		Map<String,Object> map =CommonUtil.asMapValueNull("batchId", batchId,
				 "platformType", platformType, "deliverType", deliverType,
				"status", status,"numPerPage",numPerPage);
		if(startDate!=null&&endDate!=null){
			Date startTime = DateUtil.formatDateToDate(startDate);
		    Date endTime =  DateUtil.formatDateToDate(endDate);
		    map.put("startTime", startTime);
		    map.put("endTime", endTime);
		}
		if(!_.isEmpty(batchName)) {
			map.put("batchName", "%"+batchName+"%");
		}
		if(start>-1){
			map.put("start", start);
		}
		List<CouponBatch> result;
		Integer count =0;
		if(!_.isEmpty(money)){
			map.put("money", MoneyUtils.yuan2Fen(Long.parseLong(money)));
		}
		//TODO 此方法中根据money有无做了关联查询，使用in操作，后期可能是坑，再修改
		result = uMPCouponBatchDao.getCouponBatchList(map);
		if(CollectionUtils.isNotEmpty(result)){
			count =uMPCouponBatchDao.getCountCouponBatchList(map);
		}
		return Pair.of(count, result);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void addCouponGive(CouponGive couponGive) throws Exception {
		
		//更改批次状态,由新建变为锁定状态
		boolean updateSuccess = lockCouponBatchByActiveSend(couponGive.getCouponBatchId());
		if(!updateSuccess) {
			throw new Exception("该批次不允许发送");
		}
		
		int affectNumCouponGive = couponGiveDao.insert(couponGive);
		
		if(affectNumCouponGive == 1) {
			CouponBatch couponBatch = new CouponBatch();
			couponBatch.setBatchId(couponGive.getCouponBatchId());
			couponBatch.setDeliverType(couponGive.getGiveWay());
			int affectNumCouponBatch = couponBatchDao.updateByPrimaryKey(couponBatch);
			if(affectNumCouponBatch != 1) {
				throw new Exception("更新coupon_batch记录失败,batch_id:" + couponGive.getCouponBatchId());
			}
		}
		
		
	}
	
	
	@Override
	public CouponBatch queryCouponBatch(Long couponBatchId) {
		
		return couponBatchDao.selectByPrimaryKey(couponBatchId);
		
	}
	
	@Override
	public CouponGive queryGouponGive(Long couponGiveId) {
		
		return couponGiveDao.selectByPrimaryKey(couponGiveId);
	}
	
	
	
	@Override
	public int sendCoupon(Long couponBatchId, Long userId, Long couponGiveId) throws Exception {
		
		//更新
		//int affectNum = couponDetailDao.updateAUserIdWhenNull(couponBatchId, userId);
		
		//插入coupon_detail记录
		//List<Long> sendedCouponMoney = new ArrayList<Long>();
		
		CouponBatch couponBatch = couponBatchDao.selectByPrimaryKey(couponBatchId);
		//List<CouponBatchRelation> batchRelations = uMPCouponRelationDao.queryCouponBatchRelations(couponBatchId);
	
		
		CouponDetail couponDetail = new CouponDetail();
		couponDetail.setCouponDetailId(groupSequence.nextValue());
		couponDetail.setCouponType(1);
		couponDetail.setCouponName("优惠券");
		couponDetail.setMoney(couponBatch.getMoney().intValue());
		couponDetail.setSource(CouponDetail.SOURCE_ACTIVE_SEND);
		couponDetail.setBusinessId(userId);
		couponDetail.setRealName(null);
		couponDetail.setReleaseBusinessId(-1L);
		couponDetail.setStatus(1);
		couponDetail.setCreateTime(new Date());
		couponDetail.setUpdateTime(new Date());
		couponDetail.setStartTime(couponBatch.getStartTime());
		couponDetail.setEndTime(couponBatch.getEndTime());
		couponDetail.setBatchId(couponBatchId);
		couponDetail.setFeature(couponBatch.getFeature());
		int affectRowCouponDetail = couponDetailDao.insert(couponDetail);
		
//		int affectRowCouponBatch = couponBatchDao.minusOneCouponBatch(couponBatchId);
		
		if(affectRowCouponDetail == 0) {
			logger.error("couponBatchId:" + couponBatchId + ",userId:" + userId + ",发送优惠券失败affectRowCouponDetail" + affectRowCouponDetail);
			throw new Exception("发送优惠券时发生异常");
		} else {
			CouponGiveLog log = new CouponGiveLog();
			log.setCouponGiveId(couponGiveId);
			log.setUserId(userId);
			log.setMoney(couponBatch.getMoney().intValue());
			log.setStatus(CouponGiveLog.STATUS_SENDED);
			couponGiveLogDao.updateByCouponGiveIdAndUserId(log);
		}
		
		return couponDetail.getMoney();
		
	}
	
	
	public boolean updateCouponBatchStatus(Long couponBatchId,int originStatus,int changeStatus) throws Exception {
		CouponBatch couponBatch = new CouponBatch();
		couponBatch.setBatchId(couponBatchId);
		couponBatch.setOriginStatus(originStatus);
		couponBatch.setStatus(changeStatus);
		int affectNum = couponBatchDao.updateCouponBatchStatus(couponBatch);
		return affectNum > 0;		
	}
	
	/**
	 * 后台主动发送 锁住指定的coupon_batch
	 */
	public boolean lockCouponBatchByActiveSend(Long couponBatchId) {
		int affectNum = couponBatchDao.lockCouponBatchByActiveSend(couponBatchId);
		return affectNum == 1;
	}
	
	public void insertCoupon(CouponBatch couponBatch) throws SequenceException {
		
		uMPCouponBatchDao.insert(couponBatch);

//		groupSequence = new GroupSequence();
//		for(Map<String,Object> map:list){
//			CouponBatchRelation couponBatchRelation = new CouponBatchRelation();
//			couponBatchRelation.setBatchId(couponBatch.getBatchId());
//			couponBatchRelation.setCouponRelationId(groupSequence.nextValue());
//			couponBatchRelation.setFeatureMap(couponBatch.getFeatureMap());
//			couponBatchRelation.setMoney(MoneyUtils.yuan2Fen(Long.parseLong(map.get("money").toString())));
//			couponBatchRelation.setTotalNumber(Integer.parseInt(map.get("totalNumber").toString()));
//			uMPCouponRelationDao.insert(couponBatchRelation);
//		}
	}
	
	@Override
	public void updateCoupon(CouponBatch couponBatch) {
		uMPCouponBatchDao.updateByPrimaryKey(couponBatch);
		
	}
	
	
	/**
	 * 查询指定批次下所有每种面额的数量
	 */
	public Map<Long,Integer> queryMoneyNum(Long couponBatchId) {
		Map<Long,Integer> retMap = new HashMap<Long,Integer>();
		List<CouponBatchRelation> couponBatchRelations = uMPCouponRelationDao.queryCouponBatchRelations(couponBatchId);
		for(CouponBatchRelation relation : couponBatchRelations) {
			retMap.put(relation.getMoney(), relation.getTotalNumber());
		}
		return retMap;
	}
	
	
	public List<CouponGive> getCouponGiveList(Map<String,String> param,PaginationInfo paginationInfo) {
		List<CouponGive> couponGiveList = couponGiveDao.getList(param, paginationInfo);
		return couponGiveList;
	}
	
	
	/**
	 * 分页查询couponGiveLog
	 * @param paginationInfo
	 * @return
	 */
	public List<CouponGiveLog> getCouponGiveLogList(Map<String,String> param,PaginationInfo paginationInfo) {
		List<CouponGiveLog> couponGiveLogList = couponGiveLogDao.getList(param, paginationInfo);
		return couponGiveLogList;
	}
	
	
	
	/**
	 * 将指定的couponGive记录状态从orginStatus变更为status
	 */
	public boolean updateCouponGiveStatus(Long couponGiveId,String orginStatus,String status,String msg) {
		CouponGive orginCouponGive = couponGiveDao.selectByPrimaryKey(couponGiveId);
		CouponGive couponGive = new CouponGive();
		couponGive.setId(couponGiveId);
		couponGive.setOrginStatus(orginStatus);
		couponGive.setStatus(status);
		couponGive.setMsg(StringUtils.defaultString(orginCouponGive.getMsg()) + StringUtils.defaultString(msg));
		int affectNum = couponGiveDao.updateStatusAndMsg(couponGive);
		return affectNum > 0;
	}
	

	/* (non-Javadoc)
	 * @see com.mall.admin.service.ump.coupon.CouponService#endCouponBatch(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public int endCouponBatch(Long couponBatchId, Integer status) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("couponBatchId", couponBatchId);
		map.put("status", status);
		return uMPCouponBatchDao.endCouponBatch(map);
		
	}
	
	
	/**
	 * 清除所有coupon_batch中有activityId为指定值的标记
	 * @param activityId
	 */
	public void clearCouponBatchActivityId(Long activityId) {
		uMPCouponBatchDao.clearCouponBatchActivityId(activityId);
	}
	
	/**
	 * 作废一条couponGive记录，同时更新对应的couponBatch状态
	 * @param couponGiveId
	 */
	@Transactional(rollbackFor=Exception.class)
	public void delCouponGive(Long couponGiveId) throws Exception {
		//boolean delCouponGiveSuccess = updateCouponGiveStatus(couponGiveId, CouponGive.SAVED, CouponGive.DEL, null);
		int affectNum = couponGiveDao.deleteByPrimaryKey(couponGiveId);
		if(affectNum == 1) {
			CouponGive couponGive = queryGouponGive(couponGiveId);
			boolean updateSuccess = updateCouponBatchStatus(couponGive.getCouponBatchId(),CouponBatch.STATUS_LOCKED,CouponBatch.STATUS_NEW);
			if(!updateSuccess) {
				throw new Exception("更新couponBatch" + couponGive.getCouponBatchId() + "状态解除锁定操作失败");
			}
		} else {
			throw new Exception("更新couponGive" + couponGiveId + "状态为删除操作失败");
		}
	}
	
	
	/**
	 * 主动批量发放优惠券
	 * @param couponGive
	 * @param phoneNoUserIdList
	 */
	@Transactional(rollbackFor=Exception.class)
	public void sendCouponBatch(CouponGive couponGive,List<Pair<Long,Long>> phoneNoUserIdList) throws Exception {
		
		//先把需要发送的用户信息放到log中
		/*for(Pair<Long,Long> phoneNoAndUserId : phoneNoUserIdList) {
			//try {
				CouponGiveLog log = new CouponGiveLog();
				log.setId(groupSequence.nextValue());
				log.setCouponBatchId(couponGive.getCouponBatchId());
				log.setCouponGiveId(couponGive.getId());
				log.setCreateTime(new Date());
				log.setStatus(CouponGiveLog.STATUS_NO_SEND);
				log.setPhoneNo(phoneNoAndUserId.getLeft() + "");
				log.setUserId(phoneNoAndUserId.getRight());
				couponGiveLogDao.insert(log);
			
			} catch(Exception ex) {
				logger.error("主动发送优惠券前先插入待发记录时发生异常,couponGiveId:" + couponGive.getId() + ",phoneNo:" + phoneNoAndUserId.getLeft()
						+ ",userId:" + phoneNoAndUserId.getRight(), ex);
				continue;
			}
			
		}*/
		
		Long couponBatchId = couponGive.getCouponBatchId();
		CouponBatch couponBatch = couponBatchDao.selectByPrimaryKey(couponBatchId);
		
		for(Pair<Long,Long> phoneNoAndUserId : phoneNoUserIdList) {
			
//			sendSingleCoupon(couponGive.getCouponBatchId(),phoneNoAndUserId.getRight(),phoneNoAndUserId.getLeft(),couponGive.getId());
			
			//更新
			//int affectNum = couponDetailDao.updateAUserIdWhenNull(couponBatchId, userId);
			
			//插入coupon_detail记录
			//List<Long> sendedCouponMoney = new ArrayList<Long>();
			
			
			//List<CouponBatchRelation> batchRelations = uMPCouponRelationDao.queryCouponBatchRelations(couponBatchId);
		
			CouponDetail couponDetail = new CouponDetail();
			couponDetail.setCouponDetailId(groupSequence.nextValue());
			couponDetail.setCouponType(1);
			couponDetail.setCouponName("优惠券");
			couponDetail.setMoney(couponBatch.getMoney().intValue());
			couponDetail.setSource(CouponDetail.SOURCE_ACTIVE_SEND);
			couponDetail.setBusinessId(phoneNoAndUserId.getRight());//用户ID
			couponDetail.setRealName(null);
			couponDetail.setReleaseBusinessId(-1L);
			couponDetail.setStatus(1);
			couponDetail.setCreateTime(new Date());
			couponDetail.setUpdateTime(new Date());
			couponDetail.setStartTime(couponBatch.getStartTime());
			couponDetail.setEndTime(couponBatch.getEndTime());
			couponDetail.setBatchId(couponBatchId);
			couponDetail.setFeature(couponBatch.getFeature());
			int affectRowCouponDetail = couponDetailDao.insert(couponDetail);
			if(affectRowCouponDetail == 0 ) {
				logger.error("couponBatchId:" + couponBatchId + ",userId:" + couponDetail.getBusinessId() + ",发送优惠券失败affectRowCouponDetail" + affectRowCouponDetail);
				throw new Exception("插入优惠券明细失败");
			} else {
				//插入一条log
				CouponGiveLog log = new CouponGiveLog();
				log.setId(groupSequence.nextValue());
				log.setCouponBatchId(couponGive.getCouponBatchId());
				log.setCouponGiveId(couponGive.getId());
				log.setCreateTime(new Date());
				log.setUpdateTime(new Date());
				log.setPhoneNo(phoneNoAndUserId.getLeft() + "");
				log.setUserId(phoneNoAndUserId.getRight());
				log.setMoney(couponBatch.getMoney().intValue());
				log.setStatus(CouponGiveLog.STATUS_SENDED);
				couponGiveLogDao.insert(log);
			}
		}
	  	CouponBatch cou = new CouponBatch();
		if(couponBatch.getTotalNumber()>phoneNoUserIdList.size()){
			cou.setStatus(CouponStatus.STATUS_5.getKey());
		}else{
			cou.setStatus(CouponStatus.STATUS_3.getKey());
		}
		cou.setUsedNumber(phoneNoUserIdList.size());
		cou.setBatchId(couponBatchId);
		cou.setUsedMoney(couponBatch.getMoney()*phoneNoUserIdList.size());
		int affectRowCouponBatch = couponBatchDao.minusOneCouponBatch(cou);
		if(affectRowCouponBatch == 0 ) {
			logger.error("couponBatchId:" + couponBatchId + ",发送优惠券失败affectRowCouponBatch" + affectRowCouponBatch);
			throw new Exception("更新优惠券batch表失败");
		}
	}
	
	public void sendMsgBatchAfterSending(Long couponGiveId) {
		
		List<CouponGiveLog> couponGiveLogs = couponGiveLogDao.querySendedCouponGiveLogByCouponGiveId(couponGiveId);
		if(couponGiveLogs != null && !couponGiveLogs.isEmpty()) {
			for(CouponGiveLog log : couponGiveLogs) {
				sendMsgAfterSendCoupon(log.getPhoneNo(),log.getMoney());
			}
			
		}
		
	}
	
	
	/**
	 * 主动发给客户优惠券后，给客户发送短信通知
	 */
	private void sendMsgAfterSendCoupon(String phoneNo,int money) {
		
		String msgTemplate = DictionaryUtil.getValueByTypeIdAndKey(2L, "ACTIVE_SEND_COUPON_MSG");
		String msg = msgTemplate.replace("{money}", (money / 100) + "");
		SMSUtil.sendSMSText(phoneNo, msg);
	}
	
	
	/**
	 * 发送一张优惠券 
	 */
	private void sendSingleCoupon(Long couponBatchId,Long userId,Long phoneNo, Long couponGiveId) throws Exception {
		
		sendCoupon(couponBatchId, userId, couponGiveId);
		
		/*
		try {
			int money = sendCoupon(couponBatchId, userId, couponGiveId);
			//sendMsgAfterSendCoupon(phoneNo + "", money);
		} catch(Exception ex) {
			logger.error("发送优惠券couponBatchId:" + couponBatchId + ",userId:" + userId + "发生异常",ex);
		}
		*/
	}
	
	
	public void addCouponGiveLogFromFile(InputStream in, Long couponBatchId, Long couponGiveId) {
		
		final int NUM_PER_SQL = 1000;
		Connection conn = null;
		long startTime = System.currentTimeMillis();
		try {
			List<String> phoneNos = FileUtils.readCsvForSingleLine(in);
			int size = phoneNos.size();
			int num = 1;
			StringBuffer sb = null;
			DataSource dataSource = (DataSource)SpringConstant.getBean("dataSource");
			conn = dataSource.getConnection();
			Statement statement = conn.createStatement();
			for(String phoneNo : phoneNos) {
				if(num % NUM_PER_SQL == 1) {
					sb = new StringBuffer("insert into tb_coupon_give_log(id,coupon_give_id,coupon_batch_id,user_id,phoneno,money,create_time,status,update_time) values");
				}
				sb.append("(" + new GroupSequence().nextValue() + "," + couponGiveId + "," + couponBatchId + ",null,'" + phoneNo + "',0,now(),'0',null),");
	    		if(num % NUM_PER_SQL == 0 || num == size) {
	    			sb.deleteCharAt(sb.length() - 1);
	    			System.out.println(sb.toString());
	    			statement.execute(sb.toString());
	    		}
	    		num++;
			}
			System.out.println(System.currentTimeMillis() - startTime);
		} catch(Exception ex) {
			logger.error("addCouponGiveLogFromFile发生异常,couponGiveId:" + couponGiveId);
		} finally {
			try {
				conn.close();
			} catch(Exception ex) {
				logger.error("",ex);
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.mall.admin.service.ump.coupon.CouponService#getUserAllByPhone(java.lang.Integer)
	 */
	@Override
	public List<Coupon> getUserAllByPhone(Integer userId) {
		return uMPCouponBatchDao.getUserAllByPhone(userId);
		
	}
	
	@Override
	public List<Coupon> getCouponRewardsAllByPhone(Integer userId) {
		return uMPCouponBatchDao.getCouponRewardsAllByPhone(userId);
		
	}

	
	@Override
	public Pair<Integer, List<CouponRewardsInfo>> getPushRewardList(Integer userId,String phone,
			Integer source,String startDate, String endDate, int start, int numPerPage) throws Exception {
		Map<String,Object> map = Maps.newHashMap();
		if(StringUtils.isNotBlank(startDate)&&StringUtils.isNotBlank(endDate)){
			Date startTime = DateUtil.formatDateToDate(startDate);
		    Date endTime =  DateUtil.formatDateToDate(endDate);
		    map.put("startTime", startTime);
		    map.put("endTime", endTime);
		}
		if(start>-1){
			map.put("start", start);
		}
		map.put("numPerPage", numPerPage);
		if(userId!=0){
			map.put("userId", userId);
		}
		if(source!=0){
			map.put("source", source);
		}
		List<CouponRewardsInfo> result;
		Integer count =0;
		result = couponDetailDao.getCouponRewardsInfo(map);
		if(CollectionUtils.isNotEmpty(result)){
			count = couponDetailDao.getCouponRewardsInfoCount(map);
			List<Long> userIdList = new ArrayList<Long>();
			Set<Long> releaseUserIdSet = new HashSet<Long>();
			for (CouponRewardsInfo rewardsResult : result) {
				long businessUserId = rewardsResult.getBusinessId();
				userIdList.add(businessUserId);
				releaseUserIdSet.add(rewardsResult.getReleaseBusinessId());
			}
			//根据用户ID集合查询手机号和用户名
			List<UcUser> ucUserList = ucUserService.getUcUsersByUserIds(userIdList);
			
			List<UcUser> releaseUcUserList =new ArrayList<UcUser>();
			if(userId==0){
				List<Long> list = new ArrayList<Long>(releaseUserIdSet);
				releaseUcUserList = ucUserService.getUcUsersByUserIds(list);
			}
			for (CouponRewardsInfo couponRewardsInfo : result) {
				int businessId = couponRewardsInfo.getBusinessId().intValue();
				int releaseBusinessId = couponRewardsInfo.getReleaseBusinessId().intValue();
				int status = couponRewardsInfo.getStatus();
				if(status == 4) {//如果订单状态是锁定状态，传给前端未使用状态
					couponRewardsInfo.setStatus(1);
				}
				for(UcUser ucUser : ucUserList) {
					if(businessId == ucUser.getId()) {
						couponRewardsInfo.setBussinessMobile(ucUser.getMobilephone());
						couponRewardsInfo.setHeadUrl(ucUser.getHeadimgurl());
						break;
					}
				}
				if(userId!=0){
					couponRewardsInfo.setReleaseBussinessMobile(phone);
				}else{
					for(UcUser ucUser : releaseUcUserList) {
						if(releaseBusinessId == ucUser.getId()) {
							couponRewardsInfo.setReleaseBussinessMobile(ucUser.getMobilephone());
							couponRewardsInfo.setHeadUrl(ucUser.getHeadimgurl());
							break;
						}
					}
				}
				}
			}
		return Pair.of(count, result);
	}
	
	@Override
	public List<CouponRewardsInfo> getPushRewardExportList(Integer userId,String phone,
			Integer source,Date startDate, Date endDate) throws Exception {
		Map<String,Object> map = Maps.newHashMap();
	    map.put("startTime", startDate);
	    map.put("endTime", endDate);
		if(userId!=0){
			map.put("userId", userId);
		}
		if(source!=0){
			map.put("source", source);
		}
		List<CouponRewardsInfo> result;
		result = couponDetailDao.getCouponRewardsInfo(map);
		if(CollectionUtils.isNotEmpty(result)){
			List<Long> userIdList = new ArrayList<Long>();
			Set<Long> releaseUserIdSet = new HashSet<Long>();
			for (CouponRewardsInfo rewardsResult : result) {
				long businessUserId = rewardsResult.getBusinessId();
				userIdList.add(businessUserId);
				releaseUserIdSet.add(rewardsResult.getReleaseBusinessId());
			}
			int limit = 1000;
		     int offset = 0;
		     int limit1 = 1000;
		     int offset1 = 0;
		   //根据用户ID集合查询手机号和用户名   分多次查询 一次查询1000条数据
		     List<UcUser> ucUserList = new ArrayList<UcUser>();
	        while (offset < userIdList.size()) {
	            int toIndex = offset + limit;
	            List<Long> uids = userIdList.subList(offset, 
	                    toIndex > userIdList.size() ? userIdList.size() : toIndex);
	            List<UcUser> ucList = ucUserService.getUcUsersByUserIds(uids);
	            ucUserList.addAll(ucList);
	            offset += limit;
	        }
			List<UcUser> releaseUcUserList =new ArrayList<UcUser>();
			if(userId==0){
				List<Long> list = new ArrayList<Long>(releaseUserIdSet);
				while (offset1 < list.size()) {
		            int toIndex = offset1 + limit1;
		            List<Long> uids = list.subList(offset1, 
		                    toIndex > list.size() ? list.size() : toIndex);
		            List<UcUser> ucList = ucUserService.getUcUsersByUserIds(uids);
		            releaseUcUserList.addAll(ucList);
		            offset1 += limit1;
		        }
			}
			
			for (CouponRewardsInfo couponRewardsInfo : result) {
				int businessId = couponRewardsInfo.getBusinessId().intValue();
				int releaseBusinessId = couponRewardsInfo.getReleaseBusinessId().intValue();
				int status = couponRewardsInfo.getStatus();
				if(status == 4) {//如果订单状态是锁定状态，传给前端未使用状态
					couponRewardsInfo.setStatus(1);
				}
				for(UcUser ucUser : ucUserList) {
					if(businessId == ucUser.getId()) {
						couponRewardsInfo.setBussinessMobile(ucUser.getMobilephone());
						couponRewardsInfo.setHeadUrl(ucUser.getHeadimgurl());
						break;
					}
				}
				if(userId!=0){
					couponRewardsInfo.setReleaseBussinessMobile(phone);
				}else{
					for(UcUser ucUser : releaseUcUserList) {
						if(releaseBusinessId == ucUser.getId()) {
							couponRewardsInfo.setReleaseBussinessMobile(ucUser.getMobilephone());
							couponRewardsInfo.setHeadUrl(ucUser.getHeadimgurl());
							break;
						}
					}
				}
				}
			}
		return result;
	}

	@Override
	public Pair<Integer, List<MallIni>> getSwitchFromMallIni(String value, int start, int numPerPage) {
		Map<String,Object> map = Maps.newHashMap();
		if(start>-1){
			map.put("start", start);
		}
		map.put("numPerPage",numPerPage);
		map.put("value",value);
		List<MallIni> result= new ArrayList<MallIni>();
		Integer count=0;
		result = couponDetailDao.getSwitchFromMallIni(map);
		if(CollectionUtils.isNotEmpty(result)){
			count =couponDetailDao.getSwitchFromMallIniCount(map);
		}
		return Pair.of(count, result);
	}

	@Override
	public Integer updateSwitchFromMallIni(String name, String value) {
		return couponDetailDao.updateSwitchFromMallIni(name,value);
	}
	

}
