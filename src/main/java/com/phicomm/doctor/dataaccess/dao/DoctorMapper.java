package com.phicomm.doctor.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.domain.Doctor;
import com.phicomm.doctor.service.request.DoctorListRequest;
import com.phicomm.doctor.service.response.DoctorListResponse;

public interface DoctorMapper {

	Doctor getByOpenid(@Param("openid") String openid);
	
	void insert(Doctor doctor);
	
	void updateBySelective(Doctor doctor);
	
	List<DoctorListResponse> findByPage(@Param("requestList")List<DoctorListRequest> requestList, 
			@Param("departmentId")Integer departmentId,
			@Param("pageQuery")PageQuery pageQuery);
	
	Integer findCount(@Param("requestList")List<DoctorListRequest> requestList,
			@Param("departmentId")Integer departmentId);
}