package com.mall.admin.model.mybatis.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.activity.ActivitySkuDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.activity.ActivitySku;

@Repository
public class ActivitySkuDaoImpl extends BaseMallDaoImpl implements ActivitySkuDao {

	@Override
	public int insert(ActivitySku bean) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("ActivitySku.insertActivitySku", bean);
	}

	@Override
	public int update(ActivitySku bean) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("ActivitySku.updateActivitySku", bean);
	}

	@Override
	public int update(long originPrice, long activityPrice, int stock, long activityBgSkuId) {
		/***
		 * update tb_activity_sku set
		 * origin_price=#{originPrice},acitivity_price
		 * =#{activityPrice},stock=#{stock} where
		 * activity_bg_sku_id=#{activityBgSkuId}
		 */
		Map map = new HashMap();
		map.put("originPrice", originPrice);
		map.put("activityPrice", activityPrice);
		map.put("stock", stock);
		map.put("activityBgSkuId", activityBgSkuId);
		return this.getSqlSession().update("ActivitySku.updateActivitySku", map);
	}

	@Override
	public ActivitySku getActivitySku(long activityBgSkuId, long collegeId) {
		Map map = new HashMap();
		map.put("activityBgSkuId", activityBgSkuId);
		map.put("collegeId", collegeId);
		return this.getSqlSession().selectOne("ActivitySku.selectByUnionKey", map);
	}

	@Override
	public List<ActivitySku> getActivitiSkuList(long activityBgSkuId, long collegeId) {
		Map map = new HashMap();
		map.put("activityBgSkuId", activityBgSkuId);
		if (collegeId > 0) {
			map.put("collegeId", collegeId);
		}

		return this.getSqlSession().selectList("ActivitySku.selectListByBgSkuIdAndCID", map);
	}

}
