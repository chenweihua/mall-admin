package com.mall.admin.model.dao.storage;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.mallbase.Storage;
import com.mall.admin.vo.storage.RecordType;
import com.mall.admin.vo.storage.StorageGoodsRecord;
import com.mall.admin.vo.wms.StorageGoodsStock;
import com.mall.admin.vo.wms.WmsGoods;

public interface StorageGoodsRecordDao {
	
	public int insertRecord(StorageGoodsRecord record);
	public int updateRecord(StorageGoodsRecord record);
	
	public Pair<Long, PaginationList<StorageGoodsRecord>> getPageStorage(
			PaginationInfo paginationInfo, Map paramMap);
		
	public Pair<Long,List<StorageGoodsRecord>> getStorage(
			Map paramMap);
	
	public List<StorageGoodsRecord> getGoodsRecordByLockUserId(long userId,long storageId);

	public int lockRecord(long storageGoodsRecordId,long userId);
	
	public int unlockRecord(long storageGoodsRecordId,long userId);
	
	public int setRecordPayingStatus(long userId,String applypaycode,long storageId);

	public StorageGoodsRecord queryById(long storageGoodsRecordId);
	
	public List<StorageGoodsRecord> getStorageGoodsRecordsByApplyPayCode(String applyPayCode);
	
	public int updateAccountMoney(long recordId, long accountMoney, String remark);
	
	public int updateRecordPayedStatus(String applyPayCode);
	
	
}
