package com.yanger.common.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yanger.blog.dao.OperateLogDao;
import com.yanger.blog.po.OperateLog;
import com.yanger.common.annotation.Operate;
import com.yanger.common.filter.RequestWrapper;
import com.yanger.blog.util.BlogConstant;

/**
 * 操作日志处理类
 * 
 * @time 2018年6月22日 下午4:38:38
 */
@Aspect
@Configuration
public class OperateLogAspect {

	private static final Logger logger = LoggerFactory.getLogger(OperateLogAspect.class);

	private HttpServletRequest request = null;

	@Autowired
	OperateLogDao operateLogDao;

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
		MethodSignature methodSignature = (MethodSignature) point.getSignature();
		Method method = methodSignature.getMethod();
		Operate operate = method.getAnnotation(Operate.class);
		// 存在日志注解才进行方法拦截
		if (operate != null) {
			request = getHttpServletRequest();
			// 请求路径
			String url = request.getRequestURI();
			logger.info("插入操作日志:" + url);
			// 保存日志信息
			OperateLog operateLog = new OperateLog();
			operateLog.setOperateDesc(operate.value());
			// 参数
			RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
			String body = requestWrapper.getBody();
			operateLog.setRequestParams(body);
			operateLog.setStatus(BlogConstant.STATUS_VALID);
			// ip
			operateLog.setOperateIp(getIp());
			// 插入操作痕迹到数据库
			operateLogDao.insert(operateLog);
		}
		return point.proceed();
	}

	/**
	 * @description 获取操作机器ip
	 * @author 杨号  
	 * @date 2019年2月1日-下午3:44:58  
	 * @return
	 */
	private String getIp() {
		// 获取客户端ip
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			String ips = request.getHeader("x-forwarded-for");
			if(StringUtils.isNoneBlank(ips)) {
				ip = ips.contains(",") ? ip.split(",")[0] : ips;
			}
			if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		}
		return ip;
	}
}
