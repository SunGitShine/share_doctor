package com.phicomm.doctor.service;

import javax.servlet.http.HttpSession;

import com.phicomm.doctor.dataaccess.domain.Doctor;

public interface DoctorService {

	String sendSmsCode(String phone, HttpSession session);
	
	void bindPhone(String phone, String smsCode, String openid, HttpSession session);
	
	void completeInfo(Doctor doctor);
}
