package com.mall.admin.service.storage.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.admin.model.dao.storage.StorageGoodsRecordDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.service.storage.StorageGoodsRecordService;
import com.mall.admin.service.user.UserService;
import com.mall.admin.vo.storage.StorageGoodsRecord;
import com.mall.admin.vo.user.User;
import com.mall.admin.vo.wms.StorageGoodsStock;

@Service
public class StorageGoodsRecordServiceImpl implements StorageGoodsRecordService {

	@Autowired
	StorageGoodsRecordDao recordDao;
	
	@Autowired
	UserService userService;
	
	@Override
	public List<StorageGoodsStock> getStorageGoodsStock(
			StorageGoodsRecord storageGoods, PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(StorageGoodsRecord record) {
		// TODO Auto-generated method stub
		return recordDao.insertRecord(record);
	}

	@Override
	public int update(StorageGoodsRecord record) {
		// TODO Auto-generated method stub
		return recordDao.updateRecord(record);
	}

	@Override
	public List<StorageGoodsRecord> getByQuery(long wms_goods_id,
			long storage_Id, int storagetype, PaginationInfo paginationInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StorageGoodsRecord queryById(Long id) {
		// TODO Auto-generated method stub
		return recordDao.queryById(id);
	}

	@Override
	public Pair<Long, PaginationList<StorageGoodsRecord>> getPageStorage(
			PaginationInfo paginationInfo, Map paramMap) {
		// TODO Auto-generated method stub
		Pair<Long, PaginationList<StorageGoodsRecord>> p= recordDao.getPageStorage(paginationInfo, paramMap);
		List<StorageGoodsRecord> storageGoodsRecordList = p.getRight();
		for(StorageGoodsRecord storageGoodsRecord : storageGoodsRecordList){
			if(storageGoodsRecord.getLock_user()!=0){
				User user = userService.getUserById((long) storageGoodsRecord.getLock_user());
				storageGoodsRecord.setLock_userName(user.getUser_name());
			}
		}		
		return p;
	}

	@Override
	public Pair<Long, List<StorageGoodsRecord>> getStorage(Map paramMap) {
		// TODO Auto-generated method stub
		return recordDao.getStorage(paramMap);
	}

	@Override
	public List<StorageGoodsRecord> getGoodsRecordByLockUserId(long userId,
			long storageId) {
		// TODO Auto-generated method stub
		return recordDao.getGoodsRecordByLockUserId(userId, storageId);
	}

	@Override
	public int lockRecord(long storageGoodsRecordId, long userId) {
		// TODO Auto-generated method stub
		return recordDao.lockRecord(storageGoodsRecordId, userId);
	}

	@Override
	public int unlockRecord(long storageGoodsRecordId, long userId) {
		// TODO Auto-generated method stub
		return recordDao.unlockRecord(storageGoodsRecordId, userId);
	}

	@Override
	public int setRecordPayingStatus(long userId, String applypaycode,
			long storageId) {
		// TODO Auto-generated method stub
		return recordDao.setRecordPayingStatus(userId, applypaycode, storageId);
	}

	@Override
	public List<StorageGoodsRecord> getStorageGoodsRecordsByApplyPayCode(String applyPayCode) {
		return recordDao.getStorageGoodsRecordsByApplyPayCode(applyPayCode);
	}
	
	@Override
	public boolean updateAccountMoney(long recordId, long accountMoney, String remark) {
		int count = recordDao.updateAccountMoney(recordId, accountMoney, remark);
		return count == 1;
	}
	
	@Override
	public int updateRecordPayedStatus(String applyPayCode) {
		return recordDao.updateRecordPayedStatus(applyPayCode);
	}

}
