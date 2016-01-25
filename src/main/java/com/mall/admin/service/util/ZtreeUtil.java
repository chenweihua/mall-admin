package com.mall.admin.service.util;

import java.util.List;
import java.util.Map;

import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.vo.activity.ActivityCollege;
import com.mall.admin.vo.activity.ActivityRegion;
import com.mall.admin.vo.couponRule.CouponCollege;
import com.mall.admin.vo.couponRule.CouponRegion;
import com.mall.admin.vo.mallbase.Storage;

public interface ZtreeUtil {

	public ZtreeBean getStorageZtree(List<Storage> storageList,boolean hasVmStorage);

	public ZtreeBean getCollegeZtree(List<Storage> storageList,boolean isAllChecked,long bgGoodsId);

	/**
	 * 根据rdc学校id和ldc学校id，生成一个树
	 * 
	 * @param rdcCollegeIdList
	 * @param ldcCollegeIdList
	 * @return
	 */
	public ZtreeBean getCollegeZtree(List<Long> rdcCollegeIdList, List<Long> ldcCollegeIdList,Long vmStorageId,List<Long> vmCollegeIdList,
			List<Long> selectedRdcCollegeIdList, List<Long> selectedLdcCollegeIdList,List<Long> selectedVmCollegeIdList);

	public void setZtreeStatus(ZtreeBean ztreeBean, List<Long> storageIdList);

	public void setZtreeStatusByCollege(ZtreeBean ztreeBean, List<Long> rdcCollegeIdList,
			List<Long> ldcCollegeIdList);

	public ZtreeBean getCollegeZtree(Map<Integer, CouponRegion> couponRegionMap,
			Map<Integer, CouponCollege> couponCollegesMap);

	public ZtreeBean getCollegeZtree4Activity(Map<Long, ActivityRegion> activityRegionMap,
			Map<Long, ActivityCollege> activityCollegesMap);

	public ZtreeBean getCityCollegeZtree();
	
	public ZtreeBean getCityCollegeZtree(List<Long> collegeIdList);

	/**
	 * 构造(省-市)或（直辖市-区） 两级树
	 * 
	 * @return
	 */
	public ZtreeBean getCityAreaZtree(List<Long> areaIdList, boolean editable);
	
	public ZtreeBean getCityAreaCollegeZtree(List<Long> checkedColleges);
	
	public ZtreeBean getCityAreaCollegeZtreeWithoutEmptyCity(List<Long> checkedColleges);

}
