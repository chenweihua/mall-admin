package com.mall.admin.model.bean.ztree;

import java.util.List;

/**
 * 前端显示对象列表
 * 
 * @author Administrator
 *
 */
public class ZtreeResultBean {

	public List<ZtreeModelBean> ztreeModelList;

	public List<ZtreeModelBean> getZtreeModelList() {
		return ztreeModelList;
	}

	public void setZtreeModelList(List<ZtreeModelBean> ztreeModelList) {
		this.ztreeModelList = ztreeModelList;
	}

}
