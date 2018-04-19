package com.phicomm.doctor.service.response;

import java.util.Date;

import com.alibaba.fastjson.JSON;

public class DoctorListResponse {

	private String openid;
	private String name;
	private String headImgUrl;
	private String departmentName;
	private Integer workTime;
	private Integer doctorTitle;
	private Date startTime;
	private Date endTime;
	private Object call;
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Integer getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Integer workTime) {
		this.workTime = workTime;
	}
	public Integer getDoctorTitle() {
		return doctorTitle;
	}
	public void setDoctorTitle(Integer doctorTitle) {
		this.doctorTitle = doctorTitle;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Object getCall() {
		return call;
	}
	public void setCall(Object call) {
		this.call = JSON.parse(call.toString());
	}
}
