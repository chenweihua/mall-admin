package com.mall.admin.model.dao.mallbase;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.Storage;

public interface CollegeDao {
	public List<College> getAllCollege();

	public List<College> getPageCollege(PaginationInfo paginationInfo);

	public College getCollege(long collegeId);
	
	public Pair<Long,List<College>> getPageCollege(long cityId, String keyword, int start, int numPerPage);
	
	public long addCollege(College college);
	
	public long updateCollege(College college);

	public College getCollegeById(long collegeId);
	
	public long deleCollege(long collegeId);
	
	public List<College> getListByLdcStorageId(long ldcstorage_id);
	
	public List<College> getListByRdcStorageId(long rdcstorage_id);
	
	public List<College> getListByCityId(long city_id);
	
	public List<College> getListByStorateids(List<Long> rdcStorateIds, List<Long> ldcStorateIds);
}
