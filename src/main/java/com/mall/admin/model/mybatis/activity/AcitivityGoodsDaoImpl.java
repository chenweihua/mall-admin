package com.mall.admin.model.mybatis.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.mall.admin.model.dao.activity.ActivityGoodsDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.activity.ActivityGoods;

@Repository
public class AcitivityGoodsDaoImpl extends BaseMallDaoImpl implements ActivityGoodsDao {

	@Override
	public int insert(ActivityGoods bean) {

		return this.getSqlSession().insert("ActivityGoods.insertActivityGoods", bean);
	}

	@Override
	public ActivityGoods getByUnionKey(Long activityBgGoodsId, Long collegeId) {
		Map map = new HashMap();
		map.put("activityBgGoodsId", activityBgGoodsId);
		map.put("collegeId", collegeId);
		return this.getSqlSession().selectOne("ActivityGoods.selectActivityGoods", map);
	}

	@Override
	public int update(ActivityGoods bean) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("ActivityGoods.updateActivityGoods", bean);
	}

	@Override
	public int update(long weight, long maxNum, long status, long activityBgGoodsId) {
		Map map = new HashMap();
		map.put("activityBgGoodsId", activityBgGoodsId);
		map.put("weight", weight);
		map.put("maxNum", maxNum);
		map.put("status", status);
		return this.getSqlSession().update("ActivityGoods.updateBaseDate", map);
	}

	@Override
	public int updateGoodsStatus(Integer goodsStatus, long activityBgGoodsId) {
		Map<String, Object> map = new HashMap<>();
		map.put("activityBgGoodsId", activityBgGoodsId);
		map.put("goodsStatus", goodsStatus);
		return this.getSqlSession().update("ActivityGoods.updateGoodsStatus", map);
	}

	@Override
	public int updateTime(Date beginTime, Date endTime, Date showTime, long activityBgGoodsId) {
		Map map = new HashMap();
		map.put("activityBgGoodsId", activityBgGoodsId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("showTime", showTime);
		return this.getSqlSession().update("ActivityGoods.updateTime", map);
	}

	@Override
	public int updateIsDelStatus(long activityBgGoodsId, int isDel) {
		Map map = new HashMap();
		map.put("activityBgGoodsId", activityBgGoodsId);
		map.put("isDel", isDel);
		return this.getSqlSession().update("ActivityGoods.deleteActivityGoods", map);
	}

	@Override
	public List<ActivityGoods> getActivitGoodsList(long activityBgGoodsId) {
		return this.getSqlSession().selectList("ActivityGoods.selectActivityGoodsList", activityBgGoodsId);
	}

	@Override
	public int updateIsDelStatusInCollege(long activityBgGoodsId, int isDel, long collegeId) {
		Map map = new HashMap();
		map.put("activityBgGoodsId", activityBgGoodsId);
		map.put("isDel", isDel);
		map.put("collegeId", collegeId);
		return this.getSqlSession().update("ActivityGoods.updateActivityGoodsInCollege", map);
	}

	@Override
	public int updateStatusByBgGoodsIdAndCollegeID(long bgGoodsId, long collegeId, int isDel, int distributeType) {
		Map map = new HashMap();
		map.put("bgGoodsId", bgGoodsId);
		map.put("isDel", isDel);
		map.put("collegeId", collegeId);
		map.put("distributeType", distributeType);
		return this.getSqlSession().update("ActivityGoods.updateByBgGoodsIdAndCollegeId", map);
	}

	@Override
	public int updateActivityGoodsIsDelInCollege(long collegeId, int distributeType, int isDel) {
		Map map = new HashMap();
		map.put("isDel", isDel);
		map.put("collegeId", collegeId);
		map.put("distributeType", distributeType);
		return this.getSqlSession().update("ActivityGoods.updateActivityGoodsIsdelByCollegeId", map);
	}

	@Override
	public List<ActivityGoods> getActivityGoodsListByCID(long collegeId, long bgGoodsId) {
		Map map = new HashMap();
		map.put("collegeId", collegeId);
		if (bgGoodsId > 0) {
			map.put("bgGoodsId", bgGoodsId);
		}
		return this.getSqlSession().selectList("ActivityGoods.selectActivityGoodsByCID", map);
	}
	
	@Override
	public ActivityGoods queryActivityGoods(long bgGoodsForActivityId, long activityId, long collegeId) {
		Map<String,Object> paramMap = Maps.newHashMap();
		paramMap.put("activityBgGoodsId", bgGoodsForActivityId);
		paramMap.put("activityId", activityId);
		paramMap.put("collegeId", collegeId);
		return this.getSqlSession().selectOne("ActivityGoods.queryActivityGoodsBybgIdAndActivityIdAndCollegeId", paramMap);
	}
}
