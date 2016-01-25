package com.mall.admin.service.banner;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.banner.Banner;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.user.User;

public interface BannerService {
	public Banner getById(long bannerId);
	
	public long insertObject(Banner banner);
	
	public int deleteById(long bannerId);
	
	public int updateObject(Banner banner);
	
	public Map<String, Object> setRegion(ZtreeBean ztreeBean,long bannerId,User user);
	
	public boolean setValue4Region(ZtreeBean ztreeBean,long bannerId);
	
	public List<Banner> queryBannerByPage(Map<String, Object> param,PaginationInfo paginationInfo);
	
	/**
	 * 新建学校时，复制对应城市所有banner
	 * @param college
	 * @return
	 */
	public boolean batchCopy(College college,User user);
	/**
	 * 依据activityId批量删除banner（包括在banner的显示位置和跳转位置）
	 * @param activityId
	 * @return
	 */
	public boolean batchDeleteByActivityId(long activityId);
	/**
	 * 依据categoryId批量删除banner（包括在banner的显示位置和跳转位置）
	 * @param activityId
	 * @return
	 */
	public boolean batchDeleteByCategoryId(long categoryId);
}
