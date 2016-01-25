package com.mall.admin.service.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.constant.Constants;
import com.mall.admin.model.dao.goods.BgGoodsDao;
import com.mall.admin.model.dao.goods.BgSkuDao;
import com.mall.admin.model.dao.goods.GoodsDao;
import com.mall.admin.model.dao.goods.SkuDao;
import com.mall.admin.model.dao.goods.SkuPropertyDao;
import com.mall.admin.service.base.BaseServiceImpl;
import com.mall.admin.service.goods.SkuService;
import com.mall.admin.vo.goods.BgSku;
import com.mall.admin.vo.goods.Goods;
import com.mall.admin.vo.goods.Sku;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.dto.CollegeDto;

@Service
public class SkuServiceImpl extends BaseServiceImpl implements SkuService {
	@Autowired
	BgSkuDao bgSkuDao;
	@Autowired
	SkuDao skuDao;
	@Autowired
	GoodsDao goodsDao;
	@Autowired
	BgGoodsDao bgGoodsDao;
	@Autowired
	SkuPropertyDao skuPropertyDao;

	
	@Override
	public List<Sku> get4copy2activity(Long collegeId) {
		return skuDao.get4copy2activity(collegeId);
	}

	@Override
	public long addOrUpdateSku(BgSku bgSku, long collegeId, long goodsId, int distributeType, int skuType,
			boolean isOld) {
		// 查看是否有相同的单品
		Sku skuTemp = null;
		if (skuType == 3) {
			if (bgSku != null) {
				Goods goodsTemp = goodsDao.getByBgGoodsIdAndCollegeId(bgSku.getBgGoodsId(), collegeId);
				if (goodsTemp != null) {
					skuTemp = skuDao.getBybgSkuIdAndCollegeIdAndGoodsId(bgSku.getBgSkuId(),
							collegeId, goodsTemp.getGoodsId(), distributeType);
				}
			}
			// 表明单品中不存在，多线程操作，数据出现问题了，正常情况肯定会存在，当单品不存在时，默认不加入该商品，并提示用户从新选择
			if (skuTemp == null) {
				return -2L;
			}
		}

		long result = 0;
		if (bgSku != null) {
			Sku temp = getBybgSkuIdAndCollegeIdAndGoodsId(bgSku.getBgSkuId(), collegeId, goodsId,
					distributeType);
			if (temp != null) {
				if (!isOld) {
					temp.setSkuStatus(Constants.SKU_STATUS_ONSALE);
				}
				temp.setIsDel(0);
				temp.setSkuType(skuType);
				temp.setStorageId(bgSku.getStorageId());
				temp.setImageUrl(bgSku.getImageUrl());
				int i = skuDao.updateByObject(temp);
				if (i > 0) {
					return temp.getSkuId();
				}
				return -1L;
			} else {
				Sku sku = new Sku(bgSku);
				// 不限售
				sku.setStock(Constants.SKU_STOCK_NAN);
				sku.setSkuStatus(Constants.SKU_STATUS_ONSALE);
				sku.setCollegeId(collegeId);
				sku.setGoodsId(goodsId);
				sku.setDistributeType(distributeType);
				sku.setSkuType(skuType);
				if (skuTemp != null) {
					sku.setOriginPrice(skuTemp.getOriginPrice());
					sku.setAppPrice(skuTemp.getAppPrice());
					sku.setWapPrice(skuTemp.getWapPrice());
				}
				long sku_id = skuDao.insert(sku);
				if (sku_id == -1) {
					logger.info("范围设置时，插入sku失败");
				}
				result = sku_id;
			}
		}
		return result;
	}

	@Override
	public Sku getBybgSkuIdAndCollegeIdAndGoodsId(long bgSkuId, long collegeId, long goodsId, int isLdc) {
		return skuDao.getBybgSkuIdAndCollegeIdAndGoodsId(bgSkuId, collegeId, goodsId, isLdc);
	}

	@Override
	public boolean addRdcPrice2CollegeDto(CollegeDto collegeDto, long bgGoodsId, int isLdc) {
		BgSku bgSku = bgSkuDao.getByBgGoodsId(bgGoodsId);
		Goods goods = goodsDao.getByBgGoodsIdAndCollegeId(bgGoodsId, collegeDto.getCollege().getCollegeId());
		if (goods != null) {
			collegeDto.setMaxNum(goods.getMaxNum());
			collegeDto.setGoodsId(goods.getGoodsId());
		}

		// 做了双层验证
		Sku sku = null;
		if (bgSku != null && goods != null) {
			sku = skuDao.getBybgSkuIdAndCollegeIdAndGoodsId(bgSku.getBgSkuId(), collegeDto.getCollege()
					.getCollegeId(), goods.getGoodsId(), isLdc);
		}
		if (sku != null) {
			collegeDto.setOriginPrice(sku.getOriginPrice());
			collegeDto.setWapPrice(sku.getWapPrice());
			collegeDto.setAppPrice(sku.getAppPrice());
			collegeDto.setSkuId(sku.getSkuId());
			return true;
		}
		return false;
	}

	@Override
	public boolean batchModifyRdcPriceMaxNum(List<CollegeDto> collegeDtos, long bgGoodsId, long originPrice,
			long wapPrice, long appPrice, long maxNum, int isLdc) {
		BgSku bgSku = bgSkuDao.getByBgGoodsId(bgGoodsId);
		if (bgSku == null) {
			return false;
		}
		College college = null;
		Sku sku = null;
		for (CollegeDto collegeDto : collegeDtos) {
			college = collegeDto.getCollege();
			if (college == null) {
				return false;
			}
			Goods goods = goodsDao.getByBgGoodsIdAndCollegeId(bgGoodsId, college.getCollegeId());
			// 做了双层验证
			if (goods == null) {
				return false;
			}
			sku = skuDao.getBybgSkuIdAndCollegeIdAndGoodsId(bgSku.getBgSkuId(), college.getCollegeId(),
					goods.getGoodsId(), isLdc);
			if (sku == null) {
				return false;
			}
			int result = skuDao.updatePrice(sku.getSkuId(), originPrice, wapPrice, appPrice);
			if (result < 0) {
				return false;
			}
			int r2 = goodsDao.updateMaxNum(sku.getGoodsId(), maxNum);
			if (r2 < 0) {
				return false;
			}

			modifyMultiPrice(sku, originPrice, wapPrice, appPrice);
		}
		return true;
	}

	@Override
	public boolean modifyPriceMaxnum(long skuId, long originPrice, long wapPrice, long appPrice, long maxNum) {
		int result = skuDao.updatePrice(skuId, originPrice, wapPrice, appPrice);
		if (result < 0) {
			return false;
		}

		Sku sku = skuDao.getById(skuId);
		if (sku == null)
			return false;

		int r2 = goodsDao.updateMaxNum(sku.getGoodsId(), maxNum);
		if (r2 < 0) {
			return false;
		}

		modifyMultiPrice(sku, originPrice, wapPrice, appPrice);

		return true;
	}

	private boolean modifyMultiPrice(Sku sku, long originPrice, long wapPrice, long appPrice) {
		// 更新关联的聚合品价格
		List<Long> bgGoodsIds = skuPropertyDao.getBgGoodsIdsByBgSkuId(sku.getBgSkuId());
		Goods tempGoods = null;
		Sku tempSku = null;
		if (bgGoodsIds != null && bgGoodsIds.size() > 0) {
			for (long bgGoodsId : bgGoodsIds) {
				tempGoods = goodsDao.getByBgGoodsIdAndCollegeId(bgGoodsId, sku.getCollegeId());
				if (tempGoods != null) {
					tempSku = skuDao.getBybgSkuIdAndCollegeIdAndGoodsId(sku.getBgSkuId(),
							sku.getCollegeId(), tempGoods.getGoodsId(),
							sku.getDistributeType());
					if (tempSku != null) {
						int ui = skuDao.updatePrice(tempSku.getSkuId(), originPrice, wapPrice,
								appPrice);
						if (ui < 0) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean modifyMaxnum(long goodsId, long maxNum) {
		int r2 = goodsDao.updateMaxNum(goodsId, maxNum);
		if (r2 < 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<Sku> getSkuListByGoodsId(long goodsId) {
		// TODO Auto-generated method stub
		return skuDao.getListByGoodsId(goodsId);
	}

	@Override
	public List<Long> getCollegeIdsByBgSkuId(long bgSkuId, int isLdc) {
		return skuDao.getCollegeIdsByBgSkuId(bgSkuId, isLdc);
	}

	@Override
	public int batchModifyPrice(long bgGoodsId, long bgSkuId, long originPrice, long wapPrice, long appPrice,
			long maxNum) {
		goodsDao.batchUpdateMaxNum(bgGoodsId, maxNum);
		int ret = skuDao.updatePriceByBgSkuId(bgSkuId, originPrice, wapPrice, appPrice);
		return ret;
	}
}
