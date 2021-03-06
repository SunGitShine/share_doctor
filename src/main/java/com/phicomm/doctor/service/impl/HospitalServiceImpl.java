package com.phicomm.doctor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.phicomm.doctor.common.domain.BusinessException;
import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.dao.HospitalMapper;
import com.phicomm.doctor.dataaccess.dao.HospitalReleaseMapper;
import com.phicomm.doctor.dataaccess.domain.Hospital;
import com.phicomm.doctor.dataaccess.domain.HospitalRelease;
import com.phicomm.doctor.service.DoctorService;
import com.phicomm.doctor.service.HospitalService;
import com.phicomm.doctor.service.request.ReleaseListRequest;
import com.phicomm.doctor.service.response.DoctorResponse;
import com.phicomm.doctor.service.response.HospitalResponse;
import com.phicomm.doctor.service.response.ReleaseListResponse;
import com.phicomm.doctor.util.ChainAreaUtil;
import com.phicomm.doctor.util.StringUtil;
import com.phicomm.doctor.util.ValidateUtil;

@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService{

	@Autowired
	private HospitalMapper hospitalMapper;
	
	@Autowired
	private HospitalReleaseMapper hospitalReleaseMapper;
	
	@Autowired
	private DoctorService doctorService;

	@Override
	public HospitalResponse getByOpenid(String openid) {
		
		ValidateUtil.isNotBlank(openid, "openid不能为空");
		Hospital hospital = hospitalMapper.getByOpenid(openid);
		
		if(hospital == null)
			return null;
		
		HospitalResponse response = new HospitalResponse();
		BeanUtils.copyProperties(hospital, response);
		response.setTag(JSON.parse(hospital.getTag()));
		response.setImageUrl(JSON.parse(hospital.getImageUrl()));
		return response;
	}

	@Override
	public void completeInfo(Hospital hospital) {
		
		if(!StringUtil.isNotEmpty(hospital.getOpenid(), hospital.getName(), hospital.getLogoImgUrl(), hospital.getAddress(),
				hospital.getProvince(), hospital.getCity(), hospital.getBusinessLicenceUrl())){
			throw new BusinessException("有必填项未填写");
		}
		ValidateUtil.notNull(hospital.getScale(), "规模不能为空");
		ValidateUtil.notNull(getByOpenid(hospital.getOpenid()), "不存在此医院");
		
		Integer areaType = ChainAreaUtil.provinceToArea(hospital.getProvince());
		hospital.setArea(areaType);
		hospitalMapper.completeInfo(hospital);
	}

	@Override
	public void release(String openid, HospitalRelease release) {
		
		ValidateUtil.isNotBlank(openid, "openid不能为空");
		
		Hospital hospital = hospitalMapper.getByOpenid(openid);
		ValidateUtil.notNull(getByOpenid(hospital.getOpenid()), "不存在此医院");
		ValidateUtil.isNotTrue(hospital.getCompleteStatus() == 0, "信息未完善，不能发布信息");
		ValidateUtil.isNotTrue(hospital.getAuditStatus() == 0 || hospital.getAuditStatus() == 2, "信息审核状态异常，不能发布信息");
		
		hospitalReleaseMapper.insert(release);
	}

	@Override
	public void updateReleaseStatus(Integer releaseId, Integer status, Integer auditStatus) {
		hospitalReleaseMapper.updateStatus(releaseId, status, null);
	}
	
	@Override
	public void updateReleaseAuditStatus(Integer releaseId, Integer status, Integer auditStatus) {
		hospitalReleaseMapper.updateStatus(releaseId, null, auditStatus);
	}

	@Override
	public void deleteRelease(Integer releaseId) {
		
		hospitalReleaseMapper.deleteById(releaseId);
	}

	@Override
	public void bindPhone(String phone, String openid, String name, String headImgUrl) {
		
		Hospital hospital = new Hospital();
		hospital.setOpenid(openid);
		hospital.setPhone(phone);
		hospital.setName(name);
		hospital.setLogoImgUrl(headImgUrl);
		hospitalMapper.bindPhone(hospital);
	}

	@Override
	public List<ReleaseListResponse> findReleaseListPage(String hospitalOpenid, String doctorOpenId, Integer departmentId, PageQuery pageQuery) {
		
		if(StringUtil.isNotBlank(doctorOpenId)) {
			DoctorResponse doctor = doctorService.findByOpenid(doctorOpenId);
			if(doctor.getCompleteStatus() != 1 || doctor.getAuditStatus() != 1
					|| doctor.getDoctorReleseId() == null) {
				return new ArrayList<ReleaseListResponse>();
			}
		}
		
		return hospitalReleaseMapper.findReleaseListPage(makeRequestParm(hospitalOpenid, doctorOpenId), departmentId, pageQuery);
	}

	@Override
	public Integer findReleaseCount(String hospitalOpenid, String doctorOpenid, Integer departmentId) {
		
		if(StringUtil.isNotBlank(doctorOpenid)) {
			DoctorResponse doctor = doctorService.findByOpenid(doctorOpenid);
			if(doctor.getCompleteStatus() != 1 || doctor.getAuditStatus() != 1
					|| doctor.getDoctorReleseId() == null) {
				return 0;
			}
		}
		
		
		return hospitalReleaseMapper.findReleaseCount(makeRequestParm(hospitalOpenid, doctorOpenid), departmentId);
	}	
	
	public ReleaseListRequest makeRequestParm(String hospitalOpenid, String doctorOpenid) {
		
		ReleaseListRequest request = new ReleaseListRequest();
		request.setHospitalOpenid(hospitalOpenid);
		
		if(StringUtil.isNotBlank(doctorOpenid)) {
			DoctorResponse doctor = doctorService.findByOpenid(doctorOpenid);
			ValidateUtil.notNull(doctor, "不存在医生信息");
			
			request.setArea(doctor.getArea());
			request.setDepartmentId(doctor.getDepartmentId());
			request.setDoctorTitle(doctor.getTitle());
			request.setStartTime(doctor.getStartTime());
			request.setEndTime(doctor.getEndTime());
		}
		
		return request;
	}

	@Override
	public ReleaseListResponse findReleaseById(Integer releaseId) {
		
		ValidateUtil.notNull(releaseId, "id不能为空");
		ReleaseListResponse response =  hospitalReleaseMapper.findById(releaseId);
		if(response != null) {
			response.setTag(JSON.parse(response.getTag().toString()));
		}
		return response;
	}

	@Override
	public List<Hospital> findByPageWeb(String name, Integer auditStatus, PageQuery pageQuery) {
		return hospitalMapper.findByPageWeb(name, auditStatus, pageQuery);
	}

	@Override
	public Integer findByCountWeb(String name, Integer auditStatus) {
		return hospitalMapper.findByCountWeb(name, auditStatus);
	}

	@Override
	public void auditHospital(Integer auditStatus, String openid) {
		hospitalMapper.audit(auditStatus, openid);
	}

	@Override
	public List<ReleaseListResponse> findReleaseListPageWeb(String name, Integer auditStatus, PageQuery pageQuery) {
		return hospitalReleaseMapper.findReleaseListPageWeb(name, auditStatus, pageQuery);
	}

	@Override
	public Integer findReleaseListCountWeb(String name, Integer auditStatus) {
		return hospitalReleaseMapper.findReleaseListCountWeb(name, auditStatus);	}
}
