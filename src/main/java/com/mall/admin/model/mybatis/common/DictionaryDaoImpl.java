package com.mall.admin.model.mybatis.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mall.admin.model.dao.common.DictionaryDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.vo.common.Dictionary;

@Repository
public class DictionaryDaoImpl extends BaseMallDaoImpl implements DictionaryDao {

	@Override
	public List<Dictionary> queryDictionaryByTypeId(Long typeId) {
		
		return this.getSqlSession().selectList("Dictionary.getDictionaryByTypeId", typeId);
		
	}
	
	
	
	public Dictionary getDictionaryByTypeIdAndSysCode(Long typeId,String sysCode) {
		Map parameter = new HashMap();
		parameter.put("typeId", typeId);
		parameter.put("sysCode",sysCode);
		List<Dictionary> dictionarys = this.getSqlSession().selectList("Dictionary.getDictionaryByTypeIdAndSysCode", parameter);
		if(dictionarys != null && dictionarys.size() > 0) {
			return dictionarys.get(0);
		} else {
			return null;
		}
		
	}

}
