package com.phicomm.doctor.service.request;

import java.util.Date;

public class ReleaseListRequest {

	private String hospitalOpenid;
	
	private Integer area;
	
	private Integer workTime;
	
	private Integer doctorTitle;
	
	private Integer departmentId;
	
	private Date startTime;
	
	private Date endTime;

	public String getHospitalOpenid() {
		return hospitalOpenid;
	}

	public void setHospitalOpenid(String hospitalOpenid) {
		this.hospitalOpenid = hospitalOpenid;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
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

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
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
}
