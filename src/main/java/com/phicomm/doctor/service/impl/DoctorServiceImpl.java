package com.phicomm.doctor.service.impl;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phicomm.doctor.common.domain.BusinessException;
import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.dataaccess.dao.DoctorMapper;
import com.phicomm.doctor.dataaccess.dao.DoctorReleseMapper;
import com.phicomm.doctor.dataaccess.domain.Doctor;
import com.phicomm.doctor.dataaccess.domain.DoctorRelese;
import com.phicomm.doctor.service.DoctorService;
import com.phicomm.doctor.service.response.DoctorResponse;
import com.phicomm.doctor.util.StringUtil;
import com.phicomm.doctor.util.ValidateUtil;

@Service("doctorService")
public class DoctorServiceImpl implements DoctorService{
	
	@Autowired
	private DoctorMapper doctorMapper;
	
	@Autowired
	private DoctorReleseMapper doctorReleseMapper;
	
	private static final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);
	
	private static final String SMS_CODE_PREFIX = "sms_code_";

	@Override
	public String sendSmsCode(String phone, HttpSession session) {
		
		String smsCode = getFourRandom();
		//TODO 发送短信
		session.setAttribute(SMS_CODE_PREFIX + phone, smsCode);
		return smsCode;
	}

	/**
     * 产生4位随机数(0000-9999)
     * @return 4位随机数
     */
    public static String getFourRandom(){
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if(randLength<4){
          for(int i=1; i<=4-randLength; i++)
              fourRandom = "0" + fourRandom  ;
      }
        return fourRandom;
    }

	@Override
	public void bindPhone(String phone, String smsCode, String openid, HttpSession session) {
		
		String smsCodeSession = (String) session.getAttribute(SMS_CODE_PREFIX + phone);
		ValidateUtil.isNotBlank(smsCodeSession, "验证码已过期，请重新获取");
		ValidateUtil.isTrue(smsCodeSession.equals(smsCode), "验证码错误");
		session.removeAttribute(SMS_CODE_PREFIX + phone);//清楚session中的验证码
		
		Doctor doctor = new Doctor();
		doctor.setOpenid(openid);
		doctor.setPhone(phone);
		doctorMapper.insert(doctor);
	}

	@Override
	public void completeInfo(Doctor doctor) {

		if(StringUtil.isNotEmpty(doctor.getOpenid(), doctor.getName(), doctor.getProvince(), doctor.getCity(), 
				doctor.getPhone(), doctor.getDepartmentName(), doctor.getPracticeCardUrl(), doctor.getProfessionCardUrl(),
				doctor.getQualificationCardUrl())){
			throw new BusinessException("有必填项未填写");
		}
		if(doctor.getSex() == null || doctor.getEducation() == null || doctor.getBirthday() == null ){
			throw new BusinessException("有必填项未填写");
		}
		doctor.setCompleteStatus(1);
		doctorMapper.updateBySelective(doctor);
	}
	
	@Override
	public DoctorResponse findById(String openid) {
		
		ValidateUtil.isNotBlank(openid, "openid不能为空");
		Doctor doctor = doctorMapper.getByOpenid(openid);
		if(doctor == null){
			return null;
		}
		
		DoctorResponse response = new DoctorResponse();
		BeanUtils.copyProperties(doctor, response);
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
}
