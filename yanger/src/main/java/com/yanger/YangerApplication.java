package com.yanger;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan(basePackages = "com.yanger.*.dao")
@SpringBootApplication
@EnableCaching
public class YangerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(YangerApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(YangerApplication.class);
	}
	
}
