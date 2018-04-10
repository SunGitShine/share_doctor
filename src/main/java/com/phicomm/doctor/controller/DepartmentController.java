package com.phicomm.doctor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.service.DepartmentService;
import com.phicomm.doctor.util.ParameterUtil;

@Controller
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping("/findParentDepartment")
	public Result findParentDepartment() {
		
		return ParameterUtil.commonSuccessResult("parentDepartments", departmentService.findParentDepartment());
	}
	
	@RequestMapping("/findSonDepartment")
	public Result findSonDepartment() {
		
		Integer parentId = ParameterUtil.getInteger("parentId");
		return ParameterUtil.commonSuccessResult("sonDepartments", departmentService.findSonDepartment(parentId));
	}
}
