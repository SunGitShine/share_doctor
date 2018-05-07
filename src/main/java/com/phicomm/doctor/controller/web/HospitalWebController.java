package com.phicomm.doctor.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.controller.BaseController;
import com.phicomm.doctor.dataaccess.domain.Hospital;
import com.phicomm.doctor.service.HospitalService;
import com.phicomm.doctor.service.response.HospitalResponse;
import com.phicomm.doctor.util.ParameterUtil;

@Controller
@RequestMapping("/web/hospital")
public class HospitalWebController extends BaseController{

	@Autowired
	private HospitalService hospitalService;
	
	@RequestMapping("/findByPageWeb")
	public Result findByPageWeb() {
		
		String name = ParameterUtil.getString("name");
		Integer auditStatus = ParameterUtil.getInteger("auditStatus");
		PageQuery pageQuery = getPageQuery();
		
		List<Hospital> hospitalList = hospitalService.findByPageWeb(name, auditStatus, pageQuery);
		Integer hospitalCount = hospitalService.findByCountWeb(name, auditStatus);
		
		return ParameterUtil.pageSuccessResult(hospitalCount, hospitalList);
	}
	
	@RequestMapping("/audit")
	public Result audit() {
		
		String openid = ParameterUtil.getString("openid");
		Integer auditStatus = ParameterUtil.getInteger("auditStatus");
		
		hospitalService.auditHospital(auditStatus, openid);
		return ParameterUtil.commonSuccessResult();
	}
	
	@RequestMapping("/findByOpenid")
	public Result findByOpenid() {
		
		String openid = ParameterUtil.getString("openid");
		HospitalResponse response = hospitalService.getByOpenid(openid);
		
		return ParameterUtil.commonSuccessResult("hospital", response);
	}
}
