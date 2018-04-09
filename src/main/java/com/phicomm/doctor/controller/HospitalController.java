package com.phicomm.doctor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.dataaccess.domain.Hospital;
import com.phicomm.doctor.dataaccess.domain.HospitalRelease;
import com.phicomm.doctor.service.HospitalService;
import com.phicomm.doctor.util.ParameterUtil;

@Controller
@RequestMapping("/hospital")
public class HospitalController {
	
	@Autowired
	private HospitalService hospitalService;

	@RequestMapping("/completeInfo")
	public Result completeInfo() {
		
		Hospital hospital = ParameterUtil.parseObject(Hospital.class);
		hospitalService.completeInfo(hospital);
		return ParameterUtil.commonSuccessResult();
	}
	
	@RequestMapping("/release")
	public Result release() {
		
		HospitalRelease release = ParameterUtil.parseObject(HospitalRelease.class);
		String openid = ParameterUtil.getString("openid");
		hospitalService.release(openid, release);
		return ParameterUtil.commonSuccessResult();
	}
}
