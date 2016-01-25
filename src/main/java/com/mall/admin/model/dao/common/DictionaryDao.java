package com.mall.admin.model.dao.common;

import java.util.List;

import com.mall.admin.vo.common.Dictionary;

public interface DictionaryDao {

	public List<Dictionary> queryDictionaryByTypeId(Long typeId);
	
	
	public Dictionary getDictionaryByTypeIdAndSysCode(Long typeId,String sysCode);
	
}
