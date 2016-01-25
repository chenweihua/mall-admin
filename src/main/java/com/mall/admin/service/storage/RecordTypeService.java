package com.mall.admin.service.storage;

import java.util.List;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.storage.RecordType;
import com.mall.admin.vo.wms.StorageGoodsStock;
import com.mall.admin.vo.wms.WmsGoods;

public interface RecordTypeService {

	public RecordType getById(long id);
	public RecordType getByName(String name);
	public List<RecordType> getAll();
}
