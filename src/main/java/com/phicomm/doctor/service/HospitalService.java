package com.phicomm.doctor.service;

import java.util.List;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.domain.Hospital;
import com.phicomm.doctor.dataaccess.domain.HospitalRelease;
import com.phicomm.doctor.service.response.HospitalResponse;
import com.phicomm.doctor.service.response.ReleaseListResponse;

public interface HospitalService {

	HospitalResponse getByOpenid(String openid);
	
	void bindPhone(String phone, String openid);
	
	void completeInfo(Hospital hospital);
	
	void release(String openid, HospitalRelease release);
	
	void updateReleaseStatus(Integer releaseId, Integer status, Integer auditStatus);
	
	void deleteRelease(Integer releaseId);
	
	List<ReleaseListResponse> findReleaseListPage(String hospitalOpenid, String doctorOpenid, PageQuery pageQuery);
	
	Integer findReleaseCount(String hospitalOpenid, String doctorOpenid);
}
