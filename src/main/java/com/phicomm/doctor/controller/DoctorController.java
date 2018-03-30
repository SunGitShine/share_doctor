package com.phicomm.doctor.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phicomm.doctor.common.domain.BusinessException;
import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.dataaccess.domain.Doctor;
import com.phicomm.doctor.service.DoctorService;
import com.phicomm.doctor.util.ParameterUtil;
import com.phicomm.doctor.util.StringUtil;
import com.phicomm.doctor.util.ValidateUtil;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	@RequestMapping("sendSmsCode")
	public Result sendSmsCode(HttpServletRequest request) {
		
		String phone = ParameterUtil.getString("phone");
		ValidateUtil.isNotBlank(phone, "手机号不能为空");
		String smsCode = doctorService.sendSmsCode(phone, request.getSession());
		return ParameterUtil.commonSuccessResult("smsCode", smsCode);
	}

	@RequestMapping("/bindPhone")
	public Result bindPhone(HttpServletRequest request) {
		
		String phone = ParameterUtil.getString("phone");
		String smsCode = ParameterUtil.getString("smsCode");
		String openid = ParameterUtil.getString("openid");
		if(StringUtil.isEmpty(phone) || StringUtil.isEmpty(smsCode) || StringUtil.isEmpty(openid)) {
			throw new BusinessException("参数异常");
		}
		
		doctorService.bindPhone(phone, smsCode, openid, request.getSession());
		return ParameterUtil.commonSuccessResult();
	}
	
	@RequestMapping("completeInfo")
	public Result completeInfo() {
		
		Doctor doctor = ParameterUtil.parseObject(Doctor.class);
		return ParameterUtil.commonSuccessResult();
	}
}
