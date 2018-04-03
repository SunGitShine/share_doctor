package com.phicomm.doctor.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phicomm.doctor.common.domain.BusinessException;
import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.dataaccess.domain.Doctor;
import com.phicomm.doctor.dataaccess.domain.DoctorRelese;
import com.phicomm.doctor.service.DoctorService;
import com.phicomm.doctor.service.response.DoctorResponse;
import com.phicomm.doctor.util.ParameterUtil;
import com.phicomm.doctor.util.StringUtil;
import com.phicomm.doctor.util.ValidateUtil;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	@RequestMapping("/sendSmsCode")
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
	
	@RequestMapping("/completeInfo")
	public Result completeInfo() {
		
		Doctor doctor = ParameterUtil.parseObject(Doctor.class);
		doctorService.completeInfo(doctor);
		return ParameterUtil.commonSuccessResult();
	}
	
	@RequestMapping("findByOpenid")
	public Result findByOpenid(){
		
		String openid = ParameterUtil.getString("openid");
		DoctorResponse response = doctorService.findByOpenid(openid);
		return ParameterUtil.commonSuccessResult("doctor", response);
	}
	
	@RequestMapping("/findByPage")
	public Result findByPage(){
		
		Integer departmentId = ParameterUtil.getInteger("departmentId");
		Integer hospitalId = ParameterUtil.getInteger("hospitalId");
		return null;
	}
	
	@RequestMapping("/relese")
	public Result relese(){
		
		String openid = ParameterUtil.getString("openid");
		DoctorRelese relese = ParameterUtil.parseObject(DoctorRelese.class);
		ValidateUtil.isNotBlank(openid, "openid不能为空");
		ValidateUtil.notNull(relese.getStartTime(), "开始时间不能为空");
		ValidateUtil.notNull(relese.getEndTime(), "结束时间不能为空");
		
		doctorService.relese(openid, relese);
		return ParameterUtil.commonSuccessResult();
	}
	
	@RequestMapping("/updateReleseStatus")
	public Result updateReleseStatus(){
		
		Integer doctorReleseId = ParameterUtil.getInteger("doctorReleseId");
		Integer status = ParameterUtil.getInteger("status");
		doctorService.updateReleseStatus(doctorReleseId, status);
		return ParameterUtil.commonSuccessResult();
	}
	
	@RequestMapping("/deleteRelese")
	public Result deleteRelese() {
		
		Integer doctorReleseId = ParameterUtil.getInteger("doctorReleseId");
		doctorService.deleteRelese(doctorReleseId);
		return ParameterUtil.commonSuccessResult();
	}
}
