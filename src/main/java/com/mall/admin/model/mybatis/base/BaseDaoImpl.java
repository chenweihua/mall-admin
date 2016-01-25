package com.mall.admin.model.mybatis.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;

import com.mall.admin.model.pagination.PaginationInfo;
import com.mall.admin.model.pagination.PaginationList;

@Component
public class BaseDaoImpl extends SqlSessionDaoSupport {

	@SuppressWarnings("unchecked")
	/*
	 * parameter 如果是一个非map对象，里面必须包含PaginationInfo属性或者就是PaginationInfo对象
	 */
	public PaginationList selectPaginationList(String statement,
			Object parameter, PaginationInfo paginationInfo) {
		PaginationList paginationList = new PaginationList();

		if (parameter == null) {
			throw new RuntimeException("parameter can not be null");
		}
		if (parameter instanceof Map<?, ?>) {
			((Map) parameter).put("paginationInfo", paginationInfo);
		}
		List result = this.getSqlSession().selectList(statement, parameter);

		paginationList.addAll(result);
		if (paginationInfo == null) {
			paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPage(1);
			paginationInfo.setRecordPerPage(result.size());
			paginationInfo.setTotalPage(1);
			paginationInfo.setTotalRecord(result.size());
		}
		paginationList.setPaginationInfo(paginationInfo);
		return paginationList;
	}

	public PaginationList selectPaginationList(String statement,
			Object parameter) {
		return selectPaginationList(statement, parameter, null);
	}

	@SuppressWarnings("unchecked")
	public PaginationList selectPaginationList(String statement,
			PaginationInfo paginationInfo) {
		return selectPaginationList(statement, new HashMap(), paginationInfo);
	}
}
