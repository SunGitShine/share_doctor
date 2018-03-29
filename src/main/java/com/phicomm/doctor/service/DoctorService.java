package com.phicomm.doctor.service;

import javax.servlet.http.HttpSession;

public interface DoctorService {

	String sendSmsCode(String phone, HttpSession session);
	
	void bindPhone(String phone, String smsCode, String openid, HttpSession session);
}
