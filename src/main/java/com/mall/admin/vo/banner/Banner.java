package com.mall.admin.vo.banner;

import java.sql.Timestamp;

public class Banner {
    private long bannerId;

    private String bannerName;

    private String imageUrl;
    /**
     * banner高度
     */
    private int bannerHeight;

    private String hrefUrl;
    
    private String hrefUrlStr;
    
    private String newHrefUrl;//基于协议的
    
    // Banner类型位置0：首页 1：活动页 2：类目页
    private int bannerPosition;
    public final static int BANNER_POSITION_HOME = 0;
    public final static int BANNER_POSITION_ACTIVITY = 1;
    public final static int BANNER_POSITION_CATEGORY = 2;
    
    // 关联id
 	// 如果是首页，值为0；
 	// 如果是活动，值为活动id。
 	// 如果是类目，值为类目id。
 	// 活动id，如果是首页，值为0
    private long connectId;
    private String connectStr;

    private int bannerType;
	public final static int BANNER_TYPE_ALL = 0;//全部
	public final static int BANNER_TYPE_WAP = 1;//wap
	public final static int BANNER_TYPE_APP = 2;//app
	
	// 点击banner触发的动作类型
	// 1：跳转到活动banner_href_url是activity:{acitivity_id};
	// 2：跳转到类目banner_href_url是category:{category_id};
	// 3：跳转到url，url可以为空。href_url是要跳转的地址
    private int actionType;
	public final static int ACTION_TYPE_ACTIVITY = 1;
	public final static int ACTION_TYPE_CATEGORY = 2;
	public final static int ACTION_TYPE_URL = 3;
    
    private int needLogin;

    private long weight;

    private String bannerDesc;

    private int isDel;

    private Timestamp createTime;

    private Timestamp updateTime;

    private long operator;

    private Timestamp startTime;
    
    private String startTimeStr;

    private Timestamp endTime;
    
    private String endTimeStr;
    
    /**
     * 高宽比 ，保留两位小数
     */
    private String heigthWidthRatio;

    public long getBannerId() {
        return bannerId;
    }

    public void setBannerId(long bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getBannerHeight() {
		return bannerHeight;
	}

	public void setBannerHeight(int bannerHeight) {
		this.bannerHeight = bannerHeight;
	}

	public String getHrefUrl() {
        return hrefUrl;
    }

    public void setHrefUrl(String hrefUrl) {
        this.hrefUrl = hrefUrl;
    }

    public int getBannerPosition() {
        return bannerPosition;
    }

    public void setBannerPosition(int bannerPosition) {
        this.bannerPosition = bannerPosition;
    }

    public long getConnectId() {
		return connectId;
	}

	public void setConnectId(long connectId) {
		this.connectId = connectId;
	}

	public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getNeedLogin() {
		return needLogin;
	}

	public void setNeedLogin(int needLogin) {
		this.needLogin = needLogin;
	}

	public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public String getBannerDesc() {
        return bannerDesc;
    }

    public void setBannerDesc(String bannerDesc) {
        this.bannerDesc = bannerDesc;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public long getOperator() {
        return operator;
    }

    public void setOperator(long operator) {
        this.operator = operator;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

	public String getHrefUrlStr() {
		return hrefUrlStr;
	}

	public void setHrefUrlStr(String hrefUrlStr) {
		this.hrefUrlStr = hrefUrlStr;
	}

	public String getNewHrefUrl() {
		return newHrefUrl;
	}

	public void setNewHrefUrl(String newHrefUrl) {
		this.newHrefUrl = newHrefUrl;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getConnectStr() {
		return connectStr;
	}

	public void setConnectStr(String connectStr) {
		this.connectStr = connectStr;
	}

	public String getHeigthWidthRatio() {
		return heigthWidthRatio;
	}

	public void setHeigthWidthRatio(String heigthWidthRatio) {
		this.heigthWidthRatio = heigthWidthRatio;
	}
	
	
	
}