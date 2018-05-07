package com.phicomm.doctor.util;


import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.phicomm.doctor.common.domain.BusinessException;
import com.phicomm.doctor.common.domain.Result;
import com.phicomm.doctor.common.domain.ResultCode;

/**
 * fuquanemail@gmail.com 2016/1/10 14:48
 * description:
 * 1.0.0
 */
public final class ParameterUtil {

    public static final <T> T parseObject(Class<T> clazz) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        String value = request.getParameter("d");
        if (StringUtils.isEmpty(value)) {
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return JSON.parseObject(value, clazz);
    }

    public static final <T> T parseObject(String text, Class<T> clazz) {
        if (StringUtils.isEmpty(text)) {
            throw new BusinessException("缺失请求参数");
        }
        T t = JSON.parseObject(text, clazz);
        if (t == null) {
            throw new BusinessException("参数转换异常");
        }
        return t;
    }

    public static Long getLong(String parameterKey) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String value = request.getParameter("d");
        if(StringUtils.isEmpty(value)){
        	throw new BusinessException("参数不能为空");
        }

        JSONObject object = JSON.parseObject(value);
        if (null == object) {
            return null;
        }
        return object.getLong(parameterKey);
    }

    public static Integer getInteger(String parameterKey) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String value = request.getParameter("d");
        if(StringUtils.isEmpty(value)){
        	throw new BusinessException("参数不能为空");
        }

        JSONObject object = JSON.parseObject(value);
        if (null == object) {
            return null;
        }
        return object.getInteger(parameterKey);
    }
    
    public static Integer getInteger(String parameterKey, Integer defaultNum) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String value = request.getParameter("d");
        if(StringUtils.isEmpty(value)){
        	throw new BusinessException("参数不能为空");
        }

        JSONObject object = JSON.parseObject(value);
        if (null == object) {
            return null;
        }
        if(object.getInteger(parameterKey) == null) {
        	return defaultNum;
        }
        return object.getInteger(parameterKey);
    }

    public static Byte getByte(String parameterKey) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String value = request.getParameter("d");
        if(StringUtils.isEmpty(value)){
        	throw new BusinessException("参数不能为空");
        }
        JSONObject object = JSON.parseObject(value);

        if (null == object) {
            return null;
        }
        return object.getByte(parameterKey);
    }

    public static String getString(String parameterKey) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        String value = request.getParameter("d");
        if(StringUtils.isEmpty(value)){
        	throw new BusinessException("参数不能为空");
        }
        JSONObject object = JSON.parseObject(value);
        if (null == object) {
            return null;
        }
        return object.getString(parameterKey);
    }

    public static Result commonSuccessResult() {
		return new Result(ResultCode.COMMON_SUCCESS, true);
	}

    public static Result commonSuccessResult(String key, Object data) {
		Result result = new Result(ResultCode.COMMON_SUCCESS, true);
		result.setUseDateFormat(true);
		result.setProperty(key, data);
		return result;
	}
	
    public static Result pageSuccessResult(Integer size,Collection<?> e) {
		Result result = new Result(ResultCode.COMMON_SUCCESS, true);
		result.setUseDateFormat(true);
		result.setPage(size, e);;
		return result;
	}
	
    public static String toJSONString(Object data) {

		return JSON.toJSONStringWithDateFormat(data, JSON.DEFFAULT_DATE_FORMAT, SerializerFeature.DisableCircularReferenceDetect);
	}

}
