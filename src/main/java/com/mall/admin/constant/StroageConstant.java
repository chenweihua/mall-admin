package com.mall.admin.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mall.admin.service.mallbase.StorageService;
import com.mall.admin.vo.mallbase.Storage;



public class StroageConstant {
	private static Map<Long, Storage> ldcStorageMap = new HashMap<Long, Storage>();
	private static Map<String, Storage> ldcStorageNameMap = new HashMap<String, Storage>();
	private static Map<Long, Storage> rdcStorageMap = new HashMap<Long, Storage>();
	private static Map<String, Storage> rdcStorageNameMap = new HashMap<String, Storage>();
	private static Map<Long, Storage> storageMap = new HashMap<Long, Storage>();
	private static Map<String, Storage> storageNameMap = new HashMap<String, Storage>();

	@Autowired
	private StorageService storageService;

	private StroageConstant() {
	}

	/**
	 * 初始化函数
	 */
	public void init() {
		LogConstant.mallLog.debug("init storage");
		refresh();
	}

	/**
	 * 刷新
	 */
	public void refresh() {
		Map<Long, Storage> tempLdcStorageMap = new HashMap<Long, Storage>();
		Map<String, Storage> tempLdcNamestorageMap = new HashMap<String, Storage>();
		Map<Long, Storage> tempRdcStorageMap = new HashMap<Long, Storage>();
		Map<String, Storage> tempRdcNamestorageMap = new HashMap<String, Storage>();
		Map<Long, Storage> tempStorageMap = new HashMap<Long, Storage>();
		Map<String, Storage> tempNamestorageMap = new HashMap<String, Storage>();
		List<Storage> ldcStorageList = storageService.getAllLdcStorage();
		List<Storage> rdcStorageList = storageService.getAllRdcStorage();
		List<Storage> storageList = storageService.getAllStorage();
		if (!ldcStorageList.isEmpty()) {
			for (Storage storage : ldcStorageList) {
				tempLdcStorageMap.put(storage.getStorageId(), storage);
				tempLdcNamestorageMap.put(storage.getStorageName(), storage);
			}
		}
		ldcStorageMap = tempLdcStorageMap;
		ldcStorageNameMap = tempLdcNamestorageMap;
		
		if (!rdcStorageList.isEmpty()) {
			for (Storage storage : rdcStorageList) {
				tempRdcStorageMap.put(storage.getStorageId(), storage);
				tempRdcNamestorageMap.put(storage.getStorageName(), storage);
			}
		}
		rdcStorageMap = tempRdcStorageMap;
		rdcStorageNameMap = tempRdcNamestorageMap;
		
		if (!storageList.isEmpty()) {
			for (Storage storage : storageList) {
				tempStorageMap.put(storage.getStorageId(), storage);
				tempNamestorageMap.put(storage.getStorageName(), storage);
			}
		}
		storageMap = tempStorageMap;
		storageNameMap = tempNamestorageMap;
		
		LogConstant.mallLog
				.info("refresh "
						+ (ldcStorageList.isEmpty() ? 0 : ldcStorageList.size())
						+ " ldcStorage "
						+ (rdcStorageList.isEmpty() ? 0 : rdcStorageList.size())
						+ " rdcStorage "
						+ (storageList.isEmpty() ? 0 : storageList.size())
						+ " storage ");
	}

	/**
	 * 根据id获得整个storage对象
	 * 
	 * @param key
	 * @return
	 */
	public static Storage getStorageById(Long id) {
		return storageMap.get(id);
	}
	public static Storage getLdcStorageById(Long id) {
		return ldcStorageMap.get(id);
	}
	
	public static Storage getRdcStorageById(Long id) {
		return rdcStorageMap.get(id);
	}

	/**
	 * 根据name获得整个storage对象
	 * 
	 * @param key
	 * @return
	 */
	public static Storage getLdcStorageByName(String name) {
		return ldcStorageNameMap.get(name);
	}
	
	public static Storage getRdcStorageByName(String name) {
		return rdcStorageNameMap.get(name);
	}

	public static Map<Long, Storage> getLdcStorageMap() {
		return ldcStorageMap;
	}
	
	public static Map<Long,Storage> getStorageMap(){
		return storageMap;
	}
	
	public static Map<Long, Storage> getRdcStorageMap() {
		return rdcStorageMap;
	}

	public static Map<String, Storage> getLdcStorageNameMap() {
		return ldcStorageNameMap;
	}
	
	public static Map<String, Storage> getRdcStorageNameMap() {
		return rdcStorageNameMap;
	}
	
	public static Map<String,Storage> getStorageNameMap(){
		return storageNameMap;
	}
}
