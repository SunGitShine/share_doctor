package com.phicomm.doctor.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.domain.Position;

public interface PositionMapper {

	Position findById(Integer id);
	
	void deleteById(Integer id);
	
	void insert(Position position);
	
	void updateStatus(@Param("status")Integer status, @Param("id")Integer id);
	
	List<Position> findByPage(@Param("pageQuery")PageQuery pageQuery);
	
	Integer findCount();
}
