package com.yanger.common.config;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

 
/**
 * Created by chenqixuan on 17/10/25.
 * 集成RedisTemplate
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {
 
	private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
 
    //获取springboot配置文件的值 (get的时候获取)
    @Value("${spring.redis.hostName}")
    private String host;
 
    @Value("${spring.redis.password}")
    private String password;
 
 
    /**
     * @Bean 和 @ConfigurationProperties
     * 该功能在官方文档是没有提到的，我们可以把@ConfigurationProperties和@Bean和在一起使用。
     * 举个例子，我们需要用@Bean配置一个Config对象，Config对象有a，b，c成员变量需要配置，
     * 那么我们只要在yml或properties中定义了a=1,b=2,c=3，
     * 然后通过@ConfigurationProperties就能把值注入进Config对象中
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }
 
    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setUsePool(true);
        JedisPoolConfig config = getRedisConfig();
        factory.setPoolConfig(config);
        logger.info("JedisConnectionFactory bean init success.");
        return factory;
    }
 
 
    @Bean
    public RedisTemplate<?, ?> getRedisTemplate() {
        JedisConnectionFactory factory = getConnectionFactory();
        logger.info(this.host+","+factory.getHostName()+","+factory.getDatabase());
        logger.info(this.password+","+factory.getPassword());
        logger.info(factory.getPoolConfig().getMaxIdle());
//        factory.setHostName(this.host);
//        factory.setPassword(this.password);
        RedisTemplate<?, ?> template = new StringRedisTemplate(getConnectionFactory());
        return template;
    }
}
