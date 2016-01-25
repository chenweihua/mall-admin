package com.mall.admin.service.banner.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.banner.BannerCollegeDao;
import com.mall.admin.service.banner.BannerCollegeService;
import com.mall.admin.vo.banner.BannerCollege;
@Service
public class BannerCollegeServiceImpl implements BannerCollegeService {
	@Autowired
	BannerCollegeDao bannerCollegeDao;
	
	@Override
	public BannerCollege getById(long bannerCollegeId) {
		return bannerCollegeDao.getById(bannerCollegeId);
	}

	@Override
	public List<BannerCollege> getByBannerId(long bannerId) {
		return bannerCollegeDao.getByBannerId(bannerId);
	}

	@Override
	public BannerCollege getByUnionId(long bannerId, long collegeId) {
		return bannerCollegeDao.getByUnionId(bannerId, collegeId);
	}

	@Override
	public boolean isExist(long bannerId, long collegeId) {
		if(getByUnionId(bannerId, collegeId) == null)
			return false; 
		return true;
	}

	@Override
	public int updateByBannerIdCollegeId(BannerCollege bannerCollege) {
		return bannerCollegeDao.updateByBannerCollege(bannerCollege);
	}

	@Override
	public long insertObject(BannerCollege bannerCollege) {
		BannerCollege bcTemp = getByUnionId(bannerCollege.getBannerId(),bannerCollege.getCollegeId());
		if(bcTemp != null){
			int ui = updateByBannerIdCollegeId(bannerCollege);
			if(ui < 0)return -1;
			return bcTemp.getBannerCollegeId();
		}
		return bannerCollegeDao.insertObject(bannerCollege);
	}

	@Override
	public int deleteById(long bannerCollegeId) {
		return bannerCollegeDao.deleteById(bannerCollegeId);
	}

	@Override
	public int deleteByUnionId(long bannerId, long collegeId) {
		return bannerCollegeDao.deleteByUnionId(bannerId, collegeId);
	}

	@Override
	public int updateObject(BannerCollege bannerCollege) {
		return bannerCollegeDao.updateObject(bannerCollege);
	}

	
}
