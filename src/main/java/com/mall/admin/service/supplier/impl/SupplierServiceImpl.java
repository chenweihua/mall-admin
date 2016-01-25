package com.mall.admin.service.supplier.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.admin.base._;
import com.mall.admin.constant.LogConstant;
import com.mall.admin.model.dao.supplier.SupplierDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.service.activity.ActivityService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.service.supplier.SupplierService;
import com.mall.admin.vo.activity.Activity;
import com.mall.admin.vo.activity.ActivityCollege;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.supplier.Suppiler;


@Service
@Transactional(rollbackFor = Exception.class)
public class SupplierServiceImpl implements SupplierService{
	private static final Logger LOGGER = LogConstant.mallLog;
	@Autowired
	private SupplierDao supplierDao;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private CollegeService collegeService;
	
	private static final String[] dateTimeFormat =  {"yyyy-MM-dd HH:mm:ss"};

	@Override
	public List<Suppiler> getPageSupplier(PaginationInfo paginationInfo) {
		return supplierDao.getPageSuppiler(paginationInfo);
	}

	@Override
	public boolean addSupplier(Suppiler supplier) throws Exception {
		boolean result = false;
		int storageId = setStorage(supplier);
		supplier.setStorageId(storageId);
		supplierDao.addSuppiler(supplier);
		result = setActivity(supplier);
		return result;
	}
	
	/**
	 * 设置storeage
	 * @param supplier
	 * @return
	 * @throws Exception 
	 */
	private int setStorage(Suppiler supplier) throws Exception {
		int storageId = 0;
		try {
			Storage storage = new Storage();
			storage.setStorageName(supplier.getShopName());
			storage.setStorageType(Storage.VM_STORAGE);
			storage.setVmStoreId(0);
			storage.setVmCollegeId(0);
			storage.setCityId(0);
			storage.setLdcType(Storage.HIDE_LDC_TIME);//1:商超隐藏29分钟达 0:显示29分钟达
			storage.setPushType(Storage.pushToWms);
			storage.setOperatorId(supplier.getOperateId());
			storage.setIsClose(0);
			storage.setIsDel(0);
			storage.setFreight(-1);
			storage.setFreightSub(-1);
			storage.setFreightType(-1);
			storageService.addStorage(storage);
			storageId = (int)storage.getStorageId();
		} catch (Exception e) {
			LOGGER.error("插入storage异常");
			throw new Exception("插入storage异常");
		}
		return storageId;
	}
	
	/**
	 * 设置活动
	 * @param supplier
	 * @return
	 * @throws Exception
	 */
	private boolean setActivity(Suppiler supplier) throws Exception {
		boolean result = false;
		try {
			int isOpen = supplier.getShopIsOpen();
			Activity activity = new Activity();
			activity.setStorageId(supplier.getStorageId());
			activity.setOperator((long)supplier.getOperateId());
			activity.setActivityName(supplier.getShopName());
			activity.setActivityShowName(supplier.getShopName());
			activity.setActivityType((byte) Activity.ACTIVITY_TYPE_SUPPLIER);
			activity.setPlatformType((byte) Activity.ACTIVITY_PLANTFORM_TYPE_ALL);
			activity.setDisplayType(Activity.ACTIVITY_DISPLAY_TYPE);
			activity.setOpenType((byte) 0);
			activity.setActionType((byte) 0);
			activity.setWeight(1);
			activity.setImageUrl(supplier.getImageUrl());
			activity.setWapImageUrl(supplier.getImageUrl());
			activity.setWapImageUrl(supplier.getImageUrl());
			activity.setIsShow((byte) isOpen);
			activity.setIsOpen((byte) isOpen);
			activity.setCreateTime(new Date());
			activity.setUpdateTime(new Date());
			activity.setProgramType((byte) 3);
			
			activityService.insertActivity(activity);
			
//			ActivityTime activityTime = new ActivityTime();
//			activityTime.setActivityId(activity.getActivityId());
//			activityTime.setBeginTime(DateUtils.parseDate("2015-12-15 00:00:00", dateTimeFormat));
//			activityTime.setEndTime(DateUtils.parseDate("2025-12-15 00:00:00", dateTimeFormat));
//			activityTime.setCreateTime(new Date());
//			activityTime.setUpdateTime(new Date());
//			activityTime.setIsdel((byte) 0);
//			activityService.insertActivityTime(activityTime);
			
			List<College> colleges = collegeService.getAllCollege();
			if (!_.isEmpty(colleges)) {
				for (College college : colleges) {
					ActivityCollege activityCollege = ActivityCollege.getBean();
					activityCollege.setActivityId(activity.getActivityId());
					activityCollege.setCollegeId(college.getCollegeId());
					activityCollege.setOperator(supplier.getOperateId());
					activityService.insertActivityCollege(activityCollege);
				}
			}
			result = true;
		} catch (Exception e) {
			LOGGER.error("插入活动异常", e);
			throw new Exception("插入活动异常");
		}
		return result;
	}

	@Override
	public boolean updateSupplier(Suppiler supplier) throws Exception{
		boolean result = false;
		Activity activity = activityService.getActivityByStorageId(supplier.getStorageId());
		if(activity == null) {
			return result;
		}
		activity.setImageUrl(supplier.getImageUrl());
		activity.setWapImageUrl(supplier.getImageUrl());
		activity.setUpdateTime(new Date());
		activity.setOperator((long)supplier.getOperateId());
		if(Suppiler.SHOP_ISOPEN == supplier.getShopIsOpen()) {
			activity.setIsOpen((byte) 0);//开启
		}
		else {
			activity.setIsOpen((byte) 1);//关闭	
		}
		activityService.updateActivity(activity);
		supplierDao.updateSuppiler(supplier);
		result = true;
		
		return result;
	}
	
	@Override
	public boolean updateSuppilerStatus(Suppiler supplier) {
		boolean result = false;
		Suppiler supp = getSupplierById(supplier.getShopId());
		if(supp == null) {
			return result;
		}
		Activity activity = activityService.getActivityByStorageId(supp.getStorageId());
		if(activity == null) {
			return result;
		}
		activity.setUpdateTime(new Date());
		if(Suppiler.SHOP_ISOPEN == supplier.getShopIsOpen()) {
			activity.setIsOpen((byte) 0);//开启
		}
		else {
			activity.setIsOpen((byte) 1);//关闭	
		}
		activityService.updateActivity(activity);
		supplierDao.updateSuppilerStatus(supplier);
		result = true;
		
		return result;
	}
	
	@Override
	public long getCount(){
		return supplierDao.getCount();
	}

	@Override
	public Pair<Long,PaginationList<Suppiler>> getPageSupplier(PaginationInfo paginationInfo,
			Map<String, Object> map) {
		return supplierDao.getPageSuppiler(paginationInfo, map);
	}
	
	@Override
	public Suppiler getSupplierById(long id) {
		return supplierDao.getSuppilerById(id);
	}
	
	@Override
	public Suppiler getSupplierByStorageId(long storageId) {
		return supplierDao.getSupplierByStorageId(storageId);
	}

}
