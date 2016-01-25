package com.mall.admin.model.mybatis.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.mall.admin.model.dao.activity.BgGoodsForActivityDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.activity.BgGoodsForActivity;

@Repository
public class BgGoodsForActivityDaoImpl extends BaseMallDaoImpl implements BgGoodsForActivityDao {

	@Override
	public BgGoodsForActivity getById(long activityBgGoodsId) {
		return this.getSqlSession().selectOne("BgGoodsForActivity.selectOneById", activityBgGoodsId);
	}

	@Override
	public List<BgGoodsForActivity> getListByActivityIdAndGondsName(Long[] activityIds, String goodsName) {
		Map map = new HashMap();
		if (activityIds != null && activityIds.length > 0) {
			map.put("activityIds", activityIds);
		}
		if (goodsName != null) {
			map.put("goodsName", goodsName);
		}
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("BgGoodsForActivity.selectBgGoodsForActivity", map);
	}

	@Override
	public int insert(BgGoodsForActivity bean) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("BgGoodsForActivity.insertBgGoodsForActivity", bean);
	}

	@Override
	public int update(BgGoodsForActivity bean) {
		return this.getSqlSession().update("BgGoodsForActivity.updateBgGoodsForActivity", bean);
	}

	@Override
	public BgGoodsForActivity queryByActivityIdAndBgGoodsId(long activityId, long bgGoodsId, int storageType) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("activityId", activityId);
		map.put("bgGoodsId", bgGoodsId);
		map.put("storageType", storageType);
		return this.getSqlSession().selectOne("BgGoodsForActivity.selectByActivityIdAndGoodsId", map);
	}

	@Override
	public int update(long weight, long maxNum, long status, long activityBgGoodsId) {
		Map map = new HashMap();
		map.put("activityBgGoodsId", activityBgGoodsId);
		map.put("weight", weight);
		map.put("maxNum", maxNum);
		map.put("status", status);
		return this.getSqlSession().update("BgGoodsForActivity.updateBaseDate", map);
	}

	
	@Override
	public int updateGoodsStatus(long goodsStatus, long activityBgGoodsId) {
		Map<String, Object> map = new HashMap<>();
		map.put("activityBgGoodsId", activityBgGoodsId);
		map.put("goodsStatus", goodsStatus);
		return this.getSqlSession().update("BgGoodsForActivity.updateGoodsStatus", map);
	}

	@Override
	public int updateTime(Date beginTime, Date endTime, Date showTime, long activityBgGoodsId) {
		Map map = new HashMap();
		map.put("activityBgGoodsId", activityBgGoodsId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("showTime", showTime);
		return this.getSqlSession().update("BgGoodsForActivity.updateTime", map);
	}

	@Override
	public int updateIsDelStatus(long activityBgGoodsId, int isDel) {
		Map map = new HashMap();
		map.put("activityBgGoodsId", activityBgGoodsId);
		map.put("isDel", isDel);
		return this.getSqlSession().update("BgGoodsForActivity.deleteBgGoodsForActivity", map);
	}

	@Override
	public List<BgGoodsForActivity> queryBeGoodsForActivityByQuery(Date beginDate, Date endDate, long activityId,
			String goodsName, int status, int isSeckill, PaginationInfo paginationInfo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("isSeckill", isSeckill);
		if (beginDate != null) {
			paramMap.put("beginDate", beginDate);
		}
		if (endDate != null) {
			paramMap.put("endDate", endDate);
		}
		if (activityId > 0) {
			paramMap.put("activityId", activityId);
		}
		if (status > 0) {
			paramMap.put("goodsStatus", status);
		}
		if (!Strings.isEmpty(goodsName)) {
			paramMap.put("goodsName", goodsName);
		}
		PaginationList<BgGoodsForActivity> bgGoodsForActivityList = selectPaginationList(
				"BgGoodsForActivity.selectBgGoodsByQueryByPage", paramMap, paginationInfo);
		return bgGoodsForActivityList;
	}
	
	
	

	@Override
	public List<BgGoodsForActivity> getBeanListByActivityId(long activityId) {

		return this.getSqlSession().selectList("BgGoodsForActivity.selectListByActivityId", activityId);
	}
	
	@Override
	public List<BgGoodsForActivity> getBeanListByActivityId(long activityId,int isDel) {
		Map<String,Object> paraMap = Maps.newHashMap();
		paraMap.put("activityId", activityId);
		paraMap.put("isDel", isDel);
		return this.getSqlSession().selectList("BgGoodsForActivity.selectListByActivityIdAndIsDel", paraMap);
	}
	

	@Override
	public List<BgGoodsForActivity> getBeanListByBgGoodsId(long bgGoodsId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public BgGoodsForActivity queryBgGoodsForActivity(long activityGoodsId) {
		return this.getSqlSession().selectOne("BgGoodsForActivity.selectByActivityGoodsId", activityGoodsId);
	}

	@Override
	public List<BgGoodsForActivity> getThirdBgGoods4Activity(
			Map<String, Object> params, PaginationInfo paginationInfo) {
		return this.selectPaginationList("BgGoodsForActivity.selectThirdBgGoodsByPage", params, paginationInfo);
	}

}
