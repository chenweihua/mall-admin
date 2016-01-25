package com.mall.admin.model.mybatis.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.activity.BgSkuForActivityDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.activity.BgSkuForActivity;

@Repository
public class BgSkuForActivityDaoImpl extends BaseMallDaoImpl implements BgSkuForActivityDao {

	@Override
	public List<BgSkuForActivity> getBgSkuByBgGoodsId(long activityBgGoodsId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("BgSKuForActivity.selectBgSkuForActivity", activityBgGoodsId);
	}
	
	public List<BgSkuForActivity> getBgSkuAndGoodsInfoByBgGoodsId(long activityBgGoodsId) {
		return this.getSqlSession().selectList("BgSKuForActivity.selectBgSkuForActivityWithBgGoodsName", activityBgGoodsId);
	}

	@Override
	public int insert(BgSkuForActivity bean) {
		// TODO Auto-generated method stub
		// BgSkuForActivity bean =
		return this.getSqlSession().insert("BgSKuForActivity.insertBgSkuForActivity", bean);
	}

	@Override
	public int update(long originPrice, long activityPrice, int stock, long activityBgSkuId) {
		/*
		 * update tb_activity_bg_sku set
		 * origin_price=#{originPrice},activity_price
		 * ={activityPrice},stock=#{stock},update_time=now() where
		 * activity_bg_sku_id=#{activityBgSkuId}
		 */
		Map map = new HashMap();
		map.put("originPrice", originPrice);
		map.put("activityPrice", activityPrice);
		map.put("stock", stock);
		map.put("activityBgSkuId", activityBgSkuId);
		return this.getSqlSession().update("BgSKuForActivity.updateBgSkuForActivity", map);
	}

	@Override
	public BgSkuForActivity getBgSkuForActivity(long bg_sku_id, long activity_bg_goods_id) {
		Map map = new HashMap();
		map.put("bg_sku_id", bg_sku_id);
		map.put("activity_bg_goods_id", activity_bg_goods_id);
		return this.getSqlSession().selectOne("BgSKuForActivity.selectByUnionKey", map);
	}

	@Override
	public int updateIsDel(long activityBgSkuId, int status) {
		Map map = new HashMap();
		map.put("activityBgSkuId", activityBgSkuId);
		map.put("status", status);
		return this.getSqlSession().update("BgSKuForActivity.updateIsDel", map);
	}

}
