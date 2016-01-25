package com.mall.admin.vo.activity;

import java.util.Date;

import com.mall.admin.base._;


/**
 * 生成的太乱，重写了
 * 
 * @author 温德亮
 *
 */
public class Activity {
	
	/**
	 * 活动id
	 */
    private Long activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动前端展示名称
     */
    private String activityShowName;
    /**
     * 活动展示方式 0：首页， 1：导航栏
     */
    private Integer displayType;
	public static final int ACTIVITY_DISPLAY_TYPE = 0;
	
    /**
     * 选择标签 0：空 1：HOT 2：NEW 3:推荐
     */
    private Integer labelType;
    /**
     * 是否支持链接 1:支持  0：不支持
     */
    private Integer isLinkUrl;
    public static final int ACTIVITY_IS_LINK_URL_OPEN = 1; 
    public static final int ACTIVITY_IS_LINK_URL_CLOSE = 0;
    /**
     * 链接地址
     */
    private String linkUrl;
    /**
     * 列表显示第一位
     */
    private int firstIndex;
    
    /**
     * 活动类型
     * 参考ActivityType 枚举
     */
    private Byte activityType;
    public static final int ACTIVITY_TYPE_SECKILL = 0;
    public static final int ACTIVITY_TYPE_NORMAL = 1;
    public static final int ACTIVITY_TYPE_RECOMMEND = 5;
    public static final int ACTIVITY_TYPE_POPULAR = 8; //爆品活动
    public static final int ACTIVITY_TYPE_BRAND = 9; //品牌活动
    public static final int ACTIVITY_TYPE_SUPPLIER = 10; //供应商活动
    
    /**
     * 平台类型
     * 参考platformType 枚举类
     */
    private Byte platformType;
    public static final Byte ACTIVITY_PLANTFORM_TYPE_ALL = 0; //全平台
    
    /**
     * 活动开启方式
     * 0手工；1自动
     */
    private Byte openType;
    /**
     * 是否开启
     * openType = 手工 时 该值才起作用
     */
    private Byte isOpen;
    /**
     * 点击活动的类型 0 : new page  1 : hash change 2: layer
     */
    private Byte actionType;
    /**
     * 权重？？？不知道干嘛的
     */
    private Integer weight;
    /**
     * app图片地址
     */
    private String imageUrl;
    /**
     * h5图片地址
     */
    private String wapImageUrl;
    /**
     * 活动开始时间
     */
	private Date startDate;
	/**
	 * 活动结束时间
	 */
	private Date endDate;
	/**
	 * 创建时间
	 */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 操作人
     */
    private Long operator;
    /**
     * 是否展示
     * 是否显示 0：不显示 1：显示 对于不显示的活动可以通过banner跳转到该活动中。
     */
    private Byte isShow;
    
    /**
     * 逻辑删除
     */
    private Byte isDel;
    
    /**
     * 选择方案 1:优惠劵 2：积分 3：实物产品 4：虚拟产品
     * 参考枚举 ProgramType
     */
    private Byte programType;
    
    /**
     * 关联优惠卷批次号
     */
    private String batchIds;
    
    /**
     * 钱，单位值分，get,set方法做了转换
     */
    private long value;
    
    private long storageId;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityShowName() {
		return activityShowName;
	}

	public void setActivityShowName(String activityShowName) {
		this.activityShowName = activityShowName;
	}

	public Byte getActivityType() {
		return activityType;
	}

	public void setActivityType(Byte activityType) {
		this.activityType = activityType;
	}

	public Byte getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Byte platformType) {
		this.platformType = platformType;
	}

	public Byte getOpenType() {
		return openType;
	}

	public void setOpenType(Byte openType) {
		this.openType = openType;
	}

	public Byte getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Byte isOpen) {
		this.isOpen = isOpen;
	}

	public Byte getActionType() {
		return actionType;
	}

	public void setActionType(Byte actionType) {
		this.actionType = actionType;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getImageUrl() {
		if(_.isEmpty(imageUrl) || !imageUrl.startsWith("http")) {
			return "";
		}
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getWapImageUrl() {
		if(_.isEmpty(wapImageUrl) || !wapImageUrl.startsWith("http")) {
			return "";
		}
		return wapImageUrl;
	}

	public void setWapImageUrl(String wapImageUrl) {
		this.wapImageUrl = wapImageUrl;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Byte getIsShow() {
		return isShow;
	}

	public void setIsShow(Byte isShow) {
		this.isShow = isShow;
	}

	public Byte getIsDel() {
		return isDel;
	}

	public void setIsDel(Byte isDel) {
		this.isDel = isDel;
	}

	public Byte getProgramType() {
		return programType;
	}

	public void setProgramType(Byte programType) {
		this.programType = programType;
	}

	public String getBatchIds() {
		return batchIds;
	}

	public void setBatchIds(String batchIds) {
		this.batchIds = batchIds;
	}

	 /**
     * 钱，单位值分
     */
	public long getValue() {
		return value;
	}

	 /**
     * 钱，单位值分
     */
	public void setValue(long value) {
		this.value = value ;
	}

	public Integer getDisplayType() {
		return displayType;
	}

	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}

	public Integer getLabelType() {
		return labelType;
	}

	public void setLabelType(Integer labelType) {
		this.labelType = labelType;
	}

	public Integer getIsLinkUrl() {
		return isLinkUrl;
	}

	public void setIsLinkUrl(Integer isLinkUrl) {
		this.isLinkUrl = isLinkUrl;
	}

	public String getLinkUrl() {
		if(_.isEmpty(linkUrl)) {
			return "";
		}
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public long getStorageId() {
		return storageId;
	}

	public void setStorageId(long storageId) {
		this.storageId = storageId;
	}
}