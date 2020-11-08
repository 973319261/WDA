package com.gx.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 重要！如果你的项目引入junit测试，此处需要使用@WebAppConfiguration，如果没有使用junit使用@Configuration
 */
@Configuration
@EnableSwagger2 // 重要！
// @EnableWebMvc
@ComponentScan(basePackages = { "com.gx.web" }) // 扫描control所在的package请修改为你control所在package
// 如果要访问的话直接访问 http://localhost:8080/项目名称/swagger-ui.html 就看到文档了
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()// 选择哪些路径和API会生成document
				// 扫描指定包中的swagger注解
				// .apis(RequestHandlerSelectors.basePackage("com.gx.web"))
				// 扫描所有有注解的api，用这种方式更灵活
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				// 扫描所有的api(没有添加注解也可以扫描出来),用这种方式更直接
				// .apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	/**
	 * 这是匹配api的信息
	 * 
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				// 大标题
				.title("诊断小助手接口文档")
				// 描述
				.description("诊断小助手接口测试")
				// 版本号
				.version("1.0.0").termsOfServiceUrl("").license("").licenseUrl("").build();
	}
}
