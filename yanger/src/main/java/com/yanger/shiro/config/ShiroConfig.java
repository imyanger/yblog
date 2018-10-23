package com.yanger.shiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.yanger.shiro.mgt.SessionManager;
import com.yanger.shiro.realm.ShiroRealm;
import com.yanger.shiro.util.ShiroCacheManager;

@SuppressWarnings({ "rawtypes" })
@Configuration
public class ShiroConfig {
	
	@Value("${spring.redis.host}")
    private String host;
	
    @Value("${spring.redis.port}")
    private int port;
    
    @Value("${spring.redis.timeout}")
    private int timeout;
    
    @Value("${spring.redis.password}")
    private String password;

	@Bean
	public ShiroCacheManager shiroCacheManager(RedisTemplate redisTemplate) {
		ShiroCacheManager shiroCacheManager = new ShiroCacheManager(redisTemplate);
		return shiroCacheManager;
	}

	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 注意过滤器配置顺序 不能颠倒
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
		filterChainDefinitionMap.put("/logout", "logout");
		
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		
		//拦截的路径
//		filterChainDefinitionMap.put("/**", "authc");
		
		// 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
		shiroFilterFactoryBean.setLoginUrl("/unauth");
		// 登录成功后要跳转的链接
		// shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权界面;
		// shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了 ）
	 *
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(1024);// 散列的次数
		return hashedCredentialsMatcher;
	}

	@Bean
	public ShiroRealm shiroRealm(RedisTemplate redisTemplate) {
		ShiroRealm shiroRealm = new ShiroRealm();
		// 设置缓存管理器
		shiroRealm.setCacheManager(shiroCacheManager(redisTemplate));
		// 加密设置
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(1024);
		// 设置认证密码算法及迭代复杂度
		shiroRealm.setCredentialsMatcher(credentialsMatcher);
		shiroRealm.setCachingEnabled(true);
		// 认证
		shiroRealm.setAuthenticationCachingEnabled(false);
		// 授权
		shiroRealm.setAuthorizationCachingEnabled(false);
		return shiroRealm;
	}
	
	@Bean
	public SecurityManager securityManager(RedisTemplate redisTemplate) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm(redisTemplate));
		// 自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager());
		// 自定义缓存实现 使用redis
		securityManager.setCacheManager(shiroCacheManager(redisTemplate));
		return securityManager;
	}

	 //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        SessionManager essionManager = new SessionManager();
        essionManager.setSessionDAO(redisSessionDAO());
        return essionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        // 配置缓存过期时间
        redisManager.setExpire(1800);
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }
    
	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}
