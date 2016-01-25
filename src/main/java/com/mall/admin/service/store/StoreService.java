package com.mall.admin.service.store;

public interface StoreService {
	/**
	 * 根据门店storeId，调用门店接口，并解析出collegeId
	 * @param storeId
	 * @return collegeId : 
	 * 					-3 ：传递的storeId<0
	 *                  -2 : 接口调用失败
	 *                  -1 : 返回数据错误
	 *                  > 0 :接口调用正常
	 */
	public long getCollegeIdByStoreId(long storeId);
}
