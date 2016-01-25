package com.mall.admin.vo.storage;

import java.sql.Timestamp;

public class StorageCollege {
    private Long storageCollegeId;

    private Long storageId;

    private Long collegeId;

    private Timestamp createTime;

    public Long getStorageCollegeId() {
        return storageCollegeId;
    }

    public void setStorageCollegeId(Long storageCollegeId) {
        this.storageCollegeId = storageCollegeId;
    }

    public Long getStorageId() {
        return storageId;
    }

    public void setStorageId(Long storageId) {
        this.storageId = storageId;
    }

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}