package com.phicomm.doctor.service.impl;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.phicomm.doctor.service.CommonService;
import com.phicomm.doctor.util.ValidateUtil;

@Service("commonService")
public class CommonServiceImpl implements CommonService{
	
	private static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);
	
	private static final String SMS_CODE_PREFIX = "sms_code_";

	@Override
	public String sendSmsCode(String phone, HttpSession session) {
		
		String smsCode = getFourRandom();
		log.info("发送的短信验证码：" + smsCode);
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
	public void checkSmsCode(String phone, String smsCode, HttpSession session) {
		
		String smsCodeSession = (String) session.getAttribute(SMS_CODE_PREFIX + phone);
		ValidateUtil.isNotBlank(smsCodeSession, "验证码已过期，请重新获取");
		ValidateUtil.isTrue(smsCodeSession.equals(smsCode), "验证码错误");
		session.removeAttribute(SMS_CODE_PREFIX + phone);//清楚session中的验证码
	}
}
