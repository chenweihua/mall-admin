package com.mall.admin.service.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.goods.GoodsPropertyDao;
import com.mall.admin.service.goods.GoodsPropertyService;
import com.mall.admin.vo.goods.GoodsProperty;

@Service
public class GoodsPropertyServiceImpl implements GoodsPropertyService {
	@Autowired
	GoodsPropertyDao goodsPropertyDao;

	@Override
	public GoodsProperty getById(long goodsPropertyId) {
		return goodsPropertyDao.getById(goodsPropertyId);
	}

	@Override
	public int deleteById(long goodsPropertyId) {
		return goodsPropertyDao.deleteById(goodsPropertyId);
	}

	@Override
	public int updateByObject(GoodsProperty goodsProperty) {
		return goodsPropertyDao.updateByObject(goodsProperty);
	}

	@Override
	public long insert(GoodsProperty goodsProperty) {
		return goodsPropertyDao.insert(goodsProperty);
	}

	@Override
	public List<GoodsProperty> getByBgGoodsId(long bgGoodsId) {
		return goodsPropertyDao.getByBgGoodsId(bgGoodsId);
	}
}
