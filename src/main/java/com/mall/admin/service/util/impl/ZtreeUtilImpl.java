package com.mall.admin.service.util.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mall.admin.base._;
import com.mall.admin.constant.CityConstant;
import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.constant.Constants;
import com.mall.admin.constant.StroageConstant;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.service.goods.GoodsService;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.storage.StorageCollegeService;
import com.mall.admin.service.util.ZtreeUtil;
import com.mall.admin.vo.activity.ActivityCollege;
import com.mall.admin.vo.activity.ActivityRegion;
import com.mall.admin.vo.couponRule.CouponCollege;
import com.mall.admin.vo.couponRule.CouponRegion;
import com.mall.admin.vo.mallbase.City;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;

@Service
public class ZtreeUtilImpl implements ZtreeUtil {
	@Autowired
	private CityService cityService;
	@Autowired
	private CollegeService collegeService;
	@Autowired
	private StorageCollegeService storageCollegeService;
	@Autowired
	private GoodsService goodsService;

	/**
	 * 根据storages生产ZtreeBean对象 叶子节点是仓库
	 * 叶子节点id用的是仓库id，城市id是city_{city_id},RDC和LDC的id分别是RDC_1和ldc_1
	 * ，根节点的id是root;
	 */
	@Override
	public ZtreeBean getStorageZtree(List<Storage> storageList,boolean hasVmStorage) {
		ZtreeBean rootZtreeBean = null;
		ZtreeBean rdcZtreeBean = null;
		ZtreeBean ldcZtreeBean = null;
		ZtreeBean vmZtreeBean = null;
		List<ZtreeBean> ldcChildList = null;

		List<Storage> RDCStorageList = new ArrayList<Storage>();
		Map<Long, List<Storage>> LDCStorageMap = new HashMap<Long, List<Storage>>();
		List<Storage> VMStorageList = new ArrayList<Storage>();
		for (Storage stoage : storageList) {
			if (stoage.getStorageType() == Storage.RDC_STORAGE) {
				RDCStorageList.add(stoage);
			} else if (stoage.getStorageType() == Storage.LDC_STORAGE){
				List<Storage> ldcCityStorageList = LDCStorageMap.get(stoage.getCityId());
				if (ldcCityStorageList == null) {
					ldcCityStorageList = new ArrayList<Storage>();
					LDCStorageMap.put(stoage.getCityId(), ldcCityStorageList);
				}
				ldcCityStorageList.add(stoage);
			}else if(stoage.getStorageType() == Storage.VM_STORAGE){
				VMStorageList.add(stoage);
			}
		}
		if (RDCStorageList.size() > 0) {
			rdcZtreeBean = new ZtreeBean("rdc_1", "root", Constants.RDC_STORAGE_NAME, 10,
					Constants.ICON_RDC);
			List<ZtreeBean> childBeanList = new ArrayList<ZtreeBean>();
			for (Storage storage : RDCStorageList) {
				ZtreeBean storageZtreeBean = new ZtreeBean(storage.getStorageId() + "", "rdc_1",
						storage.getStorageName(), 12, Constants.ICON_STORAGE);
				childBeanList.add(storageZtreeBean);
			}
			rdcZtreeBean.setChildren(childBeanList);
		}
		for (Map.Entry<Long, List<Storage>> entry : LDCStorageMap.entrySet()) {
			if (ldcZtreeBean == null) {
				ldcZtreeBean = new ZtreeBean("ldc_1", "root", Constants.LDC_STORAGE_NAME, 20,
						Constants.ICON_LDC);
				ldcChildList = new ArrayList<ZtreeBean>();
				ldcZtreeBean.children = ldcChildList;
			}
			City city = cityService.getCityById(entry.getKey());
			ZtreeBean cityBean = new ZtreeBean("city_" + city.getCityId(), "ldc_1", city.getCityName(), 21,
					Constants.ICON_STORAGE);
			List<ZtreeBean> cityChildList = new ArrayList<ZtreeBean>();
			for (Storage storage : entry.getValue()) {
				ZtreeBean storageBean = new ZtreeBean(storage.getStorageId() + "", "city_"
						+ city.getCityId(), storage.getStorageName(), 22,
						Constants.ICON_STORAGE);
				cityChildList.add(storageBean);
			}
			if (cityChildList.size() > 0) {
				cityBean.setChildren(cityChildList);
				ldcChildList.add(cityBean);
			}
		}
		if (VMStorageList.size() > 0 && hasVmStorage) {
			vmZtreeBean = new ZtreeBean("vm_1", "root", Constants.VM_STORAGE_NAME, 30,
					Constants.ICON_RDC);
			List<ZtreeBean> childBeanList = new ArrayList<ZtreeBean>();
			for (Storage storage : VMStorageList) {
				ZtreeBean storageZtreeBean = new ZtreeBean(storage.getStorageId() + "", "vm_1",
						storage.getStorageName(), 32, Constants.ICON_STORAGE);
				childBeanList.add(storageZtreeBean);
			}
			vmZtreeBean.setChildren(childBeanList);
		}
		if (rdcZtreeBean != null || ldcZtreeBean != null || (vmZtreeBean != null && hasVmStorage)) {
			rootZtreeBean = new ZtreeBean("root", "", Constants.All_STORAGE_NAME, 0, "");
			List<ZtreeBean> rootChildList = new ArrayList<ZtreeBean>();
			if (rdcZtreeBean != null) {
				rootChildList.add(rdcZtreeBean);
			}
			if (ldcZtreeBean != null) {
				rootChildList.add(ldcZtreeBean);
			}
			if (vmZtreeBean != null  && hasVmStorage) {
				rootChildList.add(vmZtreeBean);
			}
			rootZtreeBean.setChildren(rootChildList);
		}
		return rootZtreeBean;
	}

	/**
	 * 根据storages生产ZtreeBean对象 叶子节点是学校
	 * 叶子节点id用的是学校id，仓库id是st_{storage_id}，城市id是city_
	 * {city_id},RDC和LDC的id分别是RDC_1和ldc_1 ，根节点的id是root;
	 */
	@Override
	public ZtreeBean getCollegeZtree(List<Storage> storageList,boolean isAllChecked,long bgGoodsId) {
		List<Long> rdcCollegeIdList = goodsService.getRdcCollegeIds(bgGoodsId);
		List<Long> ldcCollegeIdList = goodsService.getLdcCollegeIds(bgGoodsId);
		List<Long> vmCollegeIdList = goodsService.getVmCollegeIds(bgGoodsId);
		ZtreeBean rootZtreeBean = null;
		ZtreeBean rdcZtreeBean = null;
		ZtreeBean ldcZtreeBean = null;
		ZtreeBean vmZtreeBean = null;
		List<ZtreeBean> ldcChildList = null;
		List<Storage> RDCStorageList = new ArrayList<Storage>();
		List<Storage> VMStorageList = new ArrayList<Storage>();
		Map<Long, List<Storage>> LDCStorageMap = new HashMap<Long, List<Storage>>();
		for (Storage stoage : storageList) {
			if (stoage.getStorageType() == Storage.RDC_STORAGE) {
				RDCStorageList.add(stoage);
			} else if(stoage.getStorageType() == Storage.LDC_STORAGE) {
				List<Storage> ldcCityStorageList = LDCStorageMap.get(stoage.getCityId());
				if (ldcCityStorageList == null) {
					ldcCityStorageList = new ArrayList<Storage>();
					LDCStorageMap.put(stoage.getCityId(), ldcCityStorageList);
				}
				ldcCityStorageList.add(stoage);
			}else if(stoage.getStorageType() == Storage.VM_STORAGE){
				VMStorageList.add(stoage);
			}
		}
		if (RDCStorageList.size() > 0) {
			rdcZtreeBean = new ZtreeBean("rdc_1", "root", Constants.RDC_STORAGE_NAME, 10,
					Constants.ICON_RDC);
			if(isAllChecked){
				rdcZtreeBean.setAllChecked(true);
				rdcZtreeBean.setChecked("true");
			}
			List<ZtreeBean> childBeanList = new ArrayList<ZtreeBean>();
			for (Storage storage : RDCStorageList) {
				ZtreeBean storageZtreeBean = new ZtreeBean("st_" + storage.getStorageId(), "rdc_1",
						storage.getStorageName(), 12, Constants.ICON_STORAGE);
				//仓默认关闭
				storageZtreeBean.setOpen("false");
				if(isAllChecked){
					storageZtreeBean.setAllChecked(true);
					storageZtreeBean.setChecked("true");
				}
				List<College> rdcCollegeList = collegeService.getListByRdcStorageId(storage
						.getStorageId());
				List<ZtreeBean> storageChildBeanList = new ArrayList<ZtreeBean>();
				for (College college : rdcCollegeList) {
					ZtreeBean collegeZtreeBean = new ZtreeBean(college.getCollegeId() + "", "st_"
							+ storage.getStorageId(), college.getCollegeName(), 13,
							Constants.ICON_COLLEGE);
					if(isAllChecked){
						collegeZtreeBean.setAllChecked(true);
						collegeZtreeBean.setChecked("true");
					}
					if (rdcCollegeIdList.contains(college.getCollegeId())) {
						collegeZtreeBean.setChecked("true");
						storageZtreeBean.setChecked("true");
						rdcZtreeBean.setChecked("true");
					}
					storageChildBeanList.add(collegeZtreeBean);
				}
				if (storageChildBeanList != null && storageChildBeanList.size() > 0) {
					storageZtreeBean.children = storageChildBeanList;
					childBeanList.add(storageZtreeBean);
				}
			}
			rdcZtreeBean.setChildren(childBeanList);
		}
		for (Map.Entry<Long, List<Storage>> entry : LDCStorageMap.entrySet()) {
			if (ldcZtreeBean == null) {
				ldcZtreeBean = new ZtreeBean("ldc_1", "root", Constants.LDC_STORAGE_NAME, 20,
						Constants.ICON_LDC);
				if(isAllChecked){
					ldcZtreeBean.setAllChecked(true);
					ldcZtreeBean.setChecked("true");
				}
				ldcChildList = new ArrayList<ZtreeBean>();
				ldcZtreeBean.children = ldcChildList;
			}
			City city = cityService.getCityById(entry.getKey());
			ZtreeBean cityBean = new ZtreeBean("city_" + city.getCityId(), "ldc_1", city.getCityName(), 21,
					Constants.ICON_STORAGE);
			if(isAllChecked){
				cityBean.setAllChecked(true);
				cityBean.setChecked("true");
			}
			List<ZtreeBean> cityChildList = new ArrayList<ZtreeBean>();
			for (Storage storage : entry.getValue()) {
				ZtreeBean storageBean = new ZtreeBean("st_" + storage.getStorageId(), "city_"
						+ city.getCityId(), storage.getStorageName(), 22,
						Constants.ICON_STORAGE);
				//仓默认关闭
				storageBean.setOpen("false");
				if(isAllChecked){
					storageBean.setAllChecked(true);
					storageBean.setChecked("true");
				}
				List<College> ldcCollegeList = collegeService.getListByLdcStorageId(storage
						.getStorageId());
				List<ZtreeBean> storageChildBeanList = new ArrayList<ZtreeBean>();
				for (College college : ldcCollegeList) {
					ZtreeBean collegeZtreeBean = new ZtreeBean(college.getCollegeId() + "", "st_"
							+ storage.getStorageId(), college.getCollegeName(), 23,
							Constants.ICON_COLLEGE);
					if(isAllChecked){
						collegeZtreeBean.setAllChecked(true);
						collegeZtreeBean.setChecked("true");
					}
					if (ldcCollegeIdList.contains(college.getCollegeId())) {
						collegeZtreeBean.setChecked("true");
						storageBean.setChecked("true");
						cityBean.setChecked("true");
						ldcZtreeBean.setChecked("true");
					}
					storageChildBeanList.add(collegeZtreeBean);
				}
				if (storageChildBeanList != null && storageChildBeanList.size() > 0) {
					storageBean.children = storageChildBeanList;
					cityChildList.add(storageBean);
				}
			}
			if (cityChildList != null && cityChildList.size() > 0) {
				cityBean.setChildren(cityChildList);
				ldcChildList.add(cityBean);
			}
		}
		if (VMStorageList.size() > 0) {
			vmZtreeBean = new ZtreeBean("vm_1", "root", Constants.VM_STORAGE_NAME, 30,
					Constants.ICON_RDC);
			if(isAllChecked){
				vmZtreeBean.setAllChecked(true);
				vmZtreeBean.setChecked("true");
			}
			List<ZtreeBean> childBeanList = new ArrayList<ZtreeBean>();
			for (Storage storage : VMStorageList) {
				ZtreeBean storageZtreeBean = new ZtreeBean("st_" + storage.getStorageId(), "vm_1",
						storage.getStorageName(), 32, Constants.ICON_STORAGE);
				//仓默认关闭
				storageZtreeBean.setOpen("false");
				if(isAllChecked){
					storageZtreeBean.setAllChecked(true);
					storageZtreeBean.setChecked("true");
				}
				List<Long> vmCollegeIdListTemp = storageCollegeService.getCollegeIdListByStorageId(storage.getStorageId());
				List<College> vmCollegeList = new ArrayList<>();
				for(Long collegeId : vmCollegeIdListTemp){
					College temp = CollegeConstant.getCollegeById(collegeId);
					if(temp != null){
						vmCollegeList.add(temp);
					}
				}
				List<ZtreeBean> storageChildBeanList = new ArrayList<ZtreeBean>();
				for (College college : vmCollegeList) {
					ZtreeBean collegeZtreeBean = new ZtreeBean(college.getCollegeId() + "", "st_"
							+ storage.getStorageId(), college.getCollegeName(), 33,
							Constants.ICON_COLLEGE);
					if(isAllChecked){
						collegeZtreeBean.setAllChecked(true);
						collegeZtreeBean.setChecked("true");
					}
					if (vmCollegeIdList.contains(college.getCollegeId())) {
						collegeZtreeBean.setChecked("true");
						storageZtreeBean.setChecked("true");
						vmZtreeBean.setChecked("true");
					}
					storageChildBeanList.add(collegeZtreeBean);
				}
				if (storageChildBeanList != null && storageChildBeanList.size() > 0) {
					storageZtreeBean.children = storageChildBeanList;
					childBeanList.add(storageZtreeBean);
				}
			}
			vmZtreeBean.setChildren(childBeanList);
		}
		
		
		if ((rdcZtreeBean != null && rdcZtreeBean.getChildren() != null && rdcZtreeBean.getChildren().size() > 0)
				|| (ldcZtreeBean != null && ldcZtreeBean.getChildren() != null && ldcZtreeBean
						.getChildren().size() > 0)|| (vmZtreeBean != null && vmZtreeBean.getChildren() != null && vmZtreeBean
								.getChildren().size() > 0)) {
			rootZtreeBean = new ZtreeBean("root", "", Constants.All_STORAGE_NAME, 0, "");
			if(isAllChecked){
				rootZtreeBean.setAllChecked(true);
				rootZtreeBean.setChecked("true");
			}
			List<ZtreeBean> rootChildList = new ArrayList<ZtreeBean>();
			if (rdcZtreeBean != null && rdcZtreeBean.getChildren() != null
					&& rdcZtreeBean.getChildren().size() > 0) {
				rootChildList.add(rdcZtreeBean);
				if(rdcZtreeBean.getChecked().equals("true")){
					rootZtreeBean.setChecked("true");
				}
			}
			if (ldcZtreeBean != null && ldcZtreeBean.getChildren() != null
					&& ldcZtreeBean.getChildren().size() > 0) {
				rootChildList.add(ldcZtreeBean);
				if(ldcZtreeBean.getChecked().equals("true")){
					rootZtreeBean.setChecked("true");
				}
			}
			if (vmZtreeBean != null && vmZtreeBean.getChildren() != null
					&& vmZtreeBean.getChildren().size() > 0) {
				rootChildList.add(vmZtreeBean);
				if(vmZtreeBean.getChecked().equals("true")){
					rootZtreeBean.setChecked("true");
				}
			}
			rootZtreeBean.setChildren(rootChildList);
		}
		return rootZtreeBean;

	}

	@Override
	public ZtreeBean getCollegeZtree(List<Long> rdcCollegeIdList, List<Long> ldcCollegeIdList,Long vmStorageId,List<Long> vmCollegeIdList,
			List<Long> selectedRdcCollegeIdList, List<Long> selectedLdcCollegeIdList,List<Long> selectedVmCollegeIdList) {
		// 都需要有根节点
		ZtreeBean rootZtreeBean = new ZtreeBean("root", "", Constants.All_STORAGE_NAME, 0, "");
		// 组合虚拟仓库Map
		Map<Long, List<College>> vmStorageCollegeMap = parseVmCollegeIdList(vmStorageId,vmCollegeIdList);
		// 组合RDC仓库Map
		Map<Long, List<College>> rdcStorageCollegeMap = parseRdcCollegeIdList(rdcCollegeIdList);
		// 组合LDC仓库Map
		Map<Long, Map<Long, List<College>>> LDCCityStorageCollegeMap = parseLdcCollegeIdList(ldcCollegeIdList);
		//生产rdc树
		ZtreeBean rdcZtreeBean = generateRdcTree(rdcStorageCollegeMap, selectedRdcCollegeIdList);
		//生产ldc树
		ZtreeBean ldcZtreeBean = generateLdcTree(LDCCityStorageCollegeMap, selectedLdcCollegeIdList);
		//生产虚拟树
		ZtreeBean vmZtreeBean = generateVmTree(vmStorageCollegeMap, selectedVmCollegeIdList);

		if (rdcZtreeBean != null || ldcZtreeBean != null || vmZtreeBean != null) {
			List<ZtreeBean> rootChildList = new ArrayList<ZtreeBean>();
			if (rdcZtreeBean != null && rdcZtreeBean.getChildren() != null
					&& rdcZtreeBean.getChildren().size() > 0) {
				rootChildList.add(rdcZtreeBean);
			}
			if (ldcZtreeBean != null && ldcZtreeBean.getChildren() != null
					&& ldcZtreeBean.getChildren().size() > 0) {
				rootChildList.add(ldcZtreeBean);
			}
			if (vmZtreeBean != null && vmZtreeBean.getChildren() != null
					&& vmZtreeBean.getChildren().size() > 0) {
				rootChildList.add(vmZtreeBean);
			}
			rootZtreeBean.setChildren(rootChildList);
			rootZtreeBean.setChecked("true");
		}
		return rootZtreeBean;
	}

	@Override
	public void setZtreeStatusByCollege(ZtreeBean ztreeBean, List<Long> rdcCollegeIdList,
			List<Long> ldcCollegeIdList) {

		if ((ztreeBean == null || rdcCollegeIdList == null || rdcCollegeIdList.size() == 0)
				&& (ldcCollegeIdList == null || ldcCollegeIdList.size() == 0)) {
			return;
		}

		List<ZtreeBean> allChildrenZtreeList = ztreeBean.getChildren();

		if (allChildrenZtreeList == null || allChildrenZtreeList.size() == 0) {
			return;
		}

		for (ZtreeBean childrenZtreen : allChildrenZtreeList) {

			if (Constants.RDC_STORAGE_NAME.equals(childrenZtreen.name)) {
				// RDC仓
				List<ZtreeBean> rdcStorageChildrenZtree = childrenZtreen.getChildren();
				if (rdcStorageChildrenZtree == null || rdcStorageChildrenZtree.size() == 0) {
					continue;
				}

				for (ZtreeBean storageBean : rdcStorageChildrenZtree) {
					List<ZtreeBean> rdcCollegeChildrenZtree = storageBean.getChildren();

					// 学校
					if (rdcCollegeChildrenZtree == null || rdcCollegeChildrenZtree.size() == 0) {
						continue;
					}

					// 实际操作
					for (ZtreeBean collegeBean : rdcCollegeChildrenZtree) {
						if (rdcCollegeIdList.contains(_.toLong(collegeBean.getId()))) {
							collegeBean.setChecked("true");
							storageBean.setChecked("true");
							childrenZtreen.setChecked("true");
							ztreeBean.setChecked("true");
						}
					}
				}
			} else if (Constants.LDC_STORAGE_NAME.equals(childrenZtreen.name)) {
				// LDC仓
				List<ZtreeBean> ldcStorageChildrenZtree = childrenZtreen.getChildren();
				if (ldcStorageChildrenZtree == null || ldcStorageChildrenZtree.size() == 0) {
					continue;
				}

				for (ZtreeBean cityBean : ldcStorageChildrenZtree) {
					List<ZtreeBean> storageChildrenZtree = cityBean.getChildren();
					// 学校-->仓
					if (storageChildrenZtree == null || storageChildrenZtree.size() == 0) {
						continue;
					}

					for (ZtreeBean storageBean : storageChildrenZtree) {
						List<ZtreeBean> ldcCollegeChildrenZtree = storageBean.getChildren();

						// 学校
						if (ldcCollegeChildrenZtree == null
								|| ldcCollegeChildrenZtree.size() == 0) {
							continue;
						}

						// 实际操作
						for (ZtreeBean collegeBean : ldcCollegeChildrenZtree) {
							if (ldcCollegeIdList.contains(_.toLong(collegeBean.getId()))) {
								collegeBean.setChecked("true");
								storageBean.setChecked("true");
								cityBean.setChecked("true");
								childrenZtreen.setChecked("true");
								ztreeBean.setChecked("true");
							}
						}
					}
				}
			}
		}

	}

	@Override
	public void setZtreeStatus(ZtreeBean ztreeBean, List<Long> storageIdList) {
		// // 获得该商品在那些仓有数据
		// List<StorageGoodsStock> storageGoodsList = storageList;
		// if (storageGoodsList == null || storageGoodsList.size() == 0)
		// {
		// return;
		// }
		// List<Long> wms_goods_id_list = new ArrayList<Long>();
		// for (StorageGoodsStock bean : storageGoodsList) {
		// // 所在仓是可用的。
		// if (bean.is_del == 0) {
		// wms_goods_id_list.add(bean.storage_id);
		// }
		// }
		if (storageIdList == null || storageIdList.size() == 0) {
			return;
		}
		List<ZtreeBean> allChildrenZtreeList = ztreeBean.getChildren();
		if (allChildrenZtreeList == null || allChildrenZtreeList.size() == 0) {
			return;
		}
		for (ZtreeBean childrenZtreen : allChildrenZtreeList) {
			boolean childChecked = false;
			if (Constants.RDC_STORAGE_NAME.equals(childrenZtreen.name)) {
				// RDC仓
				List<ZtreeBean> rdcStorageChildrenZtree = childrenZtreen.getChildren();
				if (rdcStorageChildrenZtree == null || rdcStorageChildrenZtree.size() == 0) {
					continue;
				}
				for (ZtreeBean bean : rdcStorageChildrenZtree) {
					if (storageIdList.contains(Long.parseLong(bean.id))) {
						bean.checked = "true";
						childrenZtreen.checked = "true";
						ztreeBean.checked = "true";
					}
				}

			} else if (Constants.LDC_STORAGE_NAME.equals(childrenZtreen.name)) {
				// RDC仓
				List<ZtreeBean> ldcStorageChildrenZtree = childrenZtreen.getChildren();
				if (ldcStorageChildrenZtree == null || ldcStorageChildrenZtree.size() == 0) {
					continue;
				}
				for (ZtreeBean citybean : ldcStorageChildrenZtree) {
					List<ZtreeBean> cityChildrenZtree = citybean.getChildren();
					if (cityChildrenZtree == null || cityChildrenZtree.size() == 0) {
						continue;
					}
					for (ZtreeBean storagebean : cityChildrenZtree) {
						if (storageIdList.contains(Long.parseLong(storagebean.id))) {
							storagebean.checked = "true";
							citybean.checked = "true";
							childrenZtreen.checked = "true";
							ztreeBean.checked = "true";
						}
					}
				}
			}else if (Constants.VM_STORAGE_NAME.equals(childrenZtreen.name)) {
				// RDC仓
				List<ZtreeBean> vmStorageChildrenZtree = childrenZtreen.getChildren();
				if (vmStorageChildrenZtree == null || vmStorageChildrenZtree.size() == 0) {
					continue;
				}
				for (ZtreeBean bean : vmStorageChildrenZtree) {
					if (storageIdList.contains(Long.parseLong(bean.id))) {
						bean.checked = "true";
						childrenZtreen.checked = "true";
						ztreeBean.checked = "true";
					}
				}
			}
		}
	}

	/**
	 * 根据storages生产ZtreeBean对象 叶子节点是学校
	 * 叶子节点id用的是学校id，仓库id是st_{storage_id}，城市id是city_
	 * {city_id},RDC和LDC的id分别是RDC_1和ldc_1 ，根节点的id是root;
	 */
	@Override
	public ZtreeBean getCollegeZtree(Map<Integer, CouponRegion> couponRegionMap,
			Map<Integer, CouponCollege> couponCollegesMap) {
		ZtreeBean rootZtreeBean = null;

		List<ZtreeBean> cityZtreeBeans = Lists.newArrayList();

		List<City> cities = cityService.getCityList();
		boolean isRootChecked = false;

		for (City city : cities) {

			List<College> colleges = collegeService.getListByCityId(city.getCityId());

			if (!_.isEmpty(colleges)) {
				boolean chooseAllCollege = (couponRegionMap != null && couponRegionMap.get(city
						.getCityId()) != null);
				boolean isCityChecked = false;

				ZtreeBean cityZtreeBean = new ZtreeBean("city_" + city.getCityId(), "root",
						city.getCityName(), 21, Constants.ICON_STORAGE);
				cityZtreeBean.setOpen("false");

				List<ZtreeBean> collegeZtreeBeans = Lists.newArrayList();
				// collegeZtreeBeans.add(new ZtreeBean(
				// city.getCity_id() + "_0", "city_" +
				// city.getCity_id(),
				// "全部学校", 13, Constants.ICON_COLLEGE));

				for (College college : colleges) {

					ZtreeBean collegeZtreeBean = new ZtreeBean(college.getCollegeId() + "", "city_"
							+ city.getCityId(), college.getCollegeName(), 13,
							Constants.ICON_COLLEGE);

					if (chooseAllCollege
							|| (couponCollegesMap != null && couponCollegesMap.get(_
									.toInt(college.getCollegeId() + "")) != null)) {
						collegeZtreeBean.setChecked("true");
						isCityChecked = true;
					}

					collegeZtreeBeans.add(collegeZtreeBean);
				}

				if (isCityChecked) {
					chooseAllCollege = true;
					cityZtreeBean.setChecked("true");
					isRootChecked = true;
				}

				cityZtreeBean.setChildren(collegeZtreeBeans);
				cityZtreeBeans.add(cityZtreeBean);
			}

		}

		rootZtreeBean = new ZtreeBean("root", "", Constants.All_STORAGE_NAME, 0, "");
		if (isRootChecked) {
			rootZtreeBean.setChecked("true");
		}
		rootZtreeBean.setChildren(cityZtreeBeans);
		return rootZtreeBean;
	}

	@Override
	public ZtreeBean getCollegeZtree4Activity(Map<Long, ActivityRegion> activityRegionMap,
			Map<Long, ActivityCollege> activityCollegesMap) {
		ZtreeBean rootZtreeBean = null;

		List<ZtreeBean> cityZtreeBeans = Lists.newArrayList();

		List<City> cities = cityService.getCityList();
		boolean isRootChecked = false;

		for (City city : cities) {

			List<College> colleges = collegeService.getListByCityId(city.getCityId());

			if (!_.isEmpty(colleges)) {
				boolean chooseAllCollege = (activityRegionMap != null && activityRegionMap.get(city
						.getCityId()) != null);
				boolean isCityChecked = false;

				ZtreeBean cityZtreeBean = new ZtreeBean("city_" + city.getCityId(), "root",
						city.getCityName(), 21, Constants.ICON_STORAGE);
				cityZtreeBean.setOpen("false");

				List<ZtreeBean> collegeZtreeBeans = Lists.newArrayList();
				// collegeZtreeBeans.add(new ZtreeBean(
				// city.getCity_id() + "_0", "city_" +
				// city.getCity_id(),
				// "全部学校", 13, Constants.ICON_COLLEGE));

				for (College college : colleges) {

					ZtreeBean collegeZtreeBean = new ZtreeBean(college.getCollegeId() + "", "city_"
							+ city.getCityId(), college.getCollegeName(), 13,
							Constants.ICON_COLLEGE);

					if (chooseAllCollege
							|| (activityCollegesMap != null && activityCollegesMap
									.get(college.getCollegeId()) != null)) {
						collegeZtreeBean.setChecked("true");
						isCityChecked = true;
					}

					collegeZtreeBeans.add(collegeZtreeBean);
				}

				if (isCityChecked) {
					cityZtreeBean.setChecked("true");
					isRootChecked = true;
				}

				cityZtreeBean.setChildren(collegeZtreeBeans);
				cityZtreeBeans.add(cityZtreeBean);
			}

		}

		rootZtreeBean = new ZtreeBean("root", "", Constants.All_STORAGE_NAME, 0, "");
		if (isRootChecked) {
			rootZtreeBean.setChecked("true");
		}
		rootZtreeBean.setChildren(cityZtreeBeans);
		return rootZtreeBean;
	}

	@Override
	/**
	 * 获取城市学校区域
	 * @author zhangshuai
	 */
	public ZtreeBean getCityCollegeZtree() {
		ZtreeBean rootZtreeBean = null;
		
		List<ZtreeBean> cityZtreeBeans = new ArrayList<>();
		
		List<City> cities = cityService.getCitiesByParams(null);
		boolean isRootChecked = false;
		
		for (City city : cities) {
			
			List<College> colleges = collegeService.getListByCityId(city.getCityId());
			
			if (!_.isEmpty(colleges)) {
				// boolean chooseAllCollege = (activityRegionMap
				// != null &&
				// activityRegionMap.get(city.getCityId()) !=
				// null);
				boolean isCityChecked = false;
				
				ZtreeBean cityZtreeBean = new ZtreeBean("city_" + city.getCityId(), "root",
						city.getCityName(), 21, Constants.ICON_STORAGE);
				cityZtreeBean.setOpen("true");
				
				List<ZtreeBean> collegeZtreeBeans = new ArrayList<>();
				
				for (College college : colleges) {
					
					ZtreeBean collegeZtreeBean = new ZtreeBean(college.getCollegeId() + "", "city_"
							+ city.getCityId(), college.getCollegeName(), 13,
							Constants.ICON_COLLEGE);
					collegeZtreeBeans.add(collegeZtreeBean);
				}
				
				if (isCityChecked) {
					cityZtreeBean.setChecked("true");
					isRootChecked = true;
				}
				
				cityZtreeBean.setChildren(collegeZtreeBeans);
				cityZtreeBeans.add(cityZtreeBean);
			}
			
		}
		
		rootZtreeBean = new ZtreeBean("root", "", Constants.All_CITY, 0, "");
		if (isRootChecked) {
			rootZtreeBean.setChecked("true");
		}
		rootZtreeBean.setChildren(cityZtreeBeans);
		return rootZtreeBean;
	}
	
	/**
	 * 构建城市学校树,并根据有勾选学校赋值
	 * @author zhangshuai
	 */
	@Override
	public ZtreeBean getCityCollegeZtree(List<Long> collegeIdList) {
		ZtreeBean rootZtreeBean = null;

		List<ZtreeBean> cityZtreeBeans = new ArrayList<>();

		List<City> cities = cityService.getCitiesByParams(null);
		boolean isRootChecked = false;

		for (City city : cities) {

			List<College> colleges = collegeService.getListByCityId(city.getCityId());

			if (!_.isEmpty(colleges)) {
				boolean isCityChecked = false;

				ZtreeBean cityZtreeBean = new ZtreeBean("city_" + city.getCityId(), "root",
						city.getCityName(), 21, Constants.ICON_STORAGE);
				cityZtreeBean.setOpen("true");

				List<ZtreeBean> collegeZtreeBeans = new ArrayList<>();

				for (College college : colleges) {

					ZtreeBean collegeZtreeBean = new ZtreeBean(college.getCollegeId() + "", "city_"
							+ city.getCityId(), college.getCollegeName(), 13,
							Constants.ICON_COLLEGE);
					if(collegeIdList.contains(college.getCollegeId())){
						collegeZtreeBean.setChecked("true");
						isCityChecked = true;
					}
					collegeZtreeBeans.add(collegeZtreeBean);
				}

				if (isCityChecked) {
					cityZtreeBean.setChecked("true");
					isRootChecked = true;
				}

				cityZtreeBean.setChildren(collegeZtreeBeans);
				cityZtreeBeans.add(cityZtreeBean);
			}

		}

		rootZtreeBean = new ZtreeBean("root", "", Constants.All_CITY, 0, "");
		if (isRootChecked) {
			rootZtreeBean.setChecked("true");
		}
		rootZtreeBean.setChildren(cityZtreeBeans);
		return rootZtreeBean;
	}

	/**
	 * 构造(市-区） 两级树
	 * 
	 * @return
	 */
	@Override
	public ZtreeBean getCityAreaZtree(List<Long> areaIdList, boolean editable) {

		Collection<City> citys = CityConstant.getcityMap().values();
		Map<Long, ZtreeBean> proviceZtreeBean = new HashMap<Long, ZtreeBean>();
		for (City city : citys) {
			if (city.getLevel() == 1L) {
				proviceZtreeBean.put(
						city.getCityId(),
						new ZtreeBean("province_" + city.getCityId(), "root", city
								.getCityName(), 13, "", null, areaIdList.contains(city
								.getCityId()), !editable));
			}
		}

		for (City city : citys) {
			if (city.getLevel() == 2L) {
				if(city.getUcId() == null) {
					proviceZtreeBean.get(city.getPid())
					.addChild(new ZtreeBean("city_" + city.getCityId(), "provice_"
							+ city.getPid(), city.getCityName(), 13, "", null,
							areaIdList.contains(city.getCityId()), true));
				} else {
					proviceZtreeBean.get(city.getPid())
						.addChild(new ZtreeBean("city_" + city.getCityId(), "provice_"
								+ city.getPid(), city.getCityName(), 13, "", null,
								areaIdList.contains(city.getCityId()), !editable));
				}
			}
		}

		ZtreeBean rootZtreeBean = new ZtreeBean("root", "", Constants.All_CITY, 0, "", "true");
		rootZtreeBean.setChildren(new ArrayList(proviceZtreeBean.values()));

		return rootZtreeBean;
	}
	
	
	/**
	 * 获取城市学校区域
	 * 叶节点是学校tb_college，其上节点是tb_city,其ID分别为province_加上Id和city_加上ID和college_加上ID
	 * 入参checkedColleges是选中的学校id
	 */
	@Override
	public ZtreeBean getCityAreaCollegeZtree(List<Long> checkedColleges) {
		
		Collection<City> citys = CityConstant.getcityMap().values();
		Map<Long, ZtreeBean> proviceZtreeBean = new HashMap<Long, ZtreeBean>();
		for (City city : citys) {
			if (city.getLevel() == 1L) {
				proviceZtreeBean.put(
						city.getCityId(),
						new ZtreeBean("province_" + city.getCityId(), "root", city
								.getCityName(), 13, "", null));
			}
		}

		for (City city : citys) {
			if (city.getLevel() == 2L) {
				
				ZtreeBean cityTreeBean = new ZtreeBean("city_" + city.getCityId(), "provice_" + city.getPid(), city.getCityName(), 13, "", Constants.ICON_STORAGE);
				
				//List<College> colleges = collegeService.getListByCityId(city.getCityId());
				List<College> colleges = CollegeConstant.getCollegesByAreaId(city.getCityId());
				if (!_.isEmpty(colleges)) {
					for(College college : colleges) {
						boolean checked = false;
						if(checkedColleges != null && checkedColleges.contains(college.getCollegeId())) {
							checked = true;
						}
						cityTreeBean.addChild(
							new ZtreeBean("college_" + college.getCollegeId(), "city_" + city.getCityId(), college.getCollegeName(), 13, "", Constants.ICON_COLLEGE, checked,false) 
						);
					}
					
				}
				
				proviceZtreeBean.get(city.getPid()).addChild(cityTreeBean);
			}
		}

		ZtreeBean rootZtreeBean = new ZtreeBean("root", "", Constants.All_CITY, 0, "", "true");
		rootZtreeBean.setChildren(new ArrayList(proviceZtreeBean.values()));
		
		return rootZtreeBean;
	}
	
	
	/**
	 * 获取城市学校区域
	 * 叶节点是学校tb_college，其上节点是tb_city,其ID分别为province_加上Id和city_加上ID和college_加上ID
	 * 入参checkedColleges是选中的学校id
	 * 去除无学校的节点
	 */
	@Override
	public ZtreeBean getCityAreaCollegeZtreeWithoutEmptyCity(List<Long> checkedColleges) {
		
		Collection<City> citys = CityConstant.getcityMap().values();
		Map<Long, ZtreeBean> proviceZtreeBean = new HashMap<Long, ZtreeBean>();
		for (City city : citys) {
			if (city.getLevel() == 1L) {
				proviceZtreeBean.put(
						city.getCityId(),
						new ZtreeBean("province_" + city.getCityId(), "root", city
								.getCityName(), 13, "", null));
			}
		}

		for (City city : citys) {
			if (city.getLevel() == 2L) {
				
				//List<College> colleges = collegeService.getListByCityId(city.getCityId());
				List<College> colleges = CollegeConstant.getCollegesByAreaId(city.getCityId());
				if (!_.isEmpty(colleges)) {
					ZtreeBean cityTreeBean = new ZtreeBean("city_" + city.getCityId(), "provice_" + city.getPid(), city.getCityName(), 13, "", Constants.ICON_STORAGE);
					for(College college : colleges) {
						boolean checked = false;
						if(checkedColleges != null && checkedColleges.contains(college.getCollegeId())) {
							checked = true;
						}
						cityTreeBean.addChild(
							new ZtreeBean("college_" + college.getCollegeId(), "city_" + city.getCityId(), college.getCollegeName(), 13, "", Constants.ICON_COLLEGE, checked,false) 
						);
					}
					proviceZtreeBean.get(city.getPid()).addChild(cityTreeBean);
				}
				
			}
		}
		
		for(Iterator<Map.Entry<Long, ZtreeBean>> it = proviceZtreeBean.entrySet().iterator();it.hasNext();) {
			
			ZtreeBean ztreeBean = it.next().getValue();
			if(CollectionUtils.isEmpty(ztreeBean.getChildren())) {
				it.remove();
			}
		}
		

		ZtreeBean rootZtreeBean = new ZtreeBean("root", "", Constants.All_CITY, 0, "");
		rootZtreeBean.setChildren(new ArrayList(proviceZtreeBean.values()));
		
		return rootZtreeBean;
	}
	
	private Map<Long, List<College>> parseVmCollegeIdList(Long storageId,List<Long> vmCollegeIdList){
		if(vmCollegeIdList == null || vmCollegeIdList.size() == 0 || storageId == null){
			return null;
		}
		Map<Long, List<College>> vmStorageCollegeMap = new HashMap<>();
		List<College> vmCollegeList = new ArrayList<>();
		for(Long collegeId : vmCollegeIdList){
			College college = CollegeConstant.getCollegeById(collegeId);
			if (college != null && college.getIsDel() == 0) {
				vmCollegeList.add(college);
			}
		}
		vmStorageCollegeMap.put(storageId, vmCollegeList);
		return vmStorageCollegeMap;
	}
	private Map<Long, List<College>> parseRdcCollegeIdList(List<Long> rdcCollegeIdList){
		if(rdcCollegeIdList == null || rdcCollegeIdList.size() == 0){
			return null;
		}
		Map<Long, List<College>> rdcStorageCollegeMap = new HashMap<>();
		for (long collegeId : rdcCollegeIdList) {
			College college = CollegeConstant.getCollegeById(collegeId);
			if (college != null && college.getIsDel() == 0) {
				Storage rdcStorage = StroageConstant.getRdcStorageById(college.getRdcStorageId());
				if (rdcStorage != null && rdcStorage.getIsClose() == 0 && rdcStorage.getIsDel() == 0) {
					if(rdcStorageCollegeMap.containsKey(rdcStorage.getStorageId())){
						rdcStorageCollegeMap.get(rdcStorage.getStorageId()).add(college);
					}else{
						List<College> collegeListTemp = new ArrayList<>();
						collegeListTemp.add(college);
						rdcStorageCollegeMap.put(rdcStorage.getStorageId(), collegeListTemp);
					}
				}
			}
		}
		return rdcStorageCollegeMap;
	}
	
	private Map<Long, Map<Long, List<College>>> parseLdcCollegeIdList(List<Long> ldcCollegeIdList){
		if(ldcCollegeIdList == null || ldcCollegeIdList.size() == 0){
			return null;
		}
		Map<Long, Map<Long, List<College>>> LDCCityStorageCollegeMap = new HashMap<>();
		// 将所有的ldc仓添加到map中 map的key值是城市id。
		for (long collegeId : ldcCollegeIdList) {
			College college = CollegeConstant.getCollegeById(collegeId);
			if (college != null && college.getIsDel() == 0) {
				Storage ldcStorage = StroageConstant.getLdcStorageById(college.getLdcStorageId());
				if (ldcStorage != null && ldcStorage.getIsClose() == 0 && ldcStorage.getIsDel() == 0) {
					// 如果学校对应的仓库已存在，则不再添加
					if(LDCCityStorageCollegeMap.containsKey(ldcStorage.getCityId())){
						if(LDCCityStorageCollegeMap.get(ldcStorage.getCityId()).containsKey(ldcStorage.getStorageId())){
							LDCCityStorageCollegeMap.get(ldcStorage.getCityId()).get(ldcStorage.getStorageId()).add(college);
						}else{
							List<College> collegeListTemp = new ArrayList<>();
							collegeListTemp.add(college);
							LDCCityStorageCollegeMap.get(ldcStorage.getCityId()).put(ldcStorage.getStorageId(), collegeListTemp);
						}
					}else{
						Map<Long, List<College>> storageMapTemp = new HashMap<>();
						List<College> collegeListTemp = new ArrayList<>();
						collegeListTemp.add(college);
						storageMapTemp.put(ldcStorage.getStorageId(), collegeListTemp);
						LDCCityStorageCollegeMap.put(ldcStorage.getCityId(), storageMapTemp);
					}
				}
			}
		}
		return LDCCityStorageCollegeMap;
	}
	
	private ZtreeBean generateVmTree(Map<Long, List<College>> vmStorageCollegeMap,List<Long> selectedCollegeIdList){
		ZtreeBean vmZtreeBean = null;
		if (vmStorageCollegeMap != null && !vmStorageCollegeMap.isEmpty()) {
			// 添加根节点
			vmZtreeBean = new ZtreeBean("vm_3", "root", Constants.VM_STORAGE_NAME, 10,
					Constants.ICON_RDC);
			// 根节点的子结点
			List<ZtreeBean> childBeanList = new ArrayList<ZtreeBean>();
			for (Long storageId : vmStorageCollegeMap.keySet()) {
				Storage storage = StroageConstant.getStorageById(storageId);
				if(storage != null){
					ZtreeBean storageZtreeBean = new ZtreeBean("st_" + storage.getStorageId(), "vm_3",
							storage.getStorageName(), 32, Constants.ICON_STORAGE);
					//仓默认关闭
					storageZtreeBean.setOpen("false");
					List<College> vmCollegeList = vmStorageCollegeMap.get(storageId);
					// 把可售卖的学校添加到叶子节点上
					if(vmCollegeList != null && vmCollegeList.size() > 0){
						List<ZtreeBean> storageChildBeanList = new ArrayList<ZtreeBean>();
						for (College college : vmCollegeList) {
							ZtreeBean collegeZtreeBean = new ZtreeBean(college.getCollegeId() + "", "st_"
									+ storage.getStorageId(), college.getCollegeName(), 33,
									Constants.ICON_COLLEGE);
							if(selectedCollegeIdList != null && selectedCollegeIdList.contains(college.getCollegeId())){
								collegeZtreeBean.setChecked("true");
								storageZtreeBean.setChecked("true");
								vmZtreeBean.setChecked("true");
							}
							storageChildBeanList.add(collegeZtreeBean);
						}
						// 把仓的学校添加到仓下，同时把仓添加到仓根节点下
						if (storageChildBeanList.size() > 0) {
							storageZtreeBean.children = storageChildBeanList;
							childBeanList.add(storageZtreeBean);
						}
					}
				}
			}
			// 仓根节点设置子节点
			if (childBeanList.size() > 0) {
				vmZtreeBean.setChildren(childBeanList);
			}
		}
		return vmZtreeBean;
	}
	
	private ZtreeBean generateRdcTree(Map<Long, List<College>> rdcStorageCollegeMap,List<Long> selectedCollegeIdList){
		ZtreeBean rdcZtreeBean = null;
		if (rdcStorageCollegeMap != null && !rdcStorageCollegeMap.isEmpty()) {
			// 添加rdc根节点
			rdcZtreeBean = new ZtreeBean("rdc_1", "root", Constants.RDC_STORAGE_NAME, 10,
					Constants.ICON_RDC);
			// rdc根节点的子结点
			List<ZtreeBean> childBeanList = new ArrayList<ZtreeBean>();
			for (Long storageId : rdcStorageCollegeMap.keySet()) {
				Storage storage = StroageConstant.getStorageById(storageId);
				if(storage != null){
					ZtreeBean storageZtreeBean = new ZtreeBean("st_" + storage.getStorageId(), "rdc_1",
							storage.getStorageName(), 12, Constants.ICON_STORAGE);
					//仓默认关闭
					storageZtreeBean.setOpen("false");
					List<College> rdcCollegeList = rdcStorageCollegeMap.get(storageId);
					// 把可售卖的学校添加到叶子节点上
					if(rdcCollegeList != null && rdcCollegeList.size() > 0){
						List<ZtreeBean> storageChildBeanList = new ArrayList<ZtreeBean>();
						for (College college : rdcCollegeList) {
							ZtreeBean collegeZtreeBean = new ZtreeBean(college.getCollegeId() + "", "st_"
									+ storage.getStorageId(), college.getCollegeName(), 13,
									Constants.ICON_COLLEGE);
							if(selectedCollegeIdList != null && selectedCollegeIdList.contains(college.getCollegeId())){
								collegeZtreeBean.setChecked("true");
								storageZtreeBean.setChecked("true");
								rdcZtreeBean.setChecked("true");
							}
							storageChildBeanList.add(collegeZtreeBean);
						}
						// 把rdc仓的学校添加到rdc仓下，同时把rdc仓添加到rdc仓根节点下
						if (storageChildBeanList.size() > 0) {
							storageZtreeBean.children = storageChildBeanList;
							childBeanList.add(storageZtreeBean);
						}
					}
				}
			}
			// rdc仓根节点设置子节点
			if (childBeanList.size() > 0) {
				rdcZtreeBean.setChildren(childBeanList);
			}
		}
		return rdcZtreeBean;
	}
	
	private ZtreeBean generateLdcTree(Map<Long, Map<Long, List<College>>> LDCCityStorageCollegeMap,List<Long> selectedCollegeIdList){
		ZtreeBean ldcZtreeBean = null;
		if(LDCCityStorageCollegeMap == null){
			return null;
		}
		// ldc仓的根节点
		ldcZtreeBean = new ZtreeBean("ldc_1", "root", Constants.LDC_STORAGE_NAME, 20,
				Constants.ICON_LDC);
		List<ZtreeBean> ldcChildList = new ArrayList<ZtreeBean>();
		for (Long cityId : LDCCityStorageCollegeMap.keySet()) {
			// 获取城市
			City city = CityConstant.getcityById(cityId);
			if (city == null || city.getIsDel() != 0) {
				continue;
			}
			// 创建城市节点
			ZtreeBean cityBean = new ZtreeBean("city_" + city.getCityId(), "ldc_1", city.getCityName(), 21,
					Constants.ICON_STORAGE);
			Map<Long, List<College>> storageCollegeMap = LDCCityStorageCollegeMap.get(cityId);
			if(storageCollegeMap == null || storageCollegeMap.isEmpty()){
				continue;
			}
			List<ZtreeBean> cityChildList = new ArrayList<ZtreeBean>();
			for (Long storageId : storageCollegeMap.keySet()) {
				Storage storage = StroageConstant.getStorageById(storageId);
				if(storage != null){
					ZtreeBean storageZtreeBean = new ZtreeBean("st_" + storage.getStorageId(), "city_"
							+ city.getCityId(), storage.getStorageName(), 22,
							Constants.ICON_STORAGE);
					//仓默认关闭
					storageZtreeBean.setOpen("false");
					// 把商品可售卖的学校添加到ldcCollegeList中
					List<College> ldcCollegeList = storageCollegeMap.get(storageId);
					// 把学校节点添加到ldc仓下
					if(ldcCollegeList != null && ldcCollegeList.size() > 0){
						List<ZtreeBean> storageChildBeanList = new ArrayList<ZtreeBean>();
						for (College college : ldcCollegeList) {
							ZtreeBean collegeZtreeBean = new ZtreeBean(college.getCollegeId() + "", "st_"
									+ storage.getStorageId(), college.getCollegeName(), 23,
									Constants.ICON_COLLEGE);
							if(selectedCollegeIdList != null && selectedCollegeIdList.contains(college.getCollegeId())){
								collegeZtreeBean.setChecked("true");
								storageZtreeBean.setChecked("true");
								ldcZtreeBean.setChecked("true");
							}
							storageChildBeanList.add(collegeZtreeBean);
						}
						if (storageChildBeanList.size() > 0) {
							storageZtreeBean.setChildren(storageChildBeanList);
							cityChildList.add(storageZtreeBean);
						}
					}
				}
			}
			if (cityChildList.size() > 0) {
				cityBean.setChildren(cityChildList);
				ldcChildList.add(cityBean);
			}
		}
		if (ldcChildList.size() > 0) {
			ldcZtreeBean.setChildren(ldcChildList);
		}
		return ldcZtreeBean;
	}
}
