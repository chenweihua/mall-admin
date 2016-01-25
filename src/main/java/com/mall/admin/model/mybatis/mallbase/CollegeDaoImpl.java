package com.mall.admin.model.mybatis.mallbase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.mall.admin.model.dao.mallbase.CollegeDao;
import com.mall.admin.model.mybatis.base.BaseMallDaoImpl;
import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;
import com.mall.admin.util._;
import com.mall.admin.vo.mallbase.College;
import com.mall.admin.vo.mallbase.CollegeExample;
import com.mall.admin.vo.mallbase.CollegeMapper;

@Repository
public class CollegeDaoImpl extends BaseMallDaoImpl implements CollegeDao {

	public CollegeMapper getCollegeMapper() {
		return this.getSqlSession().getMapper(CollegeMapper.class);
	}

	@Override
	public List<College> getAllCollege() {
		CollegeExample example = new CollegeExample();
		//example.createCriteria().andIsOpenEqualTo(_.toByte(1));
		// return
		// this.getSqlSession().selectList("College.getAllCollege");
		return getCollegeMapper().selectByExample(example);
	}

	@Override
	public College getCollege(long collegeId) {
		// return this.getSqlSession().selectOne("College.getCollege",
		// collegeId);
		return getCollegeMapper().selectByPrimaryKey(collegeId);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PaginationList<College> getPageCollege(PaginationInfo paginationInfo) {
		Map<String, PaginationInfo> parameterMap = new HashMap<String, PaginationInfo>();
		parameterMap.put("PaginationInfo", paginationInfo);
		PaginationList<College> collegeList = selectPaginationList("getPageCollegeByPage", paginationInfo);
		return collegeList;
	}

	@Override
	public Pair<Long, List<College>> getPageCollege(long cityId, String keyword, int start, int numPerPage) {
		// TODO Auto-generated method stub
		// long totalCount =
		// this.getSqlSession().selectOne("getCountByParam", paramMap);
		// Map<String, PaginationInfo> parameterMap = new
		// HashMap<String, PaginationInfo>();
		// parameterMap.put("PaginationInfo", paginationInfo);
		// PaginationList<College> collegeList = selectPaginationList(
		// "getPageCollegeByPage",paramMap,paginationInfo);
		// return Pair.of(totalCount , collegeList);

		CollegeExample example = new CollegeExample().setStart(start).setLimit(numPerPage);

		if (cityId != 0) {
			if (!_.isEmpty(keyword)) {
				example.createCriteria().andIsDelEqualTo((byte) 0).andCityIdEqualTo(cityId).andCollegeNameLike("%"+keyword+"%");
				example.or().andCityIdEqualTo(cityId).andCollegeNameLike(keyword);
			} else {
				example.createCriteria().andIsDelEqualTo((byte) 0).andCityIdEqualTo(cityId);
			}
		} else {
			if (!_.isEmpty(keyword)) {
				example.createCriteria().andIsDelEqualTo((byte) 0).andCollegeNameLike("%"+keyword+"%");
				example.or().andCollegeIdLike(keyword);
			} else {
				example.createCriteria().andIsDelEqualTo((byte) 0);
			}
		}
		example.setOrderByClause("create_time desc");

		List<College> datas = getCollegeMapper().selectByExample(example);
		long count = getCollegeMapper().countByExample(example);
		return Pair.of(count, datas);

	}

	@Override
	public long addCollege(College college) {
		// TODO Auto-generated method stub
		return  getCollegeMapper().insertSelective(college);
	}

	@Override
	public long updateCollege(College college) {
		// TODO Auto-generated method stub
		return getCollegeMapper().updateByPrimaryKeySelective(college);
	}

	@Override
	public College getCollegeById(long collegeId) {
		// TODO Auto-generated method stub
		return getCollegeMapper().selectByPrimaryKey(collegeId);
	}

	@Override
	public long deleCollege(long collegeId) {
		// TODO Auto-generated method stub
		return getCollegeMapper().deleteByPrimaryKey(collegeId);// this.getSqlSession().update("College.deleCollege",collegeId
										// );
	}

	@Override
	public List<College> getListByLdcStorageId(long ldcstorage_id) {
		CollegeExample example = new CollegeExample();
		example.createCriteria().andLdcStorageIdEqualTo(ldcstorage_id);
		return getCollegeMapper().selectByExample(example);
	}

	@Override
	public List<College> getListByRdcStorageId(long rdcstorage_id) {
		CollegeExample example = new CollegeExample();
		example.createCriteria().andRdcStorageIdEqualTo(rdcstorage_id);
		return getCollegeMapper().selectByExample(example);
	}

	@Override
	public List<College> getListByCityId(long city_id) {
		CollegeExample example = new CollegeExample();
		example.createCriteria().andCityIdEqualTo(city_id);
		// return
		// this.getSqlSession().selectList("College.getListByCityId",city_id);
		return getCollegeMapper().selectByExample(example);
	}
	
	@Override
	public List<College> getListByStorateids(List<Long> rdcStorageIds,
			List<Long> ldcStorageIds) {
		
		if(_.isEmpty(rdcStorageIds) && _.isEmpty(ldcStorageIds)) {
			return Lists.newArrayList();
		}
		
		CollegeExample example = new CollegeExample();
		if(!_.isEmpty(rdcStorageIds)) {
			example.or().andIsDelEqualTo(_.toByte(0)).andRdcStorageIdIn(rdcStorageIds);
		}
		
		if(!_.isEmpty(ldcStorageIds)) {
			example.or().andIsDelEqualTo(_.toByte(0)).andLdcStorageIdIn(ldcStorageIds);
		}
		
		return getCollegeMapper().selectByExample(example);
	}
}
