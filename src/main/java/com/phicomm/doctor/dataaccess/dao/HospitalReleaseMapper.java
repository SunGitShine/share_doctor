package com.phicomm.doctor.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.domain.HospitalRelease;
import com.phicomm.doctor.service.request.ReleaseListRequest;
import com.phicomm.doctor.service.response.ReleaseListResponse;

public interface HospitalReleaseMapper {

	void deleteById(Integer id);
	
	void insert(HospitalRelease hospitalRelease);
	
	void updateStatus(@Param("id")Integer id, @Param("status")Integer status, @Param("auditStatus")Integer auditStatus);
	
	List<ReleaseListResponse> findReleaseListPage(@Param("request")ReleaseListRequest request, @Param("departmentId")Integer departmentId,
			@Param("pageQuery")PageQuery pageQuery);
	
	Integer findReleaseCount(@Param("request")ReleaseListRequest request, @Param("departmentId")Integer departmentId);
	
	ReleaseListResponse findById(@Param("releaseId")Integer releaseId);
	
	List<HospitalRelease> findListByHid(@Param("hospitalId")Integer hospitalId);
}