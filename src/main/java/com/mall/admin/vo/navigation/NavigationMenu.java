package com.mall.admin.vo.navigation;

public class NavigationMenu {

	/**
	 * 菜单类型
	 */
	public static final int MENU_TYPE_ACTIVITY = 1; // 活动
	public static final int MENU_TYPE_GOODS = 2; // 商品
	
	/**
	 * 根节点/叶节点
	 */
	public static final int LEVEL_ROOT = 1;
	public static final int LEVEL_LEAF = 2;

	public long navMenuId;
	public long navigationId;
	public String menuName;
	public String showName;
	public int weight;
	public int menuType;
	public long createTime;
	public long updateTime;
	public long creator;
	public long operator;
	public int isDel;
	public long pid;
	public int level;
	public int isShow;

	public long getNavMenuId() {
		return navMenuId;
	}

	public void setNavMenuId(long navMenuId) {
		this.navMenuId = navMenuId;
	}

	public long getNavigationId() {
		return navigationId;
	}

	public void setNavigationId(long navigationId) {
		this.navigationId = navigationId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getMenuType() {
		return menuType;
	}

	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getCreator() {
		return creator;
	}

	public void setCreator(long creator) {
		this.creator = creator;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

}
