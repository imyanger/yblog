package com.yanger.common.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yanger.common.annotation.NoRecordLog;
import com.yanger.common.annotation.OperateClass;
import com.yanger.common.annotation.OperateLog;

/**
 * 操作日志处理类
 * @time 2018年6月22日 下午4:38:38
 */
@Aspect
@Configuration
public class OperateLogAspect {

	private static final Logger logger = LoggerFactory.getLogger(OperateLogAspect.class);

	private HttpServletRequest request = null;

	@Pointcut("execution(* com.yanger..*.*Api.*(..))")
	public void operateLog() {

	}

	/**
	 * 方法调用前触发 记录开始时间
	 */
	@Before("operateLog()")
	public void before(JoinPoint joinPoint) {

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
	@Around("operateLog()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Class<?> targetClass = point.getTarget().getClass();
		// 如果类上有不记录日志的注解，则操作记录将不会插入到数据库
		if (targetClass.getAnnotation(NoRecordLog.class) == null) {
			MethodSignature methodSignature = (MethodSignature) point.getSignature();
			Method method = methodSignature.getMethod();
			OperateLog operateLog = method.getAnnotation(OperateLog.class);
			// 存在日志注解才进行方法拦截
			if (operateLog != null) {
				logger.info("插入操作日志...");
				OperateClass operateClass = targetClass.getAnnotation(OperateClass.class);
				request = getHttpServletRequest();
				// 插入操作痕迹到数据库
			}
		}
		return point.proceed();
	}
}
