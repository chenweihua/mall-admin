package com.mall.admin.service.banner;

import java.util.List;

import com.mall.admin.vo.banner.BannerRegion;

public interface BannerRegionService {
	public BannerRegion getById(long bannerRegionId);
	
	public List<BannerRegion> getByBannerId(long bannerId);
	
	public long insertObject(BannerRegion bannerRegion);
	
	public int deleteById(long bannerRegionId);
	
	public int deleteByUnionId(long bannerId,long regionId,int regionType);
	
	public int updateObject(BannerRegion bannerRegion);
	
	public BannerRegion getByUnionId(long bannerId,long regionId,int regionType);
	
	public int updateByUnionId(BannerRegion bannerRegion);
	
	public boolean isExist(long bannerId,long regionId,int regionType);
	
	public List<BannerRegion> selectListByRegionId(long regionId,int regionType);
}
