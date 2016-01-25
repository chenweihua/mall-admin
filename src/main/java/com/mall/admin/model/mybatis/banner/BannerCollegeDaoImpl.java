package com.mall.admin.model.mybatis.banner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.banner.BannerCollegeDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.banner.BannerCollege;
@Repository
public class BannerCollegeDaoImpl extends BaseMallDaoImpl implements BannerCollegeDao {

	@Override
	public BannerCollege getById(long bannerCollegeId) {
		return this.getSqlSession().selectOne("BannerCollege.selectByPrimaryKey", bannerCollegeId);
	}

	@Override
	public BannerCollege getByUnionId(long bannerId, long collegeId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bannerId", bannerId);
		param.put("collegeId", collegeId);
		return this.getSqlSession().selectOne("BannerCollege.selectByUnionPrimaryKey", param);
	}

	@Override
	public int updateByBannerCollege(BannerCollege bannerCollege) {
		return this.getSqlSession().update("BannerCollege.updateByBannerIdCollegeId", bannerCollege);
	}

	@Override
	public List<BannerCollege> getByBannerId(long bannerId) {
		return this.getSqlSession().selectList("BannerCollege.selectByBannerId", bannerId);
	}

	@Override
	public long insertObject(BannerCollege bannerCollege) {
		int result = this.getSqlSession().insert("BannerCollege.insert", bannerCollege);
		if(result < 0){
			return -1L;
		}
		return bannerCollege.getBannerCollegeId();
	}

	@Override
	public int deleteById(long bannerCollegeId) {
		return this.getSqlSession().update("BannerCollege.deleteByPrimaryKey", bannerCollegeId);
	}
	
	
	@Override
	public int deleteByUnionId(long bannerId, long collegeId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bannerId", bannerId);
		param.put("collegeId", collegeId);
		return this.getSqlSession().update("BannerCollege.deleteByUnionPrimaryKey", param);
	}
	
	@Override
	public int updateObject(BannerCollege bannerCollege) {
		return this.getSqlSession().update("BannerCollege.updateByPrimaryKey", bannerCollege);
	}

}
