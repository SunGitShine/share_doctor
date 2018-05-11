package com.phicomm.doctor.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.controller.BaseController;
import com.phicomm.doctor.dataaccess.domain.Position;
import com.phicomm.doctor.service.PositionService;
import com.phicomm.doctor.util.ParameterUtil;

@Controller
@RequestMapping("/web/position")
public class PositionWebController extends BaseController{
	
	@Autowired
	private PositionService positionService;

	@RequestMapping("/findById")
	public Result findById() {
		
		Integer positionId = ParameterUtil.getInteger("positionId");
		Position position = positionService.findById(positionId);
		return ParameterUtil.commonSuccessResult("position", position);
	}
	
	@RequestMapping("/findByPage")
	public Result findByPage() {
		
		Integer status = ParameterUtil.getInteger("status");
		PageQuery pageQuery = getPageQuery();
		List<Position> positions = positionService.findByPageWeb(status, pageQuery);
		Integer count = positionService.findCountWeb(status);
		return ParameterUtil.pageSuccessResult(count, positions);
	}
	
	@RequestMapping("/add")
	public Result add() {
		
		Position position = ParameterUtil.parseObject(Position.class);
		positionService.insert(position);
		
		return ParameterUtil.commonSuccessResult();
	}
	
	@RequestMapping("/updateStatus")
	public Result updateStatus() {
		
		Integer positionId = ParameterUtil.getInteger("positionId");
		Integer status = ParameterUtil.getInteger("status");
		positionService.updateStatus(status, positionId);
		
		return ParameterUtil.commonSuccessResult();
	}
	
	@RequestMapping("/delete")
	public Result delete() {
		
		Integer positionId = ParameterUtil.getInteger("positionId");
		positionService.delete(positionId);
		return ParameterUtil.commonSuccessResult();
	}
}
