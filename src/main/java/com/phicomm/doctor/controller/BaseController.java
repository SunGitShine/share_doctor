package com.phicomm.doctor.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.phicomm.doctor.common.domain.PageQuery;


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
		Integer pageNo = getIntegerParameter("pageNo", 1);
		Integer pageSize = getIntegerParameter("pageSize", 20);
		PageQuery page = new PageQuery(pageNo, pageSize);

		return page;
	}
}
