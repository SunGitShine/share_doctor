package com.phicomm.doctor.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.phicomm.doctor.common.domain.PageQuery;
import com.phicomm.doctor.util.ParameterUtil;


public class BaseController {

	protected HttpServletRequest getHttpServletRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	protected Integer getIntegerParameter(String name, Integer defaultValue) {
		String value = getHttpServletRequest().getParameter(name);
		return StringUtils.isEmpty(value) ? defaultValue : Integer.valueOf(value);
	}
	
	protected PageQuery getPageQuery() {
		Integer pageNo = ParameterUtil.getInteger("pageNo", 1);
		Integer pageSize = ParameterUtil.getInteger("pageSize", 10);
		PageQuery page = new PageQuery(pageNo, pageSize);

		return page;
	}
}
