package com.mall.admin.vo.banner;

import java.sql.Timestamp;

import com.mall.admin.util._;

public class BannerCollege {
    private long bannerCollegeId;

    private long bannerId;

    private long collegeId;

    private int isDel;

    private long operator;

    private Timestamp createTime;

    private Timestamp updateTime;

    public BannerCollege(){
    	this.createTime = _.currentTime();
    	this.updateTime = _.currentTime();
    }
    
    public long getBannerCollegeId() {
        return bannerCollegeId;
    }

    public void setBannerCollegeId(long bannerCollegeId) {
        this.bannerCollegeId = bannerCollegeId;
    }

    public long getBannerId() {
        return bannerId;
    }

    public void setBannerId(long bannerId) {
        this.bannerId = bannerId;
    }

    public long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(long collegeId) {
        this.collegeId = collegeId;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public long getOperator() {
        return operator;
    }

    public void setOperator(long operator) {
        this.operator = operator;
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
}