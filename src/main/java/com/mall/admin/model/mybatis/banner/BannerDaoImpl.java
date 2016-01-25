package com.mall.admin.model.mybatis.banner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.banner.BannerDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.banner.Banner;
@Repository
public class BannerDaoImpl extends BaseMallDaoImpl implements BannerDao {

	@Override
	public Banner getById(long bannerId) {
		return this.getSqlSession().selectOne("Banner.selectByPrimaryKey", bannerId);
	}

	@Override
	public long insertObject(Banner banner) {
		int result = this.getSqlSession().insert("Banner.insert", banner);
		if(result < 0){
			return -1L;
		}
		return banner.getBannerId();
	}

	@Override
	public int deleteById(long bannerId) {
		return this.getSqlSession().update("Banner.deleteByPrimaryKey", bannerId);
	}

	@Override
	public int updateObject(Banner banner) {
		return this.getSqlSession().update("Banner.updateByPrimaryKey", banner);
	}

	@Override
	public List<Banner> selectListByPage(Map param,
			PaginationInfo paginationInfo) {
		return this.selectPaginationList("Banner.selectListByPage", param,paginationInfo);
	}

	@Override
	public int deleteByConnectHrefUrl(int bannerPosition, long connectId,
			int actionType, String hrefUrl) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bannerPosition", bannerPosition < 0 ? -1 : bannerPosition);
		param.put("connectId", connectId < 0 ? -1 : connectId);
		param.put("actionType", actionType < 0 ? -1 : actionType);
		param.put("hrefUrl", hrefUrl);
		return this.getSqlSession().update("Banner.deleteByConnectHrefUrl", param);
	}
	
}
