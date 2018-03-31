package com.phicomm.doctor.service.response;

import java.util.Date;

import com.phicomm.doctor.dataaccess.domain.Doctor;

public class DoctorResponse extends Doctor{
	
	private Integer doctorReleseId;

	private Date startTime;
	
	private Date endTime;
	
	private Integer status;

	public Integer getDoctorReleseId() {
		return doctorReleseId;
	}

	public void setDoctorReleseId(Integer doctorReleseId) {
		this.doctorReleseId = doctorReleseId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
