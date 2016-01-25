package com.mall.admin.vo.user;

public class UserAndCategory {

	public long user_category_id;
	public long user_id;
	public long category_id;
	public int is_del;
	public long create_time;
	public long createor;

	public long getUser_category_id() {
		return user_category_id;
	}

	public void setUser_category_id(long user_category_id) {
		this.user_category_id = user_category_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}

	public int getIs_del() {
		return is_del;
	}

	public void setIs_del(int is_del) {
		this.is_del = is_del;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public long getCreateor() {
		return createor;
	}

	public void setCreateor(long createor) {
		this.createor = createor;
	}

}
