package com.mall.admin.model.dao.mallbase;

import java.util.List;

import com.mall.admin.vo.mallbase.MallIni;


/**
 * 操作INI全局配置的dao接口
 * 
 * @author Singal
 *
 */
public interface MallIniDao {
	/**
	 * 获取所有
	 * 
	 * @return
	 */
	List<MallIni> getAllMallIni();
}
