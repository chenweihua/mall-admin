package com.mall.admin.service.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.storage.StorageGoodsRecord;
import com.mall.admin.vo.wms.StorageGoodsStock;
import com.mall.admin.vo.wms.WmsGoods;

public interface StorageGoodsRecordService {

	/**
	 * 根据条件查询分页
	 * 
	 * @param begin
	 *                开始页
	 * @param pagenum
	 *                每页个数
	 * @param wmsGoods
	 *                查询条件
	 * @return
	 */
	public List<StorageGoodsStock> getStorageGoodsStock(StorageGoodsRecord storageGoods, PaginationInfo paginationInfo);

	/**
	 * 
	 * @param wmsgoods
	 *                待插入的对象
	 * @return
	 */
	public int insert(StorageGoodsRecord storageGoods);

	/**
	 * 
	 * @param wmsgoods
	 *                待更新的对象
	 * @return
	 */
	public int update(StorageGoodsRecord record);

	/**
	 * 根据参数查询
	 * 
	 * @param gmb
	 * @return
	 */
	public List<StorageGoodsRecord> getByQuery(long wms_goods_id,long storage_Id,int storagetype,PaginationInfo paginationInfo);

	/**
	 * 根据id查询库存
	 * 
	 * @param id
	 * @return
	 */
	public StorageGoodsRecord queryById(Long id);
	
	public Pair<Long, PaginationList<StorageGoodsRecord>> getPageStorage(
			PaginationInfo paginationInfo, Map paramMap) ;
	
	public Pair<Long,List<StorageGoodsRecord>> getStorage(
			Map paramMap);
	
	public List<StorageGoodsRecord> getGoodsRecordByLockUserId(long userId,long storageId);

	public int lockRecord(long storageGoodsRecordId,long userId);
	
	public int unlockRecord(long storageGoodsRecordId,long userId);
	
	public int setRecordPayingStatus(long userId,String applypaycode,long storageId);
	
	public List<StorageGoodsRecord> getStorageGoodsRecordsByApplyPayCode(String applyPayCode);

	public boolean updateAccountMoney(long recordIdLong, long accountMoney, String remark);

	public int updateRecordPayedStatus(String applyPayCode);
	
	
}
