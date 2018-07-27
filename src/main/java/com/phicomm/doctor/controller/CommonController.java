package com.phicomm.doctor.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.phicomm.doctor.common.domain.BusinessException;
import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.common.domain.ResultCode;
import com.phicomm.doctor.service.CommonService;
import com.phicomm.doctor.service.DoctorService;
import com.phicomm.doctor.service.HospitalService;
import com.phicomm.doctor.service.response.DoctorResponse;
import com.phicomm.doctor.service.response.HospitalResponse;
import com.phicomm.doctor.util.ParameterUtil;
import com.phicomm.doctor.util.StringUtil;
import com.phicomm.doctor.util.ValidateUtil;
import com.phicomm.doctor.util.http.HttpClientUtil;

@Controller
@RequestMapping("/common")
public class CommonController {
	
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@RequestMapping("/findByOpneid")
	public Result findByOpneid() {
		
		String openid = ParameterUtil.getString("openid");
		
		DoctorResponse doctor = doctorService.findByOpenid(openid);
		HospitalResponse hospital = hospitalService.getByOpenid(openid);
		
		Integer identity = null;
		Result result = new Result(ResultCode.COMMON_SUCCESS, true);
		if(doctor == null && hospital == null) {
			identity = 0;
		}
		if(doctor != null) {
			identity = 1;
			result.setProperty("response", doctor);
		}
		if(hospital != null) {
			identity = 2;
			result.setProperty("response", hospital);
		}
		result.setProperty("identity", identity);
		return result;
	}
	
	@RequestMapping("/sendSmsCode")
	public Result sendSmsCode(HttpServletRequest request) {
		
		String phone = ParameterUtil.getString("phone");
		ValidateUtil.isNotBlank(phone, "手机号不能为空");
		commonService.sendSmsCode(phone, request.getSession());
		return ParameterUtil.commonSuccessResult();
	}
	
	@RequestMapping("/bindPhone")
	public Result bindPhone(HttpServletRequest request) {
		
		String phone = ParameterUtil.getString("phone");
		String smsCode = ParameterUtil.getString("smsCode");
		String openid = ParameterUtil.getString("openid");
		String name = ParameterUtil.getString("name");
		String headImgUrl = ParameterUtil.getString("headImgUrl");
		Integer identityType = ParameterUtil.getInteger("identityType");
		if(StringUtil.isEmpty(phone) || StringUtil.isEmpty(smsCode) || StringUtil.isEmpty(openid)
				|| StringUtil.isEmpty(name) || StringUtil.isEmpty(headImgUrl)) {
			throw new BusinessException("参数异常");
		}
		ValidateUtil.notNull(identityType, "身份类型不能为空");
		
//		commonService.checkSmsCode(phone, smsCode, request.getSession());//校验短信验证码
		
		DoctorResponse doctor = doctorService.findByOpenid(openid);
		HospitalResponse hospital = hospitalService.getByOpenid(openid);
		
		ValidateUtil.isNotTrue(doctor != null, "该账户已存在医生库");
		ValidateUtil.isNotTrue(hospital != null, "该账户已存在医院库");
		
		if(identityType == 1) {
			doctorService.bindPhone(phone, openid, name, headImgUrl);
		}else if(identityType == 2) {
			hospitalService.bindPhone(phone, openid, name, headImgUrl);
		}
		
		return ParameterUtil.commonSuccessResult();
	}
	
	@RequestMapping("getOpenid")
	public Result getOpenid(HttpServletRequest request) {
		
		String appid = request.getParameter("appid");
		String secret = request.getParameter("secret");
		String grantType = request.getParameter("grantType");
		String jsCode = request.getParameter("jsCode");
		
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		String params = "appid=" + appid + "&secret=" + secret + "&grant_type=" + grantType + "&js_code=" + jsCode;
		String result = HttpClientUtil.doGet(url, params, null);
		return ParameterUtil.commonSuccessResult("result", result);
	}
	
	@RequestMapping(value = "/upload")
	public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
	    String filePath = commonService.uploadFile(file, request);  
	    log.info("filePath:" + filePath);
	    return ParameterUtil.commonSuccessResult("picPath", filePath);
	}
}
