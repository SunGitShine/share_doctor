package com.phicomm.doctor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.phicomm.doctor.common.domain.BusinessException;
import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.dao.DoctorMapper;
import com.phicomm.doctor.dataaccess.dao.DoctorReleseMapper;
import com.phicomm.doctor.dataaccess.dao.HospitalMapper;
import com.phicomm.doctor.dataaccess.dao.HospitalReleaseMapper;
import com.phicomm.doctor.dataaccess.domain.Doctor;
import com.phicomm.doctor.dataaccess.domain.DoctorRelese;
import com.phicomm.doctor.dataaccess.domain.Hospital;
import com.phicomm.doctor.dataaccess.domain.HospitalRelease;
import com.phicomm.doctor.service.DoctorService;
import com.phicomm.doctor.service.request.DoctorListRequest;
import com.phicomm.doctor.service.response.DoctorListResponse;
import com.phicomm.doctor.service.response.DoctorListResponsePage;
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
	
	@Autowired
	private HospitalMapper hospitalMapper;
	
	@Autowired
	private HospitalReleaseMapper hospitalReleaseMapper;
	
	@Override
	public void bindPhone(String phone, String openid, String name, String headImgUrl) {
		
		Doctor doctor = new Doctor();
		doctor.setOpenid(openid);
		doctor.setPhone(phone);
		doctor.setName(name);
		doctor.setHeadImgUrl(headImgUrl);
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

	@Override
	public DoctorListResponsePage findDoctorListPage(String hospitalOpenid, Integer departmentId,
			PageQuery pageQuery) {
		
		DoctorListResponsePage responsePage = new DoctorListResponsePage();
		List<DoctorListResponse> doctorListResponses = new ArrayList<DoctorListResponse>();
		Integer count = 0;
		
		if(StringUtil.isNotBlank(hospitalOpenid)) {
			
			List<DoctorListRequest> requestList = new ArrayList<DoctorListRequest>();
			Hospital hospital = hospitalMapper.getByOpenid(hospitalOpenid);
			
			if(hospital != null) {
				
				List<HospitalRelease> hrs = hospitalReleaseMapper.findListByHid(hospital.getId());
				for(HospitalRelease release : hrs) {
					
					DoctorListRequest request = new DoctorListRequest();
					request.setArea(hospital.getArea());
					request.setDepartmentId(release.getDepartmentId());
					request.setDoctorTitle(release.getDoctorTitle());
					request.setTime(release.getTime());
					request.setWorkTime(release.getWorkTime());
					
					requestList.add(request);
				}
				
				doctorListResponses = doctorMapper.findByPage(requestList, null, pageQuery);
				count = doctorMapper.findCount(requestList, null);
			}
		}
		
		if(departmentId != null) {
			doctorListResponses = doctorMapper.findByPage(null, departmentId, pageQuery);
			count = doctorMapper.findCount(null, departmentId);
		}
		
		responsePage.setCount(count);
		responsePage.setResponses(doctorListResponses);
		return responsePage;
	}

}
