package com.mall.admin.service.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.goods.BgSkuGbmDao;
import com.mall.admin.service.goods.BgSkuGbmService;
import com.mall.admin.vo.goods.BgSkuGbm;

@Service
public class BgSkuGbmServiceImpl implements BgSkuGbmService {
	@Autowired
	BgSkuGbmDao bgSkuGbmDao;

	@Override
	public long insert(BgSkuGbm bgSkuGbm) {
		return bgSkuGbmDao.insert(bgSkuGbm);
	}

	@Override
	public List<Long> getWmsGoodsIdByBgGoodsId(long bgGoodsId) {
		// TODO Auto-generated method stub
		return bgSkuGbmDao.getWmsGoodsIdByBgGoodsId(bgGoodsId);
	}

	@Override
	public List<BgSkuGbm> getBgSkuGbmByBgGoodsId(long bgGoodsId) {
		// TODO Auto-generated method stub
		return bgSkuGbmDao.getBgSkuGbmByBgGoodsId(bgGoodsId);
	}

	@Override
	public List<Long> getWmsGoodsIdByBgPolyGoodsId(long bgGoodsId) {
		// TODO Auto-generated method stub
		return bgSkuGbmDao.getWmsGoodsIdByBgPolyGoodsId(bgGoodsId);
	}

	@Override
	public List<Long> getWmsGoodsIdByBgSkuId(long bgSkuId) {
		return bgSkuGbmDao.getWmsGoodsIdByBgSkuId(bgSkuId);
	}

}
