package com.mall.admin.service.mallbase;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.bean.ztree.ZtreeBean;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.mallbase.dto.StorageDto;
import com.mall.admin.vo.user.User;

public interface StorageService {

	/**
	 * 获得所有的RDC仓库
	 * 
	 * @return
	 */
	public List<Storage> getAllRdcStorage();

	/**
	 * 根据ids获取所有仓库
	 * 
	 * @param ids
	 * @return
	 */
	public List<Storage> getAllStorageByIds(String ids);

	/**
	 * 获得用户关联的rdc仓库
	 * 
	 * @param userId
	 * @return
	 */
	public List<Storage> getRdcStorageByUserId(long userId);

	/**
	 * 获得所有的LDC仓库
	 * 
	 * @return
	 */
	public List<Storage> getAllLdcStorage();
	
	/**
	 * 获得用户关联的LDC仓库
	 * 
	 * @param userId
	 * @return
	 */
	public List<Storage> getLdcStorageByUserId(long userId);

	/**
	 * 获得城市下的LDC仓库
	 * 
	 * @param cityId
	 * @return
	 */
	public List<Storage> getLdcStorageByCityId(long cityId);

	/**
	 * 获得城市下用户负责的仓库
	 * 
	 * @param userId
	 * @param cityId
	 * @return
	 */
	public List<Storage> getLdcStorageByUserIdAndCityId(long userId, long cityId);

	/**
	 * @author gaozhou
	 * @param paginationInfo
	 * @param map
	 * @return
	 */
	public Pair<Long, PaginationList<Storage>> getPageStorage(
			PaginationInfo paginationInfo, Map paramMap);
	
	public Pair<Long, PaginationList<Storage>> getPageStorageByUser(
			PaginationInfo paginationInfo, Map paramMap);

	public long addStorage(Storage storage);

	public long updateStorage(Storage storage);

	public long getCount();

	public Storage getStorageById(long storageId);

	public List<StorageDto> getRdcStorages(User user, List<Long> rdcStorageIds);
	
	public List<StorageDto> getLdcStorages(User user, List<Long> ldcStorageIds);
	
	public List<StorageDto> getVmStorages(User user, List<Long> vmStorageIds);

	// public ZtreeBean getStorageZtree(List<Storage> storageList);
	//
	// public ZtreeBean getCollegeZtree(List<Storage> storageList);

	/**
	 * 设置而显示结果
	 * 
	 * @param ztreeBean
	 * @param storageList
	 */
	// public void setZtreeStatus(ZtreeBean ztreeBean, List<Long>
	// storageIdList);
	
	public List<Storage> getAllStorage();

	public int deleteStorage(long storageId);
	
	public Map<String, Object> setVMRegion(Long storageId,ZtreeBean ztreeBean);

	public List<Storage> getVMStorage();
	
	public List<Storage> getVMStorageByUserId(long userId);
	
	
}
