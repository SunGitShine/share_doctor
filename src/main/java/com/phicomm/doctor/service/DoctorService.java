package com.phicomm.doctor.service;

import java.util.List;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.domain.Doctor;
import com.phicomm.doctor.dataaccess.domain.DoctorRelese;
import com.phicomm.doctor.service.response.DoctorListResponsePage;
import com.phicomm.doctor.service.response.DoctorResponse;

public interface DoctorService {

	void bindPhone(String phone, String openid, String name, String headImgUrl);
	
	void completeInfo(Doctor doctor);
	
	DoctorResponse findByOpenid(String openid);
	
	void relese(String openid, DoctorRelese doctorRelese);
	
	void updateReleseStatus(Integer doctorReleseId, Integer status);
	
	void deleteRelese(Integer doctorReleseId);
	
	DoctorListResponsePage findDoctorListPage(String hospitalOpenid, Integer departmentId, PageQuery pageQuery);
	
	List<Doctor> findByPageWeb(String name, Integer auditStatus, PageQuery pageQuery);
	
	Integer findCountWeb(String name, Integer auditStatus);
	
	void audit(String openid, Integer auditStatus);
}
