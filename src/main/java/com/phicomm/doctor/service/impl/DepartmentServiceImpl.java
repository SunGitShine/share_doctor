package com.phicomm.doctor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.doctor.dataaccess.dao.DepartmentMapper;
import com.phicomm.doctor.dataaccess.domain.Department;
import com.phicomm.doctor.service.DepartmentService;
import com.phicomm.doctor.util.ValidateUtil;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService{
	
	@Autowired
	private DepartmentMapper departmentMapper;

	@Override
	public List<Department> findParentDepartment() {
		return departmentMapper.findParentDepartment();
	}

	@Override
	public List<Department> findSonDepartment(Integer parentId) {
		
		ValidateUtil.notNull(parentId, "科室父id不能为空");
		return departmentMapper.findSonDepartment(parentId);
	}

}
