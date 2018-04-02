package com.phicomm.doctor.service;

import com.phicomm.doctor.dataaccess.domain.Hospital;

public interface HospitalService {

	Hospital getByOpenid(String openid);
}
