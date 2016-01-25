package com.mall.admin.model.bean.ztree;

import java.util.List;

public class ZtreeModelBean {

	public String id;
	public String pid;
	public String name;
	public boolean open;
	public boolean checked;
	public List<ZtreeModelBean> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<ZtreeModelBean> getChildren() {
		return children;
	}

	public void setChildren(List<ZtreeModelBean> children) {
		this.children = children;
	}

}
