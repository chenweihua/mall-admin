package com.mall.admin.vo.user;

/**
 * 用户角色表
 * 
 * @author Administrator
 *
 */
public class Role {

	public long role_id;
	public String role_name;
	public String remark;
	public int is_del;
	public int admin_flag;

	public long getRole_id() {
		return role_id;
	}

	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIs_del() {
		return is_del;
	}

	public void setIs_del(int is_del) {
		this.is_del = is_del;
	}

	public int getAdmin_flag() {
		return admin_flag;
	}

	public void setAdmin_flag(int admin_flag) {
		this.admin_flag = admin_flag;
	}

}
