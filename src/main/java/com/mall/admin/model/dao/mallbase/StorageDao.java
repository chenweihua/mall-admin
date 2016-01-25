package com.mall.admin.model.dao.mallbase;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.mallbase.Storage;

public interface StorageDao {

	/**
	 * 根据,分割的ids的获取所有仓库
	 * 
	 * @param userId
	 * @return
	 */
	public List<Storage> getStoragesByIds(String ids);

	/**
	 * 查询该城市下该用户具有的仓
	 * 
	 * @param userId
	 * @param cityId
	 * @return
	 */
	public List<Storage> getLDCStorageListByUserIdAndCityId(long userId,
			long cityId);

	/**
	 * @author gaozhou
	 */

	public List<Storage> getAllStorage();

	public List<Storage> getPageStorage(PaginationInfo paginationInfo);

	public long addStorage(Storage storage);

	public long updateStorage(Storage storage);

	public Pair<Long, PaginationList<Storage>> getPageStorage(
			PaginationInfo paginationInfo, Map paramMap);

	public long getCount();

	public Storage getStorageById(long storageId);

	public int deleteStorage(long storageId);

	public Pair<Long, PaginationList<Storage>> getPageStorageByUser(
			PaginationInfo paginationInfo, Map paramMap);
	
	public List<Storage> getListByStorageType(int type);
	
	public List<Storage> getListByStorageTypeAndUserId(int type,long userId);
	
	public List<Storage> getListByStorageTypeAndCityId(int type,long cityId);
}
