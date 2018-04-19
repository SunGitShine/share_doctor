package com.phicomm.doctor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.dataaccess.domain.Doctor;
import com.phicomm.doctor.dataaccess.domain.DoctorRelese;
import com.phicomm.doctor.service.DoctorService;
import com.phicomm.doctor.service.response.DoctorListResponse;
import com.phicomm.doctor.service.response.DoctorListResponsePage;
import com.phicomm.doctor.service.response.DoctorResponse;
import com.phicomm.doctor.util.ParameterUtil;
import com.phicomm.doctor.util.ValidateUtil;

@Controller
@RequestMapping("/doctor")
public class DoctorController extends BaseController{
	
	@Autowired
	private DoctorService doctorService;
	
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
	
	@RequestMapping("/findDoctorListPage")
	public Result findDoctorListPage(){
		
		String hospitalOpenid = ParameterUtil.getString("hospitalOpenid");
		Integer departmentId = ParameterUtil.getInteger("departmentId");
		PageQuery pageQuery = getPageQuery();
		
		DoctorListResponsePage responses = doctorService.findDoctorListPage(hospitalOpenid, departmentId, pageQuery);
		
		return ParameterUtil.pageSuccessResult(responses.getCount(), responses.getResponses());
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
