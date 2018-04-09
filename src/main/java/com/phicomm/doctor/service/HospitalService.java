package com.phicomm.doctor.service;

import com.phicomm.doctor.dataaccess.domain.Hospital;
import com.phicomm.doctor.dataaccess.domain.HospitalRelease;

public interface HospitalService {

	Hospital getByOpenid(String openid);
	
	void completeInfo(Hospital hospital);
	
	void release(String openid, HospitalRelease release);
}
