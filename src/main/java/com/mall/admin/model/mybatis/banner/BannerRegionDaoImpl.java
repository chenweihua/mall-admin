package com.mall.admin.model.mybatis.banner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.banner.BannerRegionDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.banner.BannerRegion;
@Repository
public class BannerRegionDaoImpl extends BaseMallDaoImpl implements BannerRegionDao {

	@Override
	public BannerRegion getById(long bannerRegionId) {
		return this.getSqlSession().selectOne("BannerRegion.selectByPrimaryKey", bannerRegionId);
	}

	@Override
	public List<BannerRegion> getByBannerId(long bannerId) {
		return this.getSqlSession().selectList("BannerRegion.selectByBanner", bannerId);
	}


	@Override
	public long insertObject(BannerRegion bannerRegion) {
		int result = this.getSqlSession().insert("BannerRegion.insert", bannerRegion);
		if(result < 0){
			return -1L;
		}
		return bannerRegion.getBannerRegionId();
	}

	@Override
	public int deleteById(long bannerRegionId) {
		return this.getSqlSession().update("BannerRegion.deleteByPrimaryKey", bannerRegionId);
	}

	@Override
	public int deleteByUnionId(long bannerId, long regionId, int regionType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bannerId", bannerId);
		param.put("regionId", regionId);
		param.put("regionType", regionType);
		return this.getSqlSession().update("BannerRegion.deleteByUnionPrimaryKey", param);
	}

	@Override
	public int updateObject(BannerRegion bannerRegion) {
		return this.getSqlSession().update("BannerRegion.updateByPrimaryKey", bannerRegion);
	}

	@Override
	public BannerRegion getByUnionId(long bannerId, long regionId,
			int regionType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bannerId", bannerId);
		param.put("regionId", regionId);
		param.put("regionType", regionType);
		return this.getSqlSession().selectOne("BannerRegion.selectByUnionPrimaryKey", param);
	}

	@Override
	public int updateByUnionId(BannerRegion bannerRegion) {
		return this.getSqlSession().update("BannerRegion.updateByBannerIdRegionIdRegionType", bannerRegion);
	}

	@Override
	public List<BannerRegion> selectListByRegionId(long regionId, int regionType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("regionId", regionId);
		param.put("regionType", regionType);
		return this.getSqlSession().selectList("BannerRegion.selectByRegionIdType", param);
	}
}
