package com.phicomm.doctor.dataaccess.dao;

import org.apache.ibatis.annotations.Param;

import com.phicomm.doctor.dataaccess.domain.Doctor;

public interface DoctorMapper {

	Doctor getByOpenid(@Param("openid") String openid);
	
	void insert(Doctor doctor);
	
	void updateBySelective(Doctor doctor);
}