package com.phicomm.doctor.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LogAspect {

	@Pointcut("execution(* com.phicomm.doctor.controller.*.*(..))")
    private void anyControllerMethod(){}//定义一个切入点

    @Around("anyControllerMethod()")
    public Object Around(ProceedingJoinPoint point) throws Throwable{
    	
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	Object result = point.proceed();
    	request.setAttribute("returnObj", result);
    	return result; 
    }
}
