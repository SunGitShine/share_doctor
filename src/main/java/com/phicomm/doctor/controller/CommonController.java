package com.phicomm.doctor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.common.domain.ResultCode;
import com.phicomm.doctor.dataaccess.domain.Hospital;
import com.phicomm.doctor.service.DoctorService;
import com.phicomm.doctor.service.HospitalService;
import com.phicomm.doctor.service.response.DoctorResponse;
import com.phicomm.doctor.util.ParameterUtil;

@Controller
@RequestMapping("/common")
public class CommonController {

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@RequestMapping("/findByOpneid")
	public Result findByOpneid() {
		
		String openid = ParameterUtil.getString("openid");
		
		DoctorResponse doctor = doctorService.findByOpenid(openid);
		Hospital hospital = hospitalService.getByOpenid(openid);
		
		Integer identity = null;
		Result result = new Result(ResultCode.COMMON_SUCCESS, true);
		if(doctor == null && hospital == null) {
			identity = 0;
		}
		if(doctor != null) {
			identity = 1;
			result.setProperty("response", doctor);
		}
		if(hospital != null) {
			identity = 2;
			result.setProperty("response", hospital);
		}
		result.setProperty("identity", identity);
		return result;
	}
}
