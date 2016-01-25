package com.mall.admin.service.mallbase;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.dto.CollegeDto;

public interface CollegeService {
	public List<College> getAllCollege();

	public College getCollege(long collegeId);

	public List<College> getPageCollege(PaginationInfo paginationInfo);

	public Pair<Long, List<College>> getPageCollege(long cityId, String keyword, int start, int numPerPage);

	public long addCollege(College college);

	public long updateCollege(College college);

	public long getCount();

	public College getCollegeById(long collegeId);

	public long deleCollege(long collegeId);

	public List<College> getListByLdcStorageId(long ldcstorage_id);

	public List<College> getListByRdcStorageId(long rdcstorage_id);

	public List<College> getListByCityId(long city_id);
	

	/**
	 * @author zhangshuai
	 * @param collegeId
	 * @return("ldcStorage", ldcStorage); ("rdcStorage", rdcStorage);
	 *                       ("ldcCity", ldcCity);
	 */
	public Map<String, List<Long>> parseBaseInfo(List<Long> collegeIds);
	/**
	 * 依据城市解析出仓
	 * @param collegeIds
	 * @param cityId
	 * @param isLdc
	 * @return
	 */
	public List<Long> parseStorageInfo(List<Long> collegeIds,long cityId,int isLdc);
	
	/**
	 * 根据storageID过滤collegeIds
	 * @author 张帅
	 * @param collegeIds
	 * @param storageId
	 * @param isLdc ，0表示Rdc,1表示Ldc
	 * @return
	 */
	public List<CollegeDto> filterByStorageId(List<Long> collegeIds,long storageId,int isLdc);
	
	public Map<String, List> filterByCityId(List<Long> collegeIds,long cityId,int isLdc);
	
	public List<College> getListByStorateids(List<Long> rdcStorateIds, List<Long> ldcStorateIds);

}
