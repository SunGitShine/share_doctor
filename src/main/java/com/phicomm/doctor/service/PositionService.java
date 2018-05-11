package com.phicomm.doctor.service;

import java.util.List;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.domain.Position;

public interface PositionService {

	void deleteById(Integer id);

	Position findById(Integer id);
	
	void insert(Position position);
	
	void updateStatus(Integer status, Integer id);
	
	List<Position> findByPage(PageQuery pageQuery);
	
	Integer findCount();
	
	List<Position> findByPageWeb(Integer status, PageQuery pageQuery);
	
	Integer findCountWeb(Integer status);
	
	void delete(Integer id);
}
