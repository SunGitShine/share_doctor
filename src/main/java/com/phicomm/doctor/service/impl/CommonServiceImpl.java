package com.phicomm.doctor.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.phicomm.doctor.service.CommonService;
import com.phicomm.doctor.util.MsgUtil;
import com.phicomm.doctor.util.ValidateUtil;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	
	private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);
	
	private static final String SMS_CODE_PREFIX = "sms_code_";

	@Override
	public void sendSmsCode(String phone, HttpSession session) {
		
		String smsCode = getFourRandom();
		log.info("发送的短信验证码：" + smsCode);
		//TODO 发送短信
		String tplValue = "#code#=" + smsCode;
		MsgUtil.sendSMS("76686", tplValue, phone);
		session.setAttribute(SMS_CODE_PREFIX + phone, smsCode);
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
	public void checkSmsCode(String phone, String smsCode, HttpSession session) {
		
		String smsCodeSession = (String) session.getAttribute(SMS_CODE_PREFIX + phone);
		ValidateUtil.isNotBlank(smsCodeSession, "验证码已过期，请重新获取");
		ValidateUtil.isTrue(smsCodeSession.equals(smsCode), "验证码错误");
		session.removeAttribute(SMS_CODE_PREFIX + phone);//清楚session中的验证码
	}

	@Override
	public String uploadFile(MultipartFile file, HttpServletRequest request) throws IOException {
		
		String fileName = file.getOriginalFilename();
		String newFilePath = new Date().getTime() + getFileSuffix(fileName);
        File tempFile = new File(request.getSession().getServletContext().getRealPath("../pic/doctor/"), newFilePath);  
        if (!tempFile.getParentFile().exists()) {  
            tempFile.getParentFile().mkdir();  
        }  
        if (!tempFile.exists()) {  
            tempFile.createNewFile();  
        }  
        file.transferTo(tempFile);  
        return getBaseUrl(request) + "/pic/doctor/" + newFilePath; 
	}
	
	/**
	 * 获取文件后缀
	 * @param fileName
	 * @return
	 */
	public String getFileSuffix(String fileName){
		   
		String newPicPath = fileName.substring(fileName.lastIndexOf("."));
		return newPicPath;
	}
	
	/**
	 * 获取域名
	 * @param request
	 * @return
	 */
	public String getBaseUrl(HttpServletRequest request){
		String baseUrl = request.getRequestURL().substring(0, request.getRequestURL().indexOf("/", 8)); 
		return baseUrl;
	}
}
