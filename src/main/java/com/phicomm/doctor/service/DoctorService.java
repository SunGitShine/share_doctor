package com.phicomm.doctor.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.domain.Doctor;
import com.phicomm.doctor.dataaccess.domain.DoctorRelese;
import com.phicomm.doctor.service.response.DoctorResponse;

public interface DoctorService {

	String sendSmsCode(String phone, HttpSession session);
	
	void bindPhone(String phone, String smsCode, String openid, HttpSession session);
	
	void completeInfo(Doctor doctor);
	
	DoctorResponse findByOpenid(String openid);
	
	Integer totalCount(Integer departmentId, Integer hospitalId);
	
	List<DoctorResponse> findByPage(Integer departmentId, Integer hospitalId, PageQuery pageQuery);
	
	void relese(String openid, DoctorRelese doctorRelese);
	
	void updateReleseStatus(Integer doctorReleseId, Integer status);
	
	void deleteRelese(Integer doctorReleseId);
}
