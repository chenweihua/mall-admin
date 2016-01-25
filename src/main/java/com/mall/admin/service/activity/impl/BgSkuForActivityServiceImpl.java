package com.mall.admin.service.activity.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.activity.ActivitySkuDao;
import com.mall.admin.model.dao.activity.BgSkuForActivityDao;
import com.mall.admin.service.activity.BgSkuForActivityService;
import com.mall.admin.vo.activity.BgSkuForActivity;
import com.mall.admin.vo.goods.BgGoods;

@Service
public class BgSkuForActivityServiceImpl implements BgSkuForActivityService {

	@Autowired
	BgSkuForActivityDao bgSkuForActivityDao;
	@Autowired
	ActivitySkuDao activitySkuDao;

	@Override
	public List<BgSkuForActivity> getListByActivityBgGoodsId(long activityBgGoodsId) {
		// TODO Auto-generated method stub
		return bgSkuForActivityDao.getBgSkuByBgGoodsId(activityBgGoodsId);
	}
	
	public List<BgSkuForActivity> getListByActivityBgGoodsIdAndGoodsType(long activityBgGoodsId,int goodsType) {
		if(goodsType == BgGoods.GOODS_TYPE_SINGLE || goodsType == BgGoods.GOODS_TYPE_GROUP) { //单品或组合品
			return bgSkuForActivityDao.getBgSkuByBgGoodsId(activityBgGoodsId);
		} else {  //聚合品
			return bgSkuForActivityDao.getBgSkuAndGoodsInfoByBgGoodsId(activityBgGoodsId);
		}		
	}

	@Override
	public int insert(BgSkuForActivity bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(BgSkuForActivity bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateActivityBgSku(long originPrice, long activityPrice, int stock, long activityBgSkuId) {
		bgSkuForActivityDao.update(originPrice, activityPrice, stock, activityBgSkuId);
		activitySkuDao.update(originPrice, activityPrice, stock, activityBgSkuId);
		return 0;
	}
}
