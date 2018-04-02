package com.phicomm.doctor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.doctor.dataaccess.dao.HospitalMapper;
import com.phicomm.doctor.dataaccess.domain.Hospital;
import com.phicomm.doctor.service.HospitalService;
import com.phicomm.doctor.util.ValidateUtil;

@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService{

	@Autowired
	private HospitalMapper hospitalMapper;

	@Override
	public Hospital getByOpenid(String openid) {
		ValidateUtil.isNotBlank(openid, "openid不能为空");
		return hospitalMapper.getByOpenid(openid);
	}
	
}
