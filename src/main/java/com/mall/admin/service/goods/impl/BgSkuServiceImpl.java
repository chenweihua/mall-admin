package com.mall.admin.service.goods.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.goods.BgSkuDao;
import com.mall.admin.model.dao.goods.SkuPropertyDao;
import com.mall.admin.service.goods.BgSkuService;
import com.mall.admin.vo.goods.BgSku;

@Service
public class BgSkuServiceImpl implements BgSkuService {
	@Autowired
	BgSkuDao bgSkuDao;
	@Autowired
	SkuPropertyDao skuPropertyDao;

	@Override
	public long insert(BgSku bgSku) {
		return bgSkuDao.insert(bgSku);
	}

	@Override
	public BgSku getBgSkuById(Long bgSkuId) {
		return bgSkuDao.getById(bgSkuId);
	}

	@Override
	public BgSku getByBgGoodsId(long bgGoodsId) {
		return bgSkuDao.getByBgGoodsId(bgGoodsId);
	}

	@Override
	public List<BgSku> getByGoodsId(long bgGoodsId, int type) {
		List<BgSku> bgSkus = new ArrayList<BgSku>();
		if(type == 1 || type == 2){
			bgSkus.add(bgSkuDao.getByBgGoodsId(bgGoodsId));
		}else if(type == 3){
			List<Long> bgSkuIds = skuPropertyDao.getBgSkuByBgGoodsId(bgGoodsId);
			for(Long id : bgSkuIds){
				bgSkus.add(bgSkuDao.getById(id));
			}
		}
		return bgSkus;
	}
	
}
