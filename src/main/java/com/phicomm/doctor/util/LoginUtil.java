//package com.phicomm.doctor.util;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.alibaba.fastjson.JSONObject;
//import com.phicomm.doctor.common.domain.WebContext;
//
//
//public class LoginUtil {
//
//	protected final static Logger log = LoggerFactory.getLogger(LoginUtil.class);
//	
//	public static HttpServletRequest getHttpServletRequest() {
//        return WebContext.currentRequest();
//    }
//	
//	public static AccountDO currentUser() {
//        HttpServletRequest request = getHttpServletRequest();
//        Object attribute = request.getSession().getAttribute("loginUser");
//        
//        if (attribute == null) {
//        	return null;
//		}
//        
//        if (attribute instanceof AccountDO) {
//        	 return (AccountDO)attribute;
//		} else {
//			String jsonString = JSONObject.toJSONString(attribute);
//			return JSONObject.parseObject(jsonString, AccountDO.class);
//		}
//    }
//	
//	/**
//	 * 当前登录账号
//	 * @return
//	 */
//	public static String currentUserName(){
//		
//		AccountDO user = currentUser();
//		if(user == null){
//			return null;
//		}
//		return user.getUsername();
//	}
//	
//	/**
//	 * 当前登录账号手机号
//	 * @return
//	 */
//	public static String currentMobile(){
//		
//		AccountDO user = currentUser();
//		if(user == null){
//			return null;
//		}
//		return user.getMobile();
//	}
//	
//	/**
//	 * 当前登录真实姓名
//	 * @return
//	 */
//	public static String currentRealname(){
//		
//		AccountDO user = currentUser();
//		if(user == null){
//			return null;
//		}
//		return user.getRealname();
//	}
//	
//	/**
//	 * 当前登录账号类型
//	 * @return
//	 */
//	public static Integer currentAccountType(){
//		
//		AccountDO user = currentUser();
//		if(user == null){
//			return null;
//		}
//		return user.getType();
//	}
//	
//	/**
//     * 是否登录
//     *
//     * @return
//     */
//    public static boolean isLogined() {
//    	
//    	AccountDO user = currentUser();
//        if (null == user) {
//            return false;
//        }
//        return true;
//    }
//}
