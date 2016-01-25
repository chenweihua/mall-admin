package com.mall.admin.service.mallbase.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.mall.admin.constant.CollegeConstant;
import com.mall.admin.model.dao.mallbase.CollegeDao;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.service.mallbase.CollegeService;
import com.mall.admin.vo.goods.Sku;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.dto.CollegeDto;

@Service("collegeService")
//@Transactional(propagation = Propagation.SUPPORTS,rollbackFor=Exception.class) 
public class CollegeServiceImpl implements CollegeService {

	@Autowired
	private CollegeDao collegeDao;
	
	TransactionTemplate txTemplate;  

	@Override
	public List<College> getAllCollege() {
		return collegeDao.getAllCollege();
	}

	@Override
	public College getCollege(long collegeId) {
		return collegeDao.getCollege(collegeId);
	}

	@Override
	public List<College> getPageCollege(PaginationInfo paginationInfo) {
		return collegeDao.getPageCollege(paginationInfo);
	}

	@Override
	public Pair<Long, List<College>> getPageCollege(long cityId, String keyword, int start, int numPerPage) {
		// TODO Auto-generated method stub
		return collegeDao.getPageCollege(cityId, keyword, start, numPerPage);
	}

//	@Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
	@Override
	public long addCollege(College college){
			return collegeDao.addCollege(college);		
	}

	@Override
	public long updateCollege(College college) {
		// TODO Auto-generated method stub
		return collegeDao.updateCollege(college);
	}

	@Override
	public College getCollegeById(long collegeId) {
		// TODO Auto-generated method stub
		return collegeDao.getCollegeById(collegeId);
	}

	@Override
	public long deleCollege(long collegeId) {
		// TODO Auto-generated method stub
		return collegeDao.deleCollege(collegeId);
	}

	@Override
	public long getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<College> getListByLdcStorageId(long ldcstorage_id) {
		return collegeDao.getListByLdcStorageId(ldcstorage_id);
	}

	@Override
	public List<College> getListByRdcStorageId(long rdcstorage_id) {
		return collegeDao.getListByRdcStorageId(rdcstorage_id);
	}

	@Override
	public List<College> getListByCityId(long city_id) {
		return collegeDao.getListByCityId(city_id);
	}

	@Override
	public Map<String, List<Long>> parseBaseInfo(List<Long> collegeIds) {
		College college = null;
		List<Long> ldcStorage = new ArrayList<>();
		List<Long> rdcStorage = new ArrayList<>();
		List<Long> ldcCity = new ArrayList<>();

		for (Long id : collegeIds) {
			college = collegeDao.getCollegeById(id);
			if (college != null) {
				if (college.getRdcStorageId() > 0) {
					rdcStorage.add(college.getRdcStorageId());
				}

				if (college.getLdcStorageId() > 0) {
					ldcStorage.add(college.getLdcStorageId());
					ldcCity.add(college.getCityId());
				}
			}
		}

		Map<String, List<Long>> map = new HashMap<String, List<Long>>();
		map.put("ldcStorage", ldcStorage);
		map.put("rdcStorage", rdcStorage);
		map.put("ldcCity", ldcCity);
		return map;
	}

	
	@Override
	public List<Long> parseStorageInfo(List<Long> collegeIds, long cityId,
			int isLdc) {
		College college = null;
		List<Long> storageIds = new ArrayList<>();

		for (Long id : collegeIds) {
			college = collegeDao.getCollegeById(id);
			if (college != null) {
				if(isLdc == 0){
					if (college.getCityId() == cityId && college.getRdcStorageId() > 0) {
						storageIds.add(college.getRdcStorageId());
					}
				}else if(isLdc == 1){
					if (college.getCityId() == cityId && college.getLdcStorageId() > 0) {
						storageIds.add(college.getLdcStorageId());
					}
				}
			}
		}
		return storageIds;
	}

	@Override
	public List<College> getListByStorateids(List<Long> rdcStorateIds, List<Long> ldcStorateIds) {
		return collegeDao.getListByStorateids(rdcStorateIds, ldcStorateIds);
	}

	@Override
	public List<CollegeDto> filterByStorageId(List<Long> collegeIds,
			long storageId, int isLdc) {
		List<CollegeDto> collegeDtos = new ArrayList<>();
		College college = null;

		for (Long id : collegeIds) {
			//college = collegeDao.getCollegeById(id);
			college = CollegeConstant.getCollegeById(id);
			if (college != null) {
				CollegeDto collegeDto = new CollegeDto();
				if(isLdc == Sku.RDC_DISTRIBUTE_TYPE){
					if (college.getRdcStorageId() == storageId) {
						collegeDto.setCollege(college);
						collegeDtos.add(collegeDto);
					}
				}else if(isLdc == Sku.LDC_DISTRIBUTE_TYPE){
					if (college.getLdcStorageId() == storageId) {
						collegeDto.setCollege(college);
						collegeDtos.add(collegeDto);
					}
				}else if(isLdc == Sku.VM_DISTRIBUTE_TYPE){
					collegeDto.setCollege(college);
					collegeDtos.add(collegeDto);
				}
			}
		}
		
		return collegeDtos;
	}

	@Override
	public Map<String, List> filterByCityId(List<Long> collegeIds, long cityId,
			int isLdc) {
		List<CollegeDto> collegeDtos = new ArrayList<>();
		List<Long> storages = new ArrayList<>();
		College college = null;

		for (Long id : collegeIds) {
			college = collegeDao.getCollegeById(id);
			if (college != null) {
				CollegeDto collegeDto = new CollegeDto();
				if(isLdc == 0){
					if (college.getCityId() == cityId && college.getRdcStorageId() > 0) {
						storages.add(college.getRdcStorageId());
						collegeDto.setCollege(college);
						collegeDtos.add(collegeDto);
					}
				}else if(isLdc == 1){
					if (college.getCityId() == cityId && college.getLdcStorageId() > 0) {
						storages.add(college.getLdcStorageId());
						collegeDto.setCollege(college);
						collegeDtos.add(collegeDto);
					}
				}
			}
		}
		Map<String, List> map = new HashMap<>();
		map.put("collegeDtos", collegeDtos);
		map.put("storages", storages);
		return map;
	}	
	
}
