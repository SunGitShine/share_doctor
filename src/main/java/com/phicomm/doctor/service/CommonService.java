package com.phicomm.doctor.service;

import javax.servlet.http.HttpSession;

public interface CommonService {

	String sendSmsCode(String phone, HttpSession session);
	
	void checkSmsCode(String phone, String smsCode, HttpSession session);
}
