package com.phicomm.doctor.service;

import java.util.List;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.domain.Doctor;
import com.phicomm.doctor.dataaccess.domain.DoctorRelese;
import com.phicomm.doctor.service.response.DoctorResponse;

public interface DoctorService {

	void bindPhone(String phone, String openid, String name, String headImgUrl);
	
	void completeInfo(Doctor doctor);
	
	DoctorResponse findByOpenid(String openid);
	
	Integer totalCount(Integer departmentId, Integer hospitalId);
	
	List<DoctorResponse> findByPage(Integer departmentId, Integer hospitalId, PageQuery pageQuery);
	
	void relese(String openid, DoctorRelese doctorRelese);
	
	void updateReleseStatus(Integer doctorReleseId, Integer status);
	
	void deleteRelese(Integer doctorReleseId);
}
