package com.phicomm.doctor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.dao.PositionMapper;
import com.phicomm.doctor.dataaccess.domain.Position;
import com.phicomm.doctor.service.PositionService;
import com.phicomm.doctor.util.ValidateUtil;

@Service("positionService")
public class PositionServiceImpl implements PositionService{

	@Autowired
	private PositionMapper positionMapper;

	@Override
	public void deleteById(Integer id) {
		
		ValidateUtil.notNull(id, "id不能为空");
		positionMapper.deleteById(id);
	}

	@Override
	public void insert(Position position) {
		positionMapper.insert(position);
	}

	@Override
	public void updateStatus(Integer status, Integer id) {
		
		ValidateUtil.notNull(id, "id不能为空");
		ValidateUtil.notNull(status, "状态不能为空");
		positionMapper.updateStatus(status, id);
	}

	@Override
	public List<Position> findByPage(PageQuery pageQuery) {
		return positionMapper.findByPage(pageQuery);
	}

	@Override
	public Integer findCount() {
		return positionMapper.findCount();
	}

	@Override
	public Position findById(Integer id) {
		ValidateUtil.notNull(id, "id不能为空");
		return positionMapper.findById(id);
	}

	@Override
	public List<Position> findByPageWeb(Integer status, PageQuery pageQuery) {
		return positionMapper.findByPageWeb(status, pageQuery);
	}

	@Override
	public Integer findCountWeb(Integer status) {
		return positionMapper.findCountWeb(status);
	}

	@Override
	public void delete(Integer id) {
		positionMapper.delete(id);
	}
	
	
}
