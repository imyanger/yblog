package com.yanger.common.aop;

import java.lang.reflect.Method;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.yanger.common.annotation.NoToken;
import com.yanger.common.annotation.Token;
import com.yanger.common.util.JwtUtils;
import com.yanger.common.vo.ApiResponse;
import com.yanger.common.vo.TokenMsg;

import lombok.extern.slf4j.Slf4j;


/**
 * token处理切面
 * @time 2018年6月22日 下午4:38:38
 */
@Slf4j
@Aspect
@Configuration
public class TokenAspect {

	@Value("${swagger.token-check:true}")
	private boolean tokenCheck = true;

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
		// swagger: token-cheack: false 时跳过token验证
		if(!tokenCheck){
			return point.proceed();
		}
		//是否需要token --需要token：1.类上Token注解，方法上没有NoToken 2.方法上Token注解
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
			String token = request.getHeader("token");
			log.info("TokenAspect--token：" + token);
			//校验token
			TokenMsg tokenMsg = JwtUtils.parse(TokenMsg.class, token);
			if(tokenMsg != null){
				request.setAttribute("user", tokenMsg);
			}else {
				//token解析失败，则直接返回失败信息
				HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
				ServletOutputStream os = response.getOutputStream();
				ApiResponse<String> api = new ApiResponse<String>();
				api.errorToken();
				String msg = JSON.toJSONString(api);
				os.write(msg.getBytes("UTF-8"));
				return null;
			}
		}
		return point.proceed();
	}
	
}
