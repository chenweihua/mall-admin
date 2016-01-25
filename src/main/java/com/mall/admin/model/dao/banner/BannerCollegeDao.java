package com.mall.admin.model.dao.banner;

import java.util.List;

import com.mall.admin.vo.banner.BannerCollege;

public interface BannerCollegeDao {
	public BannerCollege getById(long bannerCollegeId);
	
	public BannerCollege getByUnionId(long bannerId,long collegeId);
	
	public List<BannerCollege> getByBannerId(long bannerId);
	
	public long insertObject(BannerCollege bannerCollege);
	
	public int deleteById(long bannerCollegeId);

	public int deleteByUnionId(long bannerId,long collegeId);
	
	public int updateObject(BannerCollege bannerCollege);
	
	public int updateByBannerCollege(BannerCollege bannerCollege);
}
