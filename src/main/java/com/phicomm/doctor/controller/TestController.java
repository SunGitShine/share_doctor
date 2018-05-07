package com.phicomm.doctor.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.util.ParameterUtil;

@Controller
@RequestMapping(value = "/api/v1")
public class TestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(value = "/imgclassify/dish/feed_back", method = RequestMethod.POST)
	public void testCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOG.info("========== receive callback data: {}", request);

		Map<String, Object> map = new HashMap<>();
		map.put("status", "OK");
		map.put("code", 0);
//		return ParameterUtil.commonSuccessResult("resultMap", map);
		response.getWriter().write("{\"status\":\"OK\",\"code\":0}");
//		return map;
	}
}
