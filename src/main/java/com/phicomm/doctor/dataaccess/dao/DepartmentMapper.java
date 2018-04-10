package com.phicomm.doctor.dataaccess.dao;

import java.util.List;

import com.phicomm.doctor.dataaccess.domain.Department;

public interface DepartmentMapper {

	List<Department> findParentDepartment();
	
	List<Department> findSonDepartment(Integer parentId);
}