package com.phicomm.doctor.dataaccess.dao;

import com.phicomm.doctor.dataaccess.domain.DoctorRelese;

public interface DoctorReleseMapper {

	DoctorRelese getByDoctorId(Integer doctorId);
	
	void insert(DoctorRelese doctorRelese);
	
	void updateStatus(DoctorRelese doctorRelese);
}