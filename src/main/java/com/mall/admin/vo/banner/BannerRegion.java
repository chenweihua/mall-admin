package com.mall.admin.vo.banner;


public class BannerRegion {
    private long bannerRegionId;

    private long bannerId;

    private long regionId;

    private int regionType;
    public final static int REGION_TYPE_ALL = 0;
    public final static int REGION_TYPE_STORAGE = 1;
    public final static int REGION_TYPE_CITY = 2;

    private int isDel;
    
    public long getBannerRegionId() {
        return bannerRegionId;
    }

    public void setBannerRegionId(long bannerRegionId) {
        this.bannerRegionId = bannerRegionId;
    }

    public long getBannerId() {
        return bannerId;
    }

    public void setBannerId(long bannerId) {
        this.bannerId = bannerId;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public int getRegionType() {
        return regionType;
    }

    public void setRegionType(int regionType) {
        this.regionType = regionType;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}