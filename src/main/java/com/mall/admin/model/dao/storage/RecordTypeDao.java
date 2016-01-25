package com.mall.admin.model.dao.storage;

import java.util.List;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.storage.RecordType;
import com.mall.admin.vo.wms.StorageGoodsStock;
import com.mall.admin.vo.wms.WmsGoods;

public interface RecordTypeDao {
	
	public RecordType getByName(String name);
	public RecordType getById(long id);
	public List<RecordType> getAll();
}
