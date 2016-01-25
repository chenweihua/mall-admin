package com.mall.admin.model.bean.ztree;

import java.util.ArrayList;
import java.util.List;

public class ZtreeBean {

	public String id;
	public String pid;
	public String name;
	/**
	 * 10:rdc总仓 12:rdc仓 13:rdc学校 | 20:ldc总仓 21:城市 22:ldc仓 23:ldc学校
	 */
	public int type;
	public String open = "true";
	public String checked = "false";
	public String icon;
	public boolean isAllChecked = true;
	public List<ZtreeBean> children;
	
	/*
	 * 勾选状态说明
	 * -1	不存在子节点 或 子节点全部设置为 nocheck = true       
	 * 0	无 子节点被勾选                               
	 * 1	部分 子节点被勾选                              
	 * 2	全部 子节点被勾选                              
	 */
	private String check_Child_State;
	
	
	private String chkDisabled;
	
	
	private String nocheck;

	public ZtreeBean(String id, String pid, String name, int type, String icon)
	{
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.type = type;
		this.icon = icon;
	}
	
	public ZtreeBean(String id, String pid, String name, int type, String icon,String nocheck)	{
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.type = type;
		this.icon = icon;
		this.nocheck = nocheck;
	}
	
	public ZtreeBean(String id, String pid, String name, int type, String icon,String nocheck,boolean checked,boolean chkDisabled)	{
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.type = type;
		this.icon = icon;
		this.nocheck = nocheck;
		this.checked = checked ? "true" : "false";
		this.chkDisabled = chkDisabled ? "true" : "false";
	}

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<ZtreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<ZtreeBean> children) {
		this.children = children;
	}
	
	public void addChild(ZtreeBean child) {
		if(children == null) {
			children = new ArrayList<ZtreeBean>();
		}
		children.add(child);
	}

	public boolean isAllChecked() {
		return isAllChecked;
	}

	public void setAllChecked(boolean isAllChecked) {
		this.isAllChecked = isAllChecked;
	}

	public String getCheck_Child_State() {
		return check_Child_State;
	}

	public void setCheck_Child_State(String check_Child_State) {
		this.check_Child_State = check_Child_State;
	}

	public String getNocheck() {
		return nocheck;
	}

	public void setNocheck(String nocheck) {
		this.nocheck = nocheck;
	}

	public String getChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(String chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
	
	

}
