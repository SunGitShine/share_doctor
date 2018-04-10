package com.phicomm.doctor.service;

import com.phicomm.doctor.dataaccess.domain.Hospital;
import com.phicomm.doctor.dataaccess.domain.HospitalRelease;
import com.phicomm.doctor.service.response.HospitalResponse;

public interface HospitalService {

	HospitalResponse getByOpenid(String openid);
	
	void bindPhone(String phone, String openid);
	
	void completeInfo(Hospital hospital);
	
	void release(String openid, HospitalRelease release);
	
	void updateReleaseStatus(Integer releaseId, Integer status, Integer auditStatus);
	
	void deleteRelease(Integer releaseId);
}
