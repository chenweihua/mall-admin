package com.mall.admin.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mall.admin.constant.SpringConstant;
import com.mall.admin.model.dao.common.DictionaryDao;
import com.mall.admin.vo.common.Dictionary;

public class DictionaryUtil {
	
	private static DictionaryDao dictionaryDao = (DictionaryDao)SpringConstant.getBean("dictionaryDao");
	
	
	public static String getValueByTypeIdAndKey(Long typeId, String key) {
		
		Dictionary dictionary = dictionaryDao.getDictionaryByTypeIdAndSysCode(typeId, key);
		return dictionary == null ? null : dictionary.getSysValue();
	}
	
	
	public static List<String> getValuesByTypeId(Long typeId) {
		
		List<Dictionary> dictionarys = dictionaryDao.queryDictionaryByTypeId(typeId);
		List<String> list = new ArrayList<String>(dictionarys == null ? 0 : dictionarys.size());
		if(dictionarys != null) {
			for(Dictionary d : dictionarys) {
				list.add(d.getSysValue());
			}
		}
		return list;
		
	}

}
