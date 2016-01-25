package com.mall.admin.service.banner.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.banner.BannerRegionDao;
import com.mall.admin.service.banner.BannerRegionService;
import com.mall.admin.vo.banner.BannerRegion;
@Service
public class BannerRegionServiceImpl implements BannerRegionService {
	@Autowired
	BannerRegionDao bannerRegionDao;
	
	
	@Override
	public BannerRegion getById(long bannerRegionId) {
		return bannerRegionDao.getById(bannerRegionId);
	}

	@Override
	public List<BannerRegion> getByBannerId(long bannerId) {
		return bannerRegionDao.getByBannerId(bannerId);
	}

	@Override
	public long insertObject(BannerRegion bannerRegion) {
		BannerRegion brTemp = getByUnionId(bannerRegion.getBannerId(), bannerRegion.getRegionId(), bannerRegion.getRegionType());
		if(brTemp != null){
			int ui = updateByUnionId(bannerRegion);
			if(ui < 0)
				return -1L;
			return brTemp.getBannerRegionId();
		}
		return bannerRegionDao.insertObject(bannerRegion);
	}

	@Override
	public int deleteById(long bannerRegionId) {
		return bannerRegionDao.deleteById(bannerRegionId);
	}

	@Override
	public int deleteByUnionId(long bannerId, long regionId, int regionType) {
		return bannerRegionDao.deleteByUnionId(bannerId, regionId, regionType);
	}

	@Override
	public int updateObject(BannerRegion bannerRegion) {
		return bannerRegionDao.updateObject(bannerRegion);
	}

	@Override
	public BannerRegion getByUnionId(long bannerId, long regionId,
			int regionType) {
		return bannerRegionDao.getByUnionId(bannerId, regionId, regionType);
	}

	@Override
	public int updateByUnionId(BannerRegion bannerRegion) {
		return bannerRegionDao.updateByUnionId(bannerRegion);
	}

	@Override
	public boolean isExist(long bannerId, long regionId, int regionType) {
		if(getByUnionId(bannerId, regionId, regionType) == null)
			return false;
		return true;
	}

	@Override
	public List<BannerRegion> selectListByRegionId(long regionId, int regionType) {
		return bannerRegionDao.selectListByRegionId(regionId, regionType);
	}
}
