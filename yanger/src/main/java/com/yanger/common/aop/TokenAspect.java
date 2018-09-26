package com.yanger.common.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yanger.common.annotation.NoToken;
import com.yanger.common.annotation.Token;
import com.yanger.common.util.JwtUtils;
import com.yanger.common.vo.TokenVo;

import lombok.extern.slf4j.Slf4j;


/**
 * 操作日志处理类
 * @time 2018年6月22日 下午4:38:38
 */
@Slf4j
@Aspect
@Configuration
public class TokenAspect {

	private HttpServletRequest request = null;

	@Pointcut("execution(* com.yanger..*.*Api.*(..))")
	public void tokenLog() {

	}

	/**
	 * 获取request
	 */
	public HttpServletRequest getHttpServletRequest() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		return request;
	}

	/**
	 * 环绕触发
	 */
	@Around("tokenLog()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		//是否需要token --需要token：1.类上Token注解，则方法上没有NoToken 2.方法上Token注解
		boolean isTokenNeed = false;
		Class<?> targetClass = point.getTarget().getClass();
		MethodSignature methodSignature = (MethodSignature) point.getSignature();
		Method method = methodSignature.getMethod();
		// 判断类上没有Token注解
		if (targetClass.getAnnotation(Token.class) != null) {
			// 判断方法上是否有NoToken注解或者Token注解
			isTokenNeed = method.getAnnotation(NoToken.class) == null || method.getAnnotation(Token.class) != null;
		}else {
			// 判断方法上是否有NoToken注解或者Token注解
			isTokenNeed = method.getAnnotation(Token.class) != null;
		}
		if(isTokenNeed){
			request = getHttpServletRequest();
			String token = request.getParameter("token");
			log.info("需要token" + token);
			//校验token
			TokenVo tokenVo = JwtUtils.parse(TokenVo.class, token);
			if(tokenVo != null){
				request.setAttribute("user", tokenVo);
			}
		}
		return point.proceed();
	}
}
