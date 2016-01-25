package com.mall.admin.service.banner;

import java.util.List;

import com.mall.admin.vo.banner.BannerCollege;

public interface BannerCollegeService {
	public BannerCollege getById(long bannerCollegeId);
	
	public List<BannerCollege> getByBannerId(long bannerId);
	
	public BannerCollege getByUnionId(long bannerId,long collegeId);
	
	public boolean isExist(long bannerId,long collegeId);
	
	public long insertObject(BannerCollege bannerCollege);
	
	public int deleteById(long bannerCollegeId);
	
	public int deleteByUnionId(long bannerId,long collegeId);
	
	public int updateObject(BannerCollege bannerCollege);
	
	public int updateByBannerIdCollegeId(BannerCollege bannerCollege);
}
