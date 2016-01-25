package com.mall.admin.service.banner.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.base._;
import com.mall.admin.constant.BannerHrefUrlConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.enumdata.BannerTypeEnum;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.dao.banner.BannerDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.activity.ActivityService;
import com.mall.admin.service.banner.BannerCollegeService;
import com.mall.admin.service.banner.BannerRegionService;
import com.mall.admin.service.banner.BannerService;
import com.mall.admin.service.base.BaseServiceImpl;
import com.mall.admin.service.mallbase.CategoryService;
import com.mall.admin.service.navigation.NavigationService;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.banner.Banner;
import com.mall.admin.vo.banner.BannerCollege;
import com.mall.admin.vo.banner.BannerRegion;
import com.mall.admin.vo.category.Category;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.navigation.Navigation;
import com.mall.admin.vo.user.User;

@Service
public class BannerServiceImpl extends BaseServiceImpl implements BannerService {
	@Autowired
	BannerDao bannerDao;
	@Autowired
	BannerCollegeService bannerCollegeService;
	@Autowired
	BannerRegionService bannerRegionService;
	@Autowired
	ActivityService activityService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	private NavigationService navigationService;

	@Override
	public Banner getById(long bannerId) {
		Banner banner = bannerDao.getById(bannerId);
		completeObject(banner);
		return banner;
	}

	@Override
	public long insertObject(Banner banner) {
		Banner bannerTemp = getById(banner.getBannerId());
		if (bannerTemp != null) {
			int ui = updateObject(banner);
			if (ui < 0)
				return -1L;
			return bannerTemp.getBannerId();
		} else {
			return bannerDao.insertObject(banner);
		}
	}

	@Override
	public int deleteById(long bannerId) {
		return bannerDao.deleteById(bannerId);
	}

	@Override
	public int updateObject(Banner banner) {
		return bannerDao.updateObject(banner);
	}

	/**
	 * 设置banner的区域信息
	 */
	@Override
	public Map<String, Object> setRegion(ZtreeBean ztreeBean, long bannerId, User user) {

		List<BannerCollege> old_bcList = bannerCollegeService.getByBannerId(bannerId);
		List<BannerRegion> old_brList = bannerRegionService.getByBannerId(bannerId);
		List<Long> old_cIds = getBcIds(old_bcList);
		List<Long> old_rIds = getBrIds(old_brList, BannerRegion.REGION_TYPE_CITY);
		List<Long> new_cIds = new ArrayList<>();
		List<Long> new_rIds = new ArrayList<>();

		List<ZtreeBean> cityZtreeList = ztreeBean.getChildren();
		if (cityZtreeList == null || cityZtreeList.size() == 0) {
			return buildObj(-1, "范围设置失败，请重试！");
		}

		for (ZtreeBean cityBean : cityZtreeList) {
			List<ZtreeBean> collegeZtreeList = cityBean.getChildren();
			if (collegeZtreeList == null || collegeZtreeList.size() == 0) {
				return buildObj(-1, "范围设置失败，请重试！");
			}

			for (ZtreeBean collegeBean : collegeZtreeList) {
				long cId = _.toLong(collegeBean.getId());
				if (collegeBean.checked.equals("true")) {
					new_cIds.add(cId);
					if (!old_cIds.contains(cId)) {
						// 新增或将is_del更新为0
						BannerCollege bc = new BannerCollege();
						bc.setCollegeId(cId);
						bc.setBannerId(bannerId);
						bc.setIsDel(0);
						bc.setOperator(user.getUser_id());
						long bannerCollegeId = bannerCollegeService.insertObject(bc);
						if (bannerCollegeId < 0) {
							return buildObj(-1, "范围设置失败，请重试！");
						}
					}
				}
				if (collegeBean.checked.equals("false")) {
					cityBean.setAllChecked(false);
				}
			}
		}

		// 将旧college中不存在的删除
		for (Long old_c : old_cIds) {
			if (!new_cIds.contains(old_c)) {
				int di = bannerCollegeService.deleteByUnionId(bannerId, old_c);
				if (di < 0) {
					return buildObj(-1, "范围设置失败，请重试！");
				}
			}
		}

		// 控制region
		for (ZtreeBean cityBean : cityZtreeList) {
			long cityId = _.toLong(cityBean.getId().substring(5));
			if (cityBean.isAllChecked) {
				new_rIds.add(cityId);
				if (!old_rIds.contains(cityId)) {
					BannerRegion br = new BannerRegion();
					br.setBannerId(bannerId);
					br.setIsDel(0);
					br.setRegionId(cityId);
					br.setRegionType(BannerRegion.REGION_TYPE_CITY);
					long bannerRegionId = bannerRegionService.insertObject(br);
					if (bannerRegionId < 0) {
						return buildObj(-1, "范围设置失败，请重试！");
					}
				}
			}
		}

		// 将旧region中不存在的删除
		for (Long old_r : old_rIds) {
			if (!new_rIds.contains(old_r)) {
				int di = bannerRegionService.deleteByUnionId(bannerId, old_r,
						BannerRegion.REGION_TYPE_CITY);
				if (di < 0) {
					return buildObj(-1, "范围设置失败，请重试！");
				}
			}
		}

		return buildObj(0, "添加成功");
	}

	@Override
	public boolean setValue4Region(ZtreeBean ztreeBean, long bannerId) {
		List<BannerCollege> old_bcList = bannerCollegeService.getByBannerId(bannerId);
		List<Long> old_cIds = getBcIds(old_bcList);

		List<ZtreeBean> cityZtreeList = ztreeBean.getChildren();
		if (cityZtreeList == null || cityZtreeList.size() == 0) {
			return true;
		}

		for (ZtreeBean cityBean : cityZtreeList) {
			List<ZtreeBean> collegeZtreeList = cityBean.getChildren();
			if (collegeZtreeList == null || collegeZtreeList.size() == 0) {
				return true;
			}

			for (ZtreeBean collegeBean : collegeZtreeList) {
				long cId = _.toLong(collegeBean.getId());
				if (old_cIds.contains(cId)) {
					collegeBean.setChecked("true");
					cityBean.setChecked("true");
					cityBean.setOpen("true");
					ztreeBean.setChecked("true");
				}
			}
		}
		return true;
	}

	private List<Long> getBcIds(List<BannerCollege> bcList) {
		List<Long> list = new ArrayList<>();
		for (BannerCollege bc : bcList) {
			list.add(bc.getCollegeId());
		}
		return list;
	}

	private List<Long> getBrIds(List<BannerRegion> brList, int regionType) {
		List<Long> list = new ArrayList<>();
		for (BannerRegion br : brList) {
			if (br.getRegionType() == regionType) {
				list.add(br.getRegionId());
			}
		}
		return list;
	}

	@Override
	public List<Banner> queryBannerByPage(Map<String, Object> param, PaginationInfo paginationInfo) {
		List<Banner> bannerList = bannerDao.selectListByPage(param, paginationInfo);
		for (Banner banner : bannerList) {
			completeObject(banner);
		}
		return bannerList;
	}

	private void completeObject(Banner banner) {
		if (banner == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		banner.setStartTimeStr(sdf.format(new Date(banner.getStartTime().getTime())));
		banner.setEndTimeStr(sdf.format(new Date(banner.getEndTime().getTime())));
		//设置跳转类型
		BannerTypeEnum hrefTypeEnum = BannerTypeEnum.getEnum(banner.getActionType());
		Map<String, String> params = BannerHrefUrlConstant.parseNewHrefUrl(banner.getNewHrefUrl());
		switch (hrefTypeEnum) {
		case BANNER_HREF_TYPE_ACTIVITY:
			if(params == null){
				//兼容老版本
				String activityStr = _.trimToNull(banner.getHrefUrl());
				if (activityStr != null) {
					Activity aTemp = null;
					if(activityStr.contains(Constants.ACTIVITY_NORMAL_FLAG)){
						aTemp = activityService.getActivityById(_.toLong(activityStr.substring(16)));
					}else if(activityStr.contains(Constants.ACTIVITY_SECKILL_FLAG)){
						aTemp = activityService.getActivityById(_.toLong(activityStr.substring(17)));
					}
					if (aTemp != null) {
						banner.setHrefUrl(aTemp.getActivityId()+"");
						banner.setHrefUrlStr("活动：" + aTemp.getActivityName());
					} else {
						banner.setHrefUrlStr("异常数据");
					}
				}
			}else{
				banner.setHrefUrl(params.get(BannerHrefUrlConstant.ACTIVITY_ID));
				banner.setHrefUrlStr("活动：" +params.get(BannerHrefUrlConstant.ACTIVITY_NAME));
			}
			break;
		case BANNER_HREF_TYPE_CATEGORY:
			if(params == null){
				//兼容老版本
				String categoryStr = _.trimToNull(banner.getHrefUrl());
				if (categoryStr != null) {
					if (categoryStr.contains(Constants.CATEGORY_FLAG)) {
						Category cTemp = categoryService.getById(_.toLong(categoryStr.substring(9)));
						if (cTemp != null) {
							banner.setHrefUrl(cTemp.getCategoryId()+"");
							banner.setHrefUrlStr("类目：" + cTemp.getCategoryName());
						} else {
							banner.setHrefUrlStr("异常数据");
						}
					} else {
						banner.setHrefUrlStr("异常数据");
					}
				}
			}else{
				banner.setHrefUrl(params.get(BannerHrefUrlConstant.CATEGORY_ID));
				banner.setHrefUrlStr("类目：" +params.get(BannerHrefUrlConstant.CATEGORY_NAME));
			}
			break;
		case BANNER_HREF_TYPE_WEBVIEW:
			params = BannerHrefUrlConstant.parseWebview(banner.getNewHrefUrl());
			if(params == null){
				//兼容老版本
				banner.setHrefUrlStr(banner.getHrefUrl());
			}else{
				banner.setHrefUrlStr(params.get(BannerHrefUrlConstant.WEBVIEW_ADDRESS));
			}
			break;
		case BANNER_HREF_TYPE_GOODSDETAIL:
			
			break;
		case BANNER_HREF_TYPE_DISCOVERY:
			String navigationIdStr = params.get(BannerHrefUrlConstant.NAVIGATION_ID);
			long navigationId = NumberUtils.toLong(navigationIdStr,-1L);
			banner.setHrefUrl(navigationIdStr);
			Navigation navigation = navigationService.getNavigationById(navigationId);
			if(navigation != null){
				banner.setHrefUrlStr("发现："+navigation.getNavigationName());
			}else{
				banner.setHrefUrlStr("发现模块");
			}
			break;
		case BANNER_HREF_TYPE_SCORE:
			banner.setHrefUrlStr("积分模块");
			break;
		case BANNER_HREF_TYPE_USERCENTER:
			banner.setHrefUrlStr("个人中心");
			break;
		default:
		}
		// 设置展示页面
		switch (banner.getBannerPosition()) {
		case Banner.BANNER_POSITION_ACTIVITY:
			Activity aTemp = activityService.getActivityById(banner.getConnectId());
			if (aTemp != null) {
				banner.setConnectStr("活动：" + aTemp.getActivityName());
			}
			break;
		case Banner.ACTION_TYPE_CATEGORY:
			Category cTemp = categoryService.getById(banner.getConnectId());
			if (cTemp != null) {
				banner.setConnectStr("类目：" + cTemp.getCategoryName());
			}
			break;
		case Banner.BANNER_POSITION_HOME:
			banner.setConnectStr("首页");
			break;
		default:
			banner.setConnectStr("错误数据");
			break;
		}
	}

	@Override
	public boolean batchCopy(College college, User user) {
		if (college == null) {
			return false;
		}
		long cityId = college.getCityId();
		Banner banner = null;
		List<BannerRegion> bannerRegionList = bannerRegionService.selectListByRegionId(cityId, 2);
		if (bannerRegionList != null && bannerRegionList.size() > 0) {
			for (BannerRegion bannerRegion : bannerRegionList) {
				if (bannerRegion != null) {
					banner = getById(bannerRegion.getBannerId());
					if (banner != null && banner.getIsDel() == 0) {
						BannerCollege bannerCollege = new BannerCollege();
						bannerCollege.setBannerId(banner.getBannerId());
						bannerCollege.setCollegeId(college.getCollegeId());
						bannerCollege.setOperator(user.getUser_id());
						bannerCollege.setIsDel(0);
						long id = bannerCollegeService.insertObject(bannerCollege);
						if (id < 0) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean batchDeleteByActivityId(long activityId) {
		// 删除显示页为活动的banner
		int di = bannerDao.deleteByConnectHrefUrl(Banner.BANNER_POSITION_ACTIVITY, activityId, -1, null);
		if (di < 0) {
			return false;
		}
		// 删除跳转页为活动的banner
		Activity temp = activityService.getActivityById(activityId);
		if(temp != null){
			if(temp.getActivityType() == 0){
				String hrefUrl = Constants.ACTIVITY_SECKILL_FLAG + activityId;
				bannerDao.deleteByConnectHrefUrl(-1, -1, Banner.ACTION_TYPE_ACTIVITY, hrefUrl);
			}else if(temp.getActionType() == 1){
				String hrefUrl = Constants.ACTIVITY_NORMAL_FLAG + activityId;
				bannerDao.deleteByConnectHrefUrl(-1, -1, Banner.ACTION_TYPE_ACTIVITY, hrefUrl);
			}
		}
		LogConstant.mallLog.info("删除活动{}", activityId);
		return true;
	}

	@Override
	public boolean batchDeleteByCategoryId(long categoryId) {
		// 删除显示页为类目的banner
		int di = bannerDao.deleteByConnectHrefUrl(Banner.BANNER_POSITION_CATEGORY, categoryId, -1, null);
		if (di < 0) {
			return false;
		}
		// 删除跳转页为类目的banner
		String hrefUrl = Constants.CATEGORY_FLAG + categoryId;
		bannerDao.deleteByConnectHrefUrl(-1, -1, Banner.ACTION_TYPE_CATEGORY, hrefUrl);
		return true;
	}

}
