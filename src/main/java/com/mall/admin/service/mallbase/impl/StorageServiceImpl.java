package com.mall.admin.service.mallbase.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.base._;
import com.mall.admin.constant.Constants;
import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.dao.mallbase.StorageDao;
import com.mall.admin.model.dao.storage.StorageCollegeDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.service.base.BaseServiceImpl;
import com.mall.admin.service.mallbase.CityService;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.mallbase.dto.StorageDto;
import com.mall.admin.vo.storage.StorageCollege;
import com.mall.admin.vo.user.User;

@Service
public class StorageServiceImpl extends BaseServiceImpl implements
		StorageService {

	@Autowired
	private StorageDao storageDao;

	@Autowired
	private CityService cityService;

	@Autowired
	private CollegeService collegeService;
	@Autowired
	private StorageCollegeDao storageCollegeDao;

	@Override
	public List<Storage> getAllRdcStorage() {
		return storageDao.getListByStorageType(Storage.RDC_STORAGE);
	}

	@Override
	public List<Storage> getRdcStorageByUserId(long userId) {
		return storageDao.getListByStorageTypeAndUserId(Storage.RDC_STORAGE, userId);
	}

	@Override
	public List<Storage> getAllLdcStorage() {
		return storageDao.getListByStorageType(Storage.LDC_STORAGE);
	}

	@Override
	public List<Storage> getLdcStorageByUserId(long userId) {
		return storageDao.getListByStorageTypeAndUserId(Storage.LDC_STORAGE, userId);
	}

	@Override
	public List<Storage> getAllStorageByIds(String ids) {
		return storageDao.getStoragesByIds(ids);
	}
	
	@Override
	public List<Storage> getLdcStorageByCityId(long cityId) {
		return storageDao.getListByStorageTypeAndCityId(Storage.LDC_STORAGE, cityId);
	}

	@Override
	public List<Storage> getLdcStorageByUserIdAndCityId(long userId, long cityId) {
		return storageDao.getLDCStorageListByUserIdAndCityId(userId, cityId);
	}

	/**
	 * @author gaozhou
	 */

	@Override
	public Pair<Long, PaginationList<Storage>> getPageStorage(
			PaginationInfo paginationInfo, Map paramMap) {
		return storageDao.getPageStorage(paginationInfo, paramMap);
	}

	@Override
	public long addStorage(Storage storage) {
		return storageDao.addStorage(storage);
	}

	@Override
	public long updateStorage(Storage storage) {
		return storageDao.updateStorage(storage);
	}

	@Override
	public long getCount() {
		return storageDao.getCount();
	}

	@Override
	public Storage getStorageById(long storageId) {

		return storageDao.getStorageById(storageId);
	}

	@Override
	public List<StorageDto> getRdcStorages(User user, List<Long> rdcStorageIds) {
		List<Storage> rdcStorages = user.getRdcStorageList();
		List<StorageDto> list = new ArrayList<>();
		for (Storage storage : rdcStorages) {
			if (rdcStorageIds.contains(storage.getStorageId())) {
				StorageDto storageDto = new StorageDto();
				storageDto.setStorage(storage);
				list.add(storageDto);
			}
		}
		return list;
	}

	@Override
	public List<StorageDto> getLdcStorages(User user, List<Long> ldcStorageIds) {
		List<Storage> rdcStorages = user.getLdcStorageList();
		List<StorageDto> list = new ArrayList<>();
		for (Storage storage : rdcStorages) {
			if (ldcStorageIds.contains(storage.getStorageId())) {
				StorageDto storageDto = new StorageDto();
				storageDto.setStorage(storage);
				list.add(storageDto);
			}
		}
		return list;
	}
	
	@Override
	public List<StorageDto> getVmStorages(User user, List<Long> vmStorageIds) {
		List<Storage> rdcStorages = user.getVmStorageList();
		List<StorageDto> list = new ArrayList<>();
		for (Storage storage : rdcStorages) {
			if (vmStorageIds.contains(storage.getStorageId())) {
				StorageDto storageDto = new StorageDto();
				storageDto.setStorage(storage);
				list.add(storageDto);
			}
		}
		return list;
	}
	
	@Override
	public List<Storage> getAllStorage() {
		/*List<Storage> rdc = this.getAllRdcStorage();
		List<Storage> ldc = this.getAllLdcStorage();
		List<Storage> all = Lists.newArrayList();
		if(!_.isEmpty(rdc)) {
			all.addAll(rdc);
		}
		if(!_.isEmpty(ldc)) {
			all.addAll(ldc);
		}
		return all;*/
		
		return storageDao.getAllStorage();
	}

	@Override
	public int deleteStorage(long storageId) {
		// TODO Auto-generated method stub
		return storageDao.deleteStorage(storageId);
	}

	@Override
	public Pair<Long, PaginationList<Storage>> getPageStorageByUser(
			PaginationInfo paginationInfo, Map paramMap) {
		// TODO Auto-generated method stub
		return storageDao.getPageStorageByUser(paginationInfo, paramMap);
	}

	@Override
	public Map<String, Object> setVMRegion(Long storageId, ZtreeBean ztreeBean) {
		StringBuffer msg = new StringBuffer();
		//删除仓库所有范围
		storageCollegeDao.deleteByStorageId(storageId);
		//设置新范围
		List<ZtreeBean> allChildrenZtreeList = ztreeBean.getChildren();
		if (allChildrenZtreeList == null || allChildrenZtreeList.size() == 0) {
			return buildSuccObj("没有勾选学校！");
		}

		for (ZtreeBean cityBean : allChildrenZtreeList) {
			List<ZtreeBean> collegeChildrenZtree = cityBean.getChildren();
			// 学校-->仓
			if (collegeChildrenZtree == null || collegeChildrenZtree.size() == 0) {
				continue;
			}
			// 实际操作
			for (ZtreeBean collegeBean : collegeChildrenZtree) {
				// 已被选中
				if (Constants.ZTREECHECKED.equals(collegeBean.checked)) {
					// 插入范围
					StorageCollege storageCollege = new StorageCollege();
					storageCollege.setCollegeId(_.toLong(collegeBean.getId()));
					storageCollege.setStorageId(storageId);
					long result = storageCollegeDao.insert(storageCollege);
					if(result < 0){
						msg.append(collegeBean.getName() + ",设置失败/n");
					}
				}
			}
		}
		if(msg.length() == 0){
			return buildSuccObj("范围设置成功！");
		}else{
			return buildErrObj(msg.toString());
		}
	}

	@Override
	public List<Storage> getVMStorage() {
		return storageDao.getListByStorageType(Storage.VM_STORAGE);
	}

	@Override
	public List<Storage> getVMStorageByUserId(long userId) {
		return storageDao.getListByStorageTypeAndUserId(Storage.VM_STORAGE, userId);
	}
}
