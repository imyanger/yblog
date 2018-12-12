package com.yanger.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description Swagger插件
 * @author 杨号
 * @date 2018年9月14日
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				// 当前包路径
				.apis(RequestHandlerSelectors.basePackage("com.yanger"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				// 页面标题
				.title("yblog Swagger2 API接口").description("yblog API接口文档")
				.termsOfServiceUrl("https://github.com/imyanger")
				// 创建人
				.contact(new Contact("yanger", "https://github.com/imyanger/", "550799932@qq.com")).version("1.0")
				.build();
	}

}
