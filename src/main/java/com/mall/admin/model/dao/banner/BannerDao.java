package com.mall.admin.model.dao.banner;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.banner.Banner;

public interface BannerDao {
	public Banner getById(long bannerId);
	
	public long insertObject(Banner banner);
	
	public int deleteById(long bannerId);
	
	public int updateObject(Banner banner);
	
	public List<Banner> selectListByPage(Map param,PaginationInfo paginationInfo);
	/**
	 * 根据banner显示信息和跳转信息软删除banner
	 * 当数据为-1或为null时，该条件失效
	 * @param bannerPosition
	 * @param connect_id
	 * @param actionType
	 * @param hrefUrl
	 * @return
	 */
	public int deleteByConnectHrefUrl(int bannerPosition,long connectId,int actionType,String hrefUrl);
}
