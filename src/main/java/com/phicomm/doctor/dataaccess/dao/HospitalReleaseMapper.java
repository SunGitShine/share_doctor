package com.phicomm.doctor.dataaccess.dao;

import org.apache.ibatis.annotations.Param;

import com.phicomm.doctor.dataaccess.domain.HospitalRelease;

public interface HospitalReleaseMapper {

	void deleteById(Integer id);
	
	void insert(HospitalRelease hospitalRelease);
	
	void updateStatus(@Param("id")Integer id, @Param("status")Integer status, @Param("auditStatus")Integer auditStatus);
}