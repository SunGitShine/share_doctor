package com.phicomm.doctor.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.controller.BaseController;
import com.phicomm.doctor.service.HospitalService;
import com.phicomm.doctor.service.response.ReleaseListResponse;
import com.phicomm.doctor.util.ParameterUtil;

@Controller
@RequestMapping("/web/hospitalRelese")
public class HospitalReleseWebController extends BaseController{
	
	@Autowired
	private HospitalService hospitalService;

	@RequestMapping("/findByPageWeb")
	public Result findByPageWeb() {
		
		String name = ParameterUtil.getString("name");
		Integer auditStatus = ParameterUtil.getInteger("auditStatus");
		PageQuery pageQuery = getPageQuery();
		
		List<ReleaseListResponse> releaseList = hospitalService.findReleaseListPageWeb(name, auditStatus, pageQuery);
		Integer hospitalReleaseCount = hospitalService.findReleaseListCountWeb(name, auditStatus);
		
		return ParameterUtil.pageSuccessResult(hospitalReleaseCount, releaseList);
	}
	
	@RequestMapping("/audit")
	public Result audit() {
		
		Integer releaseId = ParameterUtil.getInteger("releaseId");
		Integer auditStatus = ParameterUtil.getInteger("auditStatus");
		
		hospitalService.updateReleaseStatus(releaseId, null, auditStatus);
		return ParameterUtil.commonSuccessResult();
	}
	
	@RequestMapping("/findByReleaseId")
	public Result findByReleaseId() {
		
		Integer releaseId = ParameterUtil.getInteger("releaseId");
		ReleaseListResponse release = hospitalService.findReleaseById(releaseId);
		
		return ParameterUtil.commonSuccessResult("release", release);
	}
}
