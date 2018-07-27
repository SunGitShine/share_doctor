package com.phicomm.doctor.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {

	void sendSmsCode(String phone, HttpSession session);
	
	void checkSmsCode(String phone, String smsCode, HttpSession session);
	
	public String uploadFile(MultipartFile file, HttpServletRequest request) throws IOException;
}
