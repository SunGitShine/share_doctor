package com.phicomm.doctor.interceptor;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.phicomm.doctor.interceptor.domain.RequestLog;
import com.phicomm.doctor.interceptor.domain.ResponseLog;

/**
 * 
 * @author xq
 * 
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);

	private static final String MODEL_VIEW_OBJECT = "org.springframework.validation";

	private static final String MODEL_VIEW_CONTENT_TYPE = "text/html;charset=UTF-8";

	/**
	 * 日志开关，默认为false不打开
	 */
	private boolean openLog;

	/**
	 * 日志最大长度，如果不传则默认1000，传-1则不限制日志打印长度
	 */
	private int logLength;

	public void setOpenLog(boolean openLog) {
		this.openLog = openLog;
	}

	public void setLogLength(int logLength) {
		this.logLength = logLength;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (openLog) {
			try {
				RequestLog requestLog = getRequestLog(request);
				request.setAttribute("requestLog", requestLog);
				String log = getLog(requestLog);
				LOG.info(log);
			} catch (Exception e) {
				LOG.error("LogInterceptor preHandle error", e);
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (openLog) {
			try {
				ResponseLog responseLog = getResponseLog(request, response, modelAndView);
				request.setAttribute("responseLog", responseLog);
			} catch (Exception e) {
				LOG.error("LogInterceptor postHandle error", e);
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if (openLog) {
			try {
				Object requestObj = request.getAttribute("requestLog");
				Object responseObj = request.getAttribute("responseLog");
				RequestLog requestLog = null;
				ResponseLog responseLog = null;

				if (requestObj != null) {
					requestLog = (RequestLog) requestObj;
				}

				if (responseObj != null) {
					responseLog = (ResponseLog) responseObj;
				}

				if (requestLog == null) {
					requestLog = getRequestLog(request);
				}

				if (responseLog == null) {
					responseLog = getResponseLog(request, response, null);
				}

				Date start = (requestLog.getTime()) != null ? requestLog.getTime() : new Date();
				Date end = (responseLog.getTime()) != null ? responseLog.getTime() : new Date();
				String cost = end.getTime() - start.getTime() + "ms";
				responseLog.setCost(cost);
				String log = getLog(responseLog);
				int logLength = this.logLength != 0 ? this.logLength : 100000;

				if (logLength != -1 && log.length() > logLength) {
					log = log.substring(0, logLength);
				}

				LOG.info(log);
			} catch (Throwable t) {
				LOG.error("LogInterceptor afterCompletion error", t);
			}
		}
	}

	private String getLog(RequestLog requestLog) {
		String log = "[HttpRequest] "
				+ JSON.toJSONStringWithDateFormat(requestLog, "yyyy-MM-dd HH:mm:ss.SSS", SerializerFeature.DisableCircularReferenceDetect);
		return log;
	}

	private String getLog(ResponseLog responseLog) {
		String log = "[HttpResponse] "
				+ JSON.toJSONStringWithDateFormat(responseLog, "yyyy-MM-dd HH:mm:ss.SSS", SerializerFeature.DisableCircularReferenceDetect);
		return log;
	}

	private RequestLog getRequestLog(HttpServletRequest request) {
		Date time = new Date();
		String url = new String(request.getRequestURL());
		String addr = getClientAddr(request);
		String referer = request.getHeader("Referer");
		String accept = request.getHeader("Accept");
		String agent = request.getHeader("User-Agent");
		String contentType = request.getContentType();
		Cookie[] cookies = request.getCookies();
		String sessionId = null;

		RequestLog requestLog = new RequestLog();
		requestLog.setUrl(url);
		requestLog.setReferer(referer);
		requestLog.setAddr(addr);
		requestLog.setAccept(accept);
		requestLog.setAgent(agent);
		requestLog.setContentType(contentType);
		requestLog.setSessionId(sessionId);
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, String> parameters = new LinkedHashMap<String, String>();

		if (parameterMap != null && !parameterMap.isEmpty()) {
			for (Entry<String, String[]> entry : parameterMap.entrySet()) {
				String key = entry.getKey();
				String[] valueArray = entry.getValue();
				String value = getStringArrayStr(valueArray, ",");
				parameters.put(key, value);
			}
		}

		requestLog.setParameters(parameters);
		requestLog.setTime(time);
		return requestLog;
	}

	private ResponseLog getResponseLog(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
		ResponseLog responseLog = new ResponseLog();
		String contentType = response.getContentType();
		String addr = getLocalIp();

		if (modelAndView != null) {
			if (contentType == null || contentType.equals("")) {
				contentType = MODEL_VIEW_CONTENT_TYPE;
			}

			String viewName = modelAndView.getViewName();
			responseLog.setContentType(contentType);
			responseLog.setView(viewName);
			responseLog.setAddr(addr);
			Map<String, Object> map = modelAndView.getModel();
			Map<String, Object> result = new LinkedHashMap<String, Object>();

			if (map != null && !map.isEmpty()) {
				for (Entry<String, Object> entry : map.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();

					if (key.contains(MODEL_VIEW_OBJECT) || value instanceof BeanPropertyBindingResult) {
						continue;
					}

					result.put(key, value);
				}

				responseLog.setResult(result);
			}
		} else {
			Object returnObj = request.getAttribute("returnObj"); 
			responseLog.setResult(returnObj);
			responseLog.setContentType(contentType);
			responseLog.setAddr(addr);
		}

		responseLog.setTime(new Date());
		return responseLog;
	}
	
	/**
	 * 获取用户访问地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientAddr(HttpServletRequest request) {
		String str = request.getHeader("X-Forwarded-For");

		if ((str == null) || (str.length() == 0) || ("unknown".equalsIgnoreCase(str))) {
			str = request.getHeader("X-Real-IP");
		}
		if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
			str = request.getHeader("Proxy-Client-IP");
		}
		if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
			str = request.getHeader("WL-Proxy-Client-IP");
		}
		if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
			str = request.getHeader("HTTP_CLIENT_IP");
		}
		if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
			str = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
			str = request.getRemoteAddr();
		}

		if (str != null && str.indexOf(",") != -1) {
			str = str.substring(str.lastIndexOf(",") + 1, str.length()).trim();
		}

		return String.valueOf(str);
	}
	
	/**
	 * 获取stringArray对应的字符串(中间以指定分隔符链接)
	 *
	 * @param list 需要转换的列表
	 * @param splitSign 指定分隔符
	 * @return list对应的字符串
	 */
	public static String getStringArrayStr(String[] strArray, String splitSign) {
		StringBuffer stringArraySb = new StringBuffer();

		if (strArray != null && strArray.length > 0) {
			int size = strArray.length;

			for (int i = 0; i < size; i++) {
				stringArraySb.append(strArray[i]);
				int end = size - 1;

				if (i != end) {
					stringArraySb.append(splitSign);
				}
			}
		}

		return stringArraySb.toString();
	}
	
	/**
	 * 获取本机IP(非127.0.0.1)
	 * @return
	 */
	public static String getLocalIp() {
		String localIp = null;
		InetAddress inetAddress = null;
		Enumeration<NetworkInterface> allNetInterfaces = null;
		
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			LOG.error("GetIp error");
		}

		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			
			while (addresses.hasMoreElements()) {
				inetAddress = (InetAddress) addresses.nextElement();
				
				if (inetAddress != null && inetAddress instanceof Inet4Address) {
					String host = inetAddress.getHostAddress();
					
					localIp = host;
				}
			}
		}

		return localIp;
	}

}
