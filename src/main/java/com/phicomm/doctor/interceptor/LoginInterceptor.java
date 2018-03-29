//package com.phicomm.doctor.interceptor;
//
//import java.io.IOException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.http.HttpStatus;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import com.phicomm.doctor.util.URLUtil;
//
//public class LoginInterceptor extends HandlerInterceptorAdapter {
//
//	@Override
//	public boolean preHandle(HttpServletRequest request,
//			HttpServletResponse response, Object handler) throws Exception {
//
//		String currentURI = request.getRequestURI();
//		if ("/login.htm".equals(currentURI) || "/".endsWith(currentURI)) {
//			return true;
//		}
//		boolean isLogin = LoginUtil.isLogined();
//		if (!isLogin) {
//			processUnLogin(request, response);
//			return false;
//		}
//		return true;
//	}
//
//	private void processUnLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		if (URLUtil.isAjaxUrl(request)) {
//			setAjaxResponse(response);
//			response.setStatus(HttpStatus.SC_UNAUTHORIZED);
//		} else {
//			response.setContentType("text/html");
//			if (Boolean.parseBoolean(request.getParameter("noRedirect"))) {
//				response.getWriter().write("401");
//			} else {
//				response.sendRedirect(request.getContextPath());
//			}
//		}
//	}
//
//	private void setAjaxResponse(HttpServletResponse response) throws IOException {
//		response.setContentType("application/json;charset=UTF-8");
//		response.getWriter().write("{}");
//	}
//}
