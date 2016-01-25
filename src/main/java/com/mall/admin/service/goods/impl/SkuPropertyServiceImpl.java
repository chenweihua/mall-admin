package com.mall.admin.service.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.goods.SkuPropertyDao;
import com.mall.admin.service.goods.SkuPropertyService;
@Service
public class SkuPropertyServiceImpl implements SkuPropertyService {
	@Autowired
	SkuPropertyDao skuPropertyDao;
	
	@Override
	public List<Long> getBgSkuByBgGoodsId(long bgGoodsId) {
		return skuPropertyDao.getBgSkuByBgGoodsId(bgGoodsId);
	}

}
