package com.phicomm.doctor.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.phicomm.doctor.common.domain.BusinessException;
import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.dao.DoctorMapper;
import com.phicomm.doctor.dataaccess.dao.DoctorReleseMapper;
import com.phicomm.doctor.dataaccess.domain.Doctor;
import com.phicomm.doctor.dataaccess.domain.DoctorRelese;
import com.phicomm.doctor.service.DoctorService;
import com.phicomm.doctor.service.response.DoctorResponse;
import com.phicomm.doctor.util.ChainAreaUtil;
import com.phicomm.doctor.util.StringUtil;
import com.phicomm.doctor.util.ValidateUtil;

@Service("doctorService")
public class DoctorServiceImpl implements DoctorService{
	
	@Autowired
	private DoctorMapper doctorMapper;
	
	@Autowired
	private DoctorReleseMapper doctorReleseMapper;
	
	@Override
	public void bindPhone(String phone, String smsCode, String openid, HttpSession session) {
		
		Doctor doctor = new Doctor();
		doctor.setOpenid(openid);
		doctor.setPhone(phone);
		doctorMapper.insert(doctor);
	}

	@Override
	public void completeInfo(Doctor doctor) {

		if(!StringUtil.isNotEmpty(doctor.getOpenid(), doctor.getName(), doctor.getProvince(), doctor.getCity(), 
				doctor.getDepartmentName(), doctor.getPracticeCardUrl(), doctor.getProfessionCardUrl(),
				doctor.getQualificationCardUrl())){
			throw new BusinessException("有必填项未填写");
		}
		if(doctor.getSex() == null || doctor.getEducation() == null || doctor.getBirthday() == null ){
			throw new BusinessException("有必填项未填写");
		}
		Integer areaType = ChainAreaUtil.provinceToArea(doctor.getProvince());
		doctor.setArea(areaType);
		doctor.setCompleteStatus(1);
		doctorMapper.updateBySelective(doctor);
	}
	
	@Override
	public DoctorResponse findByOpenid(String openid) {
		
		ValidateUtil.isNotBlank(openid, "openid不能为空");
		Doctor doctor = doctorMapper.getByOpenid(openid);
		if(doctor == null){
			return null;
		}
		
		DoctorResponse response = new DoctorResponse();
		BeanUtils.copyProperties(doctor, response);
		response.setCall(JSONObject.parse(doctor.getCall()));
		response.setWorkExperience(JSONObject.parse(doctor.getWorkExperience()));
		response.setWorks(JSONObject.parse(doctor.getWorks()));
		response.setEducationExperience(JSONObject.parse(doctor.getEducationExperience()));
		//医生坐诊
		DoctorRelese doctorRelese = doctorReleseMapper.getByDoctorId(doctor.getId());
		if(doctorRelese != null){
			response.setDoctorReleseId(doctorRelese.getId());
			response.setStartTime(doctorRelese.getStartTime());
			response.setEndTime(doctorRelese.getEndTime());
			response.setStatus(doctorRelese.getStatus());
		}
		return response;
	}

	@Override
	public Integer totalCount(Integer departmentId, Integer hospitalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DoctorResponse> findByPage(Integer departmentId,
			Integer hospitalId, PageQuery pageQuery) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void relese(String openid, DoctorRelese doctorRelese) {
		
		Doctor doctor = doctorMapper.getByOpenid(openid);
		ValidateUtil.notNull(doctor, "医生不存在");
		ValidateUtil.isNotTrue(doctor.getCompleteStatus() != 1, "资料未完善，不能发布");
		ValidateUtil.isNotTrue(doctor.getAuditStatus() != 1, "资料审核状态异常，不能发布");
		
		doctorRelese.setDoctorId(doctor.getId());
		doctorReleseMapper.insert(doctorRelese);
	}

	@Override
	public void updateReleseStatus(Integer doctorReleseId, Integer status) {
		
		DoctorRelese doctorRelese = new DoctorRelese();
		doctorRelese.setId(doctorReleseId);
		doctorRelese.setStatus(status);
		
		doctorReleseMapper.updateStatus(doctorRelese);
	}

	@Override
	public void deleteRelese(Integer doctorReleseId) {
		
		doctorReleseMapper.delete(doctorReleseId);
	}		
}
