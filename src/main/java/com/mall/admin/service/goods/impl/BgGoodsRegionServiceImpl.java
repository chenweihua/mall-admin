package com.mall.admin.service.goods.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.goods.BgGoodsRegionDao;
import com.mall.admin.service.goods.BgGoodsRegionService;
import com.mall.admin.vo.goods.BgGoodsRegion;
import com.mall.admin.vo.mallbase.dto.CityDto;
import com.mall.admin.vo.mallbase.dto.StorageDto;

@Service
public class BgGoodsRegionServiceImpl implements BgGoodsRegionService {
	@Autowired
	BgGoodsRegionDao bgGoodsRegionDao;

	@Override
	public BgGoodsRegion getByBgGoodsIdAndRegionId(long bgGoodsId, long regionId, int regionType) {
		return bgGoodsRegionDao.getByBgGoodsIdAndRegionId(bgGoodsId, regionId, regionType);
	}

	@Override
	public long insertOrUpdatePrice(BgGoodsRegion bgGoodsRegion) {
		BgGoodsRegion temp = getByBgGoodsIdAndRegionId(bgGoodsRegion.getBgGoodsId(),
				bgGoodsRegion.getRegionId(), bgGoodsRegion.getRegionType());
		if (temp == null) {
			return bgGoodsRegionDao.insert(bgGoodsRegion);
		} else {
			temp.setOriginPrice(bgGoodsRegion.getOriginPrice());
			temp.setWapPrice(bgGoodsRegion.getWapPrice());
			temp.setAppPrice(bgGoodsRegion.getAppPrice());
			temp.setMaxNum(bgGoodsRegion.getMaxNum());
			int r = bgGoodsRegionDao.updateByObject(temp);
			if (r < 0) {
				return -1L;
			}
			return temp.getBgGoodsRegionId();
		}
	}

	@Override
	public boolean addPrice2Dto(Object o, long bgGoodsId, int regionType) {

		if (regionType == 1) {
			// 仓
			StorageDto sDto = (StorageDto) o;
			BgGoodsRegion sRegion = getByBgGoodsIdAndRegionId(bgGoodsId, sDto.getStorage().getStorageId(),
					regionType);
			if (sRegion != null) {
				sDto.setOriginPrice(sRegion.getOriginPrice());
				sDto.setWapPrice(sRegion.getWapPrice());
				sDto.setAppPrice(sRegion.getAppPrice());
				sDto.setMaxNum(sRegion.getMaxNum());
			}
		} else if (regionType == 2) {
			// 城市
			CityDto cDto = (CityDto) o;
			BgGoodsRegion sRegion = getByBgGoodsIdAndRegionId(bgGoodsId, cDto.getCity().getCityId(),
					regionType);
			if (sRegion != null) {
				cDto.setOriginPrice(sRegion.getOriginPrice());
				cDto.setWapPrice(sRegion.getWapPrice());
				cDto.setAppPrice(sRegion.getAppPrice());
				cDto.setMaxNum(sRegion.getMaxNum());
			}
		}
		return true;
	}

	@Override
	public int update(long regionId, long bgGoodsId, int regionType, long weight, long stock, int status) {
		if (!bgGoodsRegionDao.isExist(bgGoodsId, regionId, regionType)) {
			return 2;
		}
		int r = bgGoodsRegionDao
				.updateWeightStockStatus(bgGoodsId, regionId, regionType, weight, stock, status);
		if (r > 0) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public int update(long storageId, long bgGoodsId, int regionType, int status) {
		if (!bgGoodsRegionDao.isExist(bgGoodsId, storageId, regionType)) {
			return 2;
		}
		int r = bgGoodsRegionDao.updateStatus(bgGoodsId, storageId, regionType, status);
		if (r > 0) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public int update(long regionId, long bgGoodsId, int regionType, long originPrice, long wapPrice,
			long appPrice, long maxNum) {
		return bgGoodsRegionDao
				.update(regionId, bgGoodsId, regionType, originPrice, wapPrice, appPrice, maxNum);
	}

	@Override
	public int batchUpdate(long bgGoodsId, long originPrice, long wapPrice, long appPrice, long maxNum) {
		// TODO Auto-generated method stub
		return bgGoodsRegionDao.batchUpdate(bgGoodsId, originPrice, wapPrice, appPrice, maxNum);
	}

}
