package com.phicomm.doctor.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phicomm.doctor.common.domain.BusinessException;
import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.common.domain.ResultCode;
import com.phicomm.doctor.dataaccess.domain.Hospital;
import com.phicomm.doctor.service.CommonService;
import com.phicomm.doctor.service.DoctorService;
import com.phicomm.doctor.service.HospitalService;
import com.phicomm.doctor.service.response.DoctorResponse;
import com.phicomm.doctor.service.response.HospitalResponse;
import com.phicomm.doctor.util.ParameterUtil;
import com.phicomm.doctor.util.StringUtil;
import com.phicomm.doctor.util.ValidateUtil;

@Controller
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@RequestMapping("/findByOpneid")
	public Result findByOpneid() {
		
		String openid = ParameterUtil.getString("openid");
		
		DoctorResponse doctor = doctorService.findByOpenid(openid);
		HospitalResponse hospital = hospitalService.getByOpenid(openid);
		
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
	
	@RequestMapping("/sendSmsCode")
	public Result sendSmsCode(HttpServletRequest request) {
		
		String phone = ParameterUtil.getString("phone");
		ValidateUtil.isNotBlank(phone, "手机号不能为空");
		String smsCode = commonService.sendSmsCode(phone, request.getSession());
		return ParameterUtil.commonSuccessResult("smsCode", smsCode);
	}
	
	@RequestMapping("/bindPhone")
	public Result bindPhone(HttpServletRequest request) {
		
		String phone = ParameterUtil.getString("phone");
		String smsCode = ParameterUtil.getString("smsCode");
		String openid = ParameterUtil.getString("openid");
		Integer identityType = ParameterUtil.getInteger("identityType");
		if(StringUtil.isEmpty(phone) || StringUtil.isEmpty(smsCode) || StringUtil.isEmpty(openid)) {
			throw new BusinessException("参数异常");
		}
		ValidateUtil.notNull(identityType, "身份类型不能为空");
		
		commonService.checkSmsCode(phone, smsCode, request.getSession());//校验短信验证码
		
		DoctorResponse doctor = doctorService.findByOpenid(openid);
		HospitalResponse hospital = hospitalService.getByOpenid(openid);
		
		ValidateUtil.isNotTrue(doctor != null, "该账户已存在医生库");
		ValidateUtil.isNotTrue(hospital != null, "该账户已存在医院库");
		
		if(identityType == 1) {
			doctorService.bindPhone(phone, smsCode, openid, request.getSession());
		}else if(identityType == 2) {
			hospitalService.bindPhone(phone, openid);
		}
		
		return ParameterUtil.commonSuccessResult();
	}
}
