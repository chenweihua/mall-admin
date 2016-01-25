package com.mall.admin.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mall.admin.service.storage.RecordTypeService;
import com.mall.admin.vo.storage.RecordType;


public class RecordTypeConstant {
	private static Map<Integer, RecordType> recordTypeMap = new HashMap<Integer, RecordType>();
	private static Map<String, RecordType> recordTypeNameMap = new HashMap<String, RecordType>();

	@Autowired
	private RecordTypeService recordTypeService;

	private RecordTypeConstant() {
	}

	/**
	 * 初始化函数
	 */
	public void init() {
		
		LogConstant.mallLog.debug("init RecordType");
		refresh();
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		Map<Integer, RecordType> tempRecordTypeMap = new HashMap<Integer, RecordType>();
		Map<String, RecordType> tempNameRecordTypeMap = new HashMap<String, RecordType>();
		List<RecordType> RecordTypeList = recordTypeService.getAll();
		if (!RecordTypeList.isEmpty()) {
			for (RecordType recordType : RecordTypeList) {
				tempRecordTypeMap.put(recordType.getId(), recordType);
				tempNameRecordTypeMap.put(recordType.getName(), recordType);
			}
		}
		recordTypeMap = tempRecordTypeMap;
		recordTypeNameMap = tempNameRecordTypeMap;
		LogConstant.mallLog
				.info("refresh "
						+ (RecordTypeList.isEmpty() ? 0 : RecordTypeList.size())
						+ " RecordType");
	}

	/**
	 * 根据id获得整个RecordType对象
	 * 
	 * @param key
	 * @return
	 */
	public static RecordType getRecordTypeById(Integer id) {
		return recordTypeMap.get(id);
	}

	/**
	 * 根据name获得整个RecordType对象
	 * 
	 * @param key
	 * @return
	 */
	public static RecordType getRecordTypeByName(String name) {
		return recordTypeNameMap.get(name);
	}

	public static Map<Integer, RecordType> getRecordTypeMap() {
		return recordTypeMap;
	}

	public static Map<String, RecordType> getRecordTypeNameMap() {
		return recordTypeNameMap;
	}
	
}
