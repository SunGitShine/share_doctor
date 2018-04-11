package com.phicomm.doctor.service.impl;

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
	public void deleteRelease(Integer releaseId) {
		
		hospitalReleaseMapper.deleteById(releaseId);
	}

	@Override
	public void bindPhone(String phone, String openid) {
		
		Hospital hospital = new Hospital();
		hospital.setOpenid(openid);
		hospital.setPhone(phone);
		hospitalMapper.bindPhone(hospital);
	}

	@Override
	public List<ReleaseListResponse> findReleaseListPage(String hospitalOpenid, String doctorOpenId, PageQuery pageQuery) {
		
		return hospitalReleaseMapper.findReleaseListPage(makeRequestParm(hospitalOpenid, doctorOpenId), pageQuery);
	}

	@Override
	public Integer findReleaseCount(String hospitalOpenid, String doctorOpenid) {
		
		return hospitalReleaseMapper.findReleaseCount(makeRequestParm(hospitalOpenid, doctorOpenid));
	}	
	
	public ReleaseListRequest makeRequestParm(String hospitalOpenid, String doctorOpenid) {
		
		ReleaseListRequest request = new ReleaseListRequest();
		request.setHospitalOpenid(hospitalOpenid);
		
		DoctorResponse doctor = doctorService.findByOpenid(doctorOpenid);
		ValidateUtil.notNull(doctor, "不存在医生信息");
		
		request.setArea(doctor.getArea());
		request.setDepartmentId(doctor.getDepartmentId());
		request.setDoctorTitle(doctor.getTitle());
		request.setStartTime(doctor.getStartTime());
		request.setEndTime(doctor.getEndTime());
		
		return request;
	}
}
