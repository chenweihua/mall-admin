package com.mall.admin.vo.express;

import java.sql.Timestamp;

public class ExpressBill {
	private long id;
	/**
	  - rowkey 字符串id，标记一条记录的唯一id
	  - mendianId 门店id
	  - kuaidiId 快递id
	  - kuaidiNo 快递单号
	  - phoneNo 电话号
	  - picurl 上传图片地址
	  - time 上传时间戳(单位ms)，这个参数也是后面修改时必须提交的
	  - name 收件人姓名
	  - address 收件人地址
	  - mark 任意字符串标注，建议存json字符串，以后扩展方便
	 */
	private String rowkey;
	private String mendianId;
	private String kuaidiId;
	private String kuaidiNo;
	private String phoneNo;
	private String picurl;
	private String name;
	private String address;
	private String mark;
	private Timestamp time;
	private Timestamp createTime;
	
	private String schoolName;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRowkey() {
		return rowkey;
	}
	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}
	public String getMendianId() {
		return mendianId;
	}
	public void setMendianId(String mendianId) {
		this.mendianId = mendianId;
	}
	public String getKuaidiId() {
		return kuaidiId;
	}
	public void setKuaidiId(String kuaidiId) {
		this.kuaidiId = kuaidiId;
	}
	public String getKuaidiNo() {
		return kuaidiNo;
	}
	public void setKuaidiNo(String kuaidiNo) {
		this.kuaidiNo = kuaidiNo;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
