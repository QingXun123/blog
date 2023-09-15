package com.qxbase.blog.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author ALsW
 * @version 1.0.0
 * @since 2023-05-29
 */
@Configuration
@EnableSwagger2
//@EnableOpenApi
public class SwaggerConfig {

	 @Bean
	 public Docket createRestApi() {
	 	return new Docket(DocumentationType.OAS_30)
	 			.apiInfo(apiInfo()) // 用于生成API信息
	 			.select() // select()函数返回一个ApiSelectorBuilder实例,用来控制接口被swagger做成文档
	 			.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)) // 用于指定扫描哪个包下的接口
	 			.paths(PathSelectors.any())// 选择所有的API,如果你想只为部分API生成文档，可以配置这里
	 			.build();
	 }

	@Bean
	public Docket api1() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("reception")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.qxbase.blog.server.reception.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	 private ApiInfo apiInfo() {
	 	return new ApiInfoBuilder()
	 			.title("QxBase博客 API") //  可以用来自定义API的主标题
	 			.description("API for my application") // 可以用来描述整体的API
	 			// .termsOfServiceUrl("/swagger") // 用于定义服务的域名
	 			.version("1.0") // 可以用来定义版本。
	 			.build(); //
	 }

//	@Resource
//	private SwaggerProperties swaggerProperties;
//
//	@Bean
//	public Docket userDocket() {
//		return new Docket(DocumentationType.OAS_30)
//				//定义是否开启swagger
//				.enable(swaggerProperties.getEnable())
//				.groupName("blog")
//				//api展示信息
//				.apiInfo(apiInfo())
//				//接口调试地址
//				.host(swaggerProperties.getTryHost())
//				//过滤条件
//				.select()
//				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//				.build();
//	}
//
//	private ApiInfo apiInfo() {
//		//作者信息
//		Contact contact = new Contact("blog", "https://blog.qxbase.com", "947338658@qq.com");
//		return new ApiInfo(
//				swaggerProperties.getApplicationName() + "APi Doc",
//				swaggerProperties.getApplicationDescription(),
//				swaggerProperties.getApplicationVersion(),
//				"urn:tos",
//				contact,
//				"Apache 2.0",
//				"http://www.apache.org/licenses/LICENSE-2.0",
//				new ArrayList());
//	}

}
