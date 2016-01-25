package com.mall.admin.service.activity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.activity.ActivityGoodsDao;
import com.mall.admin.service.activity.ActivityGoodsService;

@Service
public class ActivityGoodsServiceImpl implements ActivityGoodsService {

	@Autowired
	ActivityGoodsDao activityGoodsDao;

	@Override
	public int updateStatusByBgGoodsIdAndCollegeId(long bgGoodsId, long collegeId, int isDel, int distributeType) {
		// TODO Auto-generated method stub
		return activityGoodsDao
				.updateStatusByBgGoodsIdAndCollegeID(bgGoodsId, collegeId, isDel, distributeType);
	}

	@Override
	public int deletActivityGoodsByCollegeId(long collegeId, int distributeType) {
		// TODO Auto-generated method stub
		return activityGoodsDao.updateActivityGoodsIsDelInCollege(collegeId, distributeType, 1);
	}

}
