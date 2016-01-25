package com.mall.admin.vo.user;

import java.util.List;

import com.mall.admin.vo.category.Category;
import com.mall.admin.vo.mallbase.Storage;

public class User {

	/**
	 * 第三方用户类型
	 */
	public static final int USER_TYPE_THIRD_SELLER = 2;
	/**
	 * 商户类型
	 */
	public static final int MERCHANT_USER = 3;

	/**
	 * 商户角色id
	 */
	public static final int MERCHANT_ROLE = 20;

	public long user_id;
	public String user_name;
	public String account;
	public transient String password;
	public transient String token;
	public transient int salt;
	public int user_type;
	public int is_del;
	public int is_all_category;
	public int is_all_storage;

	public Role role;
	/**
	 * 用户负责的rdc仓
	 */
	public List<Storage> rdcStorageList;
	/**
	 * 用户负责的ldc仓
	 */
	public List<Storage> ldcStorageList;
	/**
	 * 用户负责的虚拟仓
	 */
	public List<Storage> vmStorageList;
	/**
	 * 用户负责的所有仓
	 */
	public List<Storage> allStorageList;

	public List<Menu> menuList;

	public List<Category> categoryList;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getSalt() {
		return salt;
	}

	public void setSalt(int salt) {
		this.salt = salt;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public int getIs_del() {
		return is_del;
	}

	public void setIs_del(int is_del) {
		this.is_del = is_del;
	}

	public int getIs_all_category() {
		return is_all_category;
	}

	public void setIs_all_category(int is_all_category) {
		this.is_all_category = is_all_category;
	}

	public int getIs_all_storage() {
		return is_all_storage;
	}

	public void setIs_all_storage(int is_all_storage) {
		this.is_all_storage = is_all_storage;
	}

	public List<Storage> getRdcStorageList() {
		return rdcStorageList;
	}

	public void setRdcStorageList(List<Storage> rdcStorageList) {
		this.rdcStorageList = rdcStorageList;
	}

	public List<Storage> getLdcStorageList() {
		return ldcStorageList;
	}

	public void setLdcStorageList(List<Storage> ldcStorageList) {
		this.ldcStorageList = ldcStorageList;
	}

	public List<Storage> getAllStorageList() {
		return allStorageList;
	}

	public void setAllStorageList(List<Storage> allStorageList) {
		this.allStorageList = allStorageList;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Storage> getVmStorageList() {
		return vmStorageList;
	}

	public void setVmStorageList(List<Storage> vmStorageList) {
		this.vmStorageList = vmStorageList;
	}
}
