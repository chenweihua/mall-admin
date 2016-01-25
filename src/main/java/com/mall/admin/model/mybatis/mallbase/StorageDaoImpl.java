package com.mall.admin.model.mybatis.mallbase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.mallbase.StorageDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.mallbase.Storage;

@Repository
public class StorageDaoImpl extends BaseMallDaoImpl implements StorageDao {

	@Override
	public List<Storage> getLDCStorageListByUserIdAndCityId(long userId,
			long cityId) {
		// TODO Auto-generated method stub
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("user_id", userId);
		map.put("city_id", cityId);
		return this.getSqlSession().selectList(
				"Storage.getLdcStorageByUserIdAndCityId", map);
	}

	/**
	 * @author gaozhou
	 */
	@Override
	public List<Storage> getAllStorage() {
		return this.getSqlSession().selectList("Storage.getAllStorage");
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Storage> getPageStorage(PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<Storage> storageList = selectPaginationList(
				"Storage.getPageStorageByPage", paginationInfo);
		return storageList;
	}

	@Override
	public long addStorage(Storage storage) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("Storage.addStorage", storage);
	}

	@Override
	public long updateStorage(Storage storage) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("Storage.updateStorage", storage);
	}

	@Override
	public Pair<Long, PaginationList<Storage>> getPageStorage(
			PaginationInfo paginationInfo, Map paramMap) {
		long totalCount = this.getSqlSession().selectOne(
				"Storage.getCountByParams", paramMap);
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<Storage> storageList = selectPaginationList(
				"Storage.getPageStorageByPage", paramMap, paginationInfo);
		return Pair.of(totalCount, storageList);
	}

	@Override
	public long getCount() {
		return this.getSqlSession().selectOne("Storage.getCount");
	}

	@Override
	public Storage getStorageById(long storageId) {
		return this.getSqlSession().selectOne("Storage.getStorageById",
				storageId);
	}

	@Override
	public List<Storage> getStoragesByIds(String ids) {
		Map<String, Object> map = new HashMap<>();
		map.put("ids", ids);
		return this.getSqlSession().selectList("Storage.getStoragesByIds", map);
	}

	@Override
	public int deleteStorage(long storageId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("Storage.deleteStorage", storageId);
	}

	@Override
	public Pair<Long, PaginationList<Storage>> getPageStorageByUser(
			PaginationInfo paginationInfo, Map paramMap) {
		long totalCount = this.getSqlSession().selectOne(
				"Storage.getCountByUser", paramMap);
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<Storage> storageList = selectPaginationList(
				"Storage.getPageStorageByPageAndUser", paramMap, paginationInfo);
		return Pair.of(totalCount, storageList);
	}

	@Override
	public List<Storage> getListByStorageType(int type) {
		return this.getSqlSession().selectList("Storage.getListByStorageType", type);
	}

	@Override
	public List<Storage> getListByStorageTypeAndUserId(int type, long userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("type", type);
		params.put("userId", userId);
		return this.getSqlSession().selectList("Storage.getListByStorageTypeAndUserId", params);
	}

	@Override
	public List<Storage> getListByStorageTypeAndCityId(int type, long cityId) {
		Map<String, Object> params = new HashMap<>();
		params.put("type", type);
		params.put("cityId", cityId);
		return this.getSqlSession().selectList("Storage.getListByStorageTypeAndCityId", params);
	}
}
