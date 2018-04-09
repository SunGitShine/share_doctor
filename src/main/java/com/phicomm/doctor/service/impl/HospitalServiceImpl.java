package com.phicomm.doctor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.doctor.common.domain.BusinessException;
import com.phicomm.doctor.dataaccess.dao.HospitalMapper;
import com.phicomm.doctor.dataaccess.dao.HospitalReleaseMapper;
import com.phicomm.doctor.dataaccess.domain.Hospital;
import com.phicomm.doctor.dataaccess.domain.HospitalRelease;
import com.phicomm.doctor.service.HospitalService;
import com.phicomm.doctor.util.StringUtil;
import com.phicomm.doctor.util.ValidateUtil;

@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService{

	@Autowired
	private HospitalMapper hospitalMapper;
	
	@Autowired
	private HospitalReleaseMapper hospitalReleaseMapper;

	@Override
	public Hospital getByOpenid(String openid) {
		ValidateUtil.isNotBlank(openid, "openid不能为空");
		return hospitalMapper.getByOpenid(openid);
	}

	@Override
	public void completeInfo(Hospital hospital) {
		
		if(StringUtil.isNotEmpty(hospital.getOpenid(), hospital.getName(), hospital.getLogoImgUrl(), hospital.getAddress(),
				hospital.getProvince(), hospital.getCity(), hospital.getBusinessLicenceUrl())){
			throw new BusinessException("有必填项未填写");
		}
		ValidateUtil.notNull(hospital.getScale(), "规模不能为空");
		ValidateUtil.notNull(getByOpenid(hospital.getOpenid()), "不存在此医院");
		
		hospitalMapper.completeInfo(hospital);
	}

	@Override
	public void release(String openid, HospitalRelease release) {
		
		ValidateUtil.isNotBlank(openid, "openid不能为空");
		
		Hospital hospital = getByOpenid(openid);
		ValidateUtil.notNull(getByOpenid(hospital.getOpenid()), "不存在此医院");
		ValidateUtil.isNotTrue(hospital.getCompleteStatus() == 0, "信息未完善，不能发布信息");
		ValidateUtil.isNotTrue(hospital.getAuditStatus() == 0 || hospital.getAuditStatus() == 2, "信息审核状态异常，不能发布信息");
		
		hospitalReleaseMapper.insert(release);
	}	
	
}
