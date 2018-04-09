package com.phicomm.doctor.service;

import com.phicomm.doctor.dataaccess.domain.Hospital;
import com.phicomm.doctor.dataaccess.domain.HospitalRelease;

public interface HospitalService {

	Hospital getByOpenid(String openid);
	
	void completeInfo(Hospital hospital);
	
	void release(String openid, HospitalRelease release);
	
	void updateReleaseStatus(Integer releaseId, Integer status, Integer auditStatus);
	
	void deleteRelease(Integer releaseId);
}
