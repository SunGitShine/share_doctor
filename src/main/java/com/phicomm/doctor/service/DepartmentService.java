package com.phicomm.doctor.service;

import java.util.List;

import com.phicomm.doctor.dataaccess.domain.Department;

public interface DepartmentService {

	List<Department> findParentDepartment();
	
	List<Department> findSonDepartment(Integer parentId);
}
