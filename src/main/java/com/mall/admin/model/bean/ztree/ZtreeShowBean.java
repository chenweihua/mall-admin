package com.mall.admin.model.bean.ztree;

/**
 * { id:1, pId:0, name:"RDC", open:true,icon:"/img/ztree/rdc.png"} 前端数据待显示的对象信息
 * 
 * @author Administrator
 *
 */
public class ZtreeShowBean {

	public String id;
	public String pId;
	public String name;
	public String open;
	public String icon;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
