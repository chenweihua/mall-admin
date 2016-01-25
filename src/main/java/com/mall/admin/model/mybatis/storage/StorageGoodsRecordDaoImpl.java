package com.mall.admin.model.mybatis.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.mall.admin.model.dao.storage.StorageGoodsRecordDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.storage.StorageGoodsRecord;

@Repository
public class StorageGoodsRecordDaoImpl extends BaseMallDaoImpl implements StorageGoodsRecordDao {

	@Override
	public int insertRecord(StorageGoodsRecord record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert("StorageGoodsRecord.insertRecord", record);
	}

	@Override
	public int updateRecord(StorageGoodsRecord record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update("StorageGoodsRecord.updateRecord", record);
	}

	@Override
	public Pair<Long, PaginationList<StorageGoodsRecord>> getPageStorage(PaginationInfo paginationInfo, Map paramMap) {
		// TODO Auto-generated method stub
		long totalCount = this.getSqlSession().selectOne("StorageGoodsRecord.getCountByParams", paramMap);
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<StorageGoodsRecord> storageGoodsRecordList = selectPaginationList(
				"StorageGoodsRecord.getPageStorageGoodsRecordByPage", paramMap, paginationInfo);
		return Pair.of(totalCount, storageGoodsRecordList);
	}

	@Override
	public Pair<Long, List<StorageGoodsRecord>> getStorage(Map paramMap) {
		// TODO Auto-generated method stub
		long totalCount = this.getSqlSession().selectOne("StorageGoodsRecord.getCountByParams", paramMap);
		List<StorageGoodsRecord> storageGoodsRecordList = selectPaginationList(
				"StorageGoodsRecord.getPageStorageGoodsRecordByPage", paramMap);
		return Pair.of(totalCount, storageGoodsRecordList);
	}

	@Override
	public List<StorageGoodsRecord> getGoodsRecordByLockUserId(long userId, long storageId) {
		// TODO Auto-generated method stub
		Map paramMap = new HashMap();
		paramMap.put("lock_user", userId);
		paramMap.put("pay_status", 0);
		paramMap.put("storage_id", storageId);
		return this.getSqlSession().selectList("StorageGoodsRecord.getPageStorageGoodsRecordByPage", paramMap);
	}

	@Override
	public int lockRecord(long storageGoodsRecordId, long userId) {
		// TODO Auto-generated method stub
		Map paramMap = new HashMap();
		paramMap.put("storageGoodsRecordId", storageGoodsRecordId);
		paramMap.put("userId", userId);
		return this.getSqlSession().update("StorageGoodsRecord.lockRecord", paramMap);
	}

	@Override
	public int unlockRecord(long storageGoodsRecordId, long userId) {
		// TODO Auto-generated method stub
		Map paramMap = new HashMap();
		paramMap.put("storageGoodsRecordId", storageGoodsRecordId);
		paramMap.put("userId", userId);
		return this.getSqlSession().update("StorageGoodsRecord.unlockRecord", paramMap);
	}

	@Override
	public int setRecordPayingStatus(long userId, String applypaycode, long storageId) {
		// TODO Auto-generated method stub
		Map paramMap = new HashMap();
		paramMap.put("storageId", storageId);
		paramMap.put("userId", userId);
		paramMap.put("applypaycode", applypaycode);
		return this.getSqlSession().update("StorageGoodsRecord.setRecordPayingStatus", paramMap);
	}

	@Override
	public StorageGoodsRecord queryById(long storageGoodsRecordId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne("StorageGoodsRecord.queryById", storageGoodsRecordId);
	}

	@Override
	public List<StorageGoodsRecord> getStorageGoodsRecordsByApplyPayCode(String applyPayCode) {
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("applyPayCode", applyPayCode);
		return this.getSqlSession().selectList("StorageGoodsRecord.getStorageGoodsRecordsByApplyPayCode",
				paramMap);
	}

	@Override
	public int updateAccountMoney(long recordId, long accountMoney, String remark) {
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("accountMoney", accountMoney);
		paramMap.put("modifyRemark", remark);
		paramMap.put("id", recordId);
		return this.getSqlSession().update("StorageGoodsRecord.updateAccountMoney", paramMap);
	}

	@Override
	public int updateRecordPayedStatus(String applyPayCode) {
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("applyPayCode", applyPayCode);
		return this.getSqlSession().update("StorageGoodsRecord.updateRecordPayedStatus", paramMap);
	}

}
