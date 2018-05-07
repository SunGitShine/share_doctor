package com.phicomm.doctor.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.domain.Hospital;

public interface HospitalMapper {

	Hospital getByOpenid(@Param("openid") String openid);
	
	void bindPhone(Hospital hospital);
	
	void completeInfo(Hospital hospital);
	
	List<Hospital> findByPageWeb(@Param("name") String name,
			@Param("auditStatus") Integer auditStatus,
			@Param("pageQuery") PageQuery pageQuery);
	
	Integer findByCountWeb(@Param("name") String name,
			@Param("auditStatus") Integer auditStatus);
	
	void audit(@Param("auditStatus")Integer auditStatus,
			@Param("openid")String openid);
}