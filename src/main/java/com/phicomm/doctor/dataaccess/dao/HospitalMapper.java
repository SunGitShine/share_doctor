package com.phicomm.doctor.dataaccess.dao;

import org.apache.ibatis.annotations.Param;

import com.phicomm.doctor.dataaccess.domain.Hospital;

public interface HospitalMapper {

	Hospital getByOpenid(@Param("openid") String openid);
}