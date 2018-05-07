package com.phicomm.doctor.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.controller.BaseController;
import com.phicomm.doctor.dataaccess.domain.Doctor;
import com.phicomm.doctor.service.DoctorService;
import com.phicomm.doctor.service.response.DoctorResponse;
import com.phicomm.doctor.util.ParameterUtil;

@Controller
@RequestMapping("/web/doctor")
public class DoctorWebController extends BaseController{
	
	@Autowired
	private DoctorService doctorService;

	@RequestMapping("/findByPage")
	public Result findByPage() {
		
		String name = ParameterUtil.getString("name");
		Integer auditStatus = ParameterUtil.getInteger("auditStatus");
		PageQuery pageQuery = getPageQuery();
		
		List<Doctor> doctorList = doctorService.findByPageWeb(name, auditStatus, pageQuery);
		Integer doctorCount = doctorService.findCountWeb(name, auditStatus);
		
		return ParameterUtil.pageSuccessResult(doctorCount, doctorList);
	}
	
	@RequestMapping("/audit")
	public Result audit() {
		
		String openid = ParameterUtil.getString("openid");
		Integer auditStatus = ParameterUtil.getInteger("auditStatus");
		
		doctorService.audit(openid, auditStatus);
		
		return ParameterUtil.commonSuccessResult();
	}
	
	@RequestMapping("/findByOpneid")
	public Result findByOpneid() {
		
		String openid = ParameterUtil.getString("openid");
		DoctorResponse response = doctorService.findByOpenid(openid);
		return ParameterUtil.commonSuccessResult("doctor", response);
	}
}
