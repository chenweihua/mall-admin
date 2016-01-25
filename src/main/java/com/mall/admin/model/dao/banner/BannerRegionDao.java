package com.mall.admin.model.dao.banner;

import java.util.List;

import com.mall.admin.vo.banner.BannerRegion;

public interface BannerRegionDao {
	public BannerRegion getById(long bannerRegionId);
	
	public BannerRegion getByUnionId(long bannerId,long regionId,int regionType);
	
	public List<BannerRegion> getByBannerId(long bannerId);
	
	public long insertObject(BannerRegion bannerRegion);
	
	public int deleteById(long bannerRegionId);
	
	public int deleteByUnionId(long bannerId,long regionId,int regionType);
	
	public int updateObject(BannerRegion bannerRegion);
	
	public int updateByUnionId(BannerRegion bannerRegion);
	
	public List<BannerRegion> selectListByRegionId(long regionId,int regionType);
}
