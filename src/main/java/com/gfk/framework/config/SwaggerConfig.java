package com.gfk.framework.config;

import com.gfk.framework.jwt.JWTConstants;
import com.gfk.framework.properties.ApplicationInfoProperties;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Swagger2API文档的配置
 *
 * @author : wzl
 * @date : 2023/02/26
 * @description :
 **/
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    /**
     * 系统管理相关接口
     *
     * @return
     */
    @Bean
    public Docket createSystemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置分组名称
                .groupName("1-系统后台管理")
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(
                        // 使用swagger 多包扫描：https://blog.csdn.net/Melody_Susan/article/details/80339542
                        Predicates.or(
                                RequestHandlerSelectors.basePackage("com.gfk.business.system"),
//                                RequestHandlerSelectors.basePackage("com.gfk.business.common.file"),
                                RequestHandlerSelectors.basePackage("com.gfk.business.common.unavailable"),
                                RequestHandlerSelectors.basePackage("com.gfk.business.user")
//                                RequestHandlerSelectors.basePackage("com.gfk.business.common.gen")
                        ))
                // 扫描指定包中的swagger注解
                //.apis(RequestHandlerSelectors.basePackage("com.ruoyi.project.tool.swagger"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()));
    }

    /**
     * 业务相关接口
     *
     * @return
     */
    @Bean
    public Docket createDocument() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置分组名称
                .groupName("2-业务相关接口")
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(
                        // 使用swagger 多包扫描：https://blog.csdn.net/Melody_Susan/article/details/80339542
                        Predicates.or(
                                RequestHandlerSelectors.basePackage("com.gfk.business.data"),
                                RequestHandlerSelectors.basePackage("com.gfk.business.permission"),
                                RequestHandlerSelectors.basePackage("com.gfk.business.user"),
                                RequestHandlerSelectors.basePackage("com.gfk.business.project")
                        ))
                // 扫描指定包中的swagger注解
                //.apis(RequestHandlerSelectors.basePackage("com.ruoyi.project.tool.swagger"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()));
    }

//    /**
//     * 用户相关接口
//     *
//     * @return
//     */
//    @Bean
//    public Docket createUserApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
//                .apiInfo(apiInfo())
//                // 设置分组名称
//                .groupName("用户模块")
//                // 设置哪些接口暴露给Swagger展示
//                .select()
//                // 扫描所有有注解的api，用这种方式更灵活
////                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .apis(
//                        // 使用swagger 多包扫描：https://blog.csdn.net/Melody_Susan/article/details/80339542
//                        Predicates.or(
//                                RequestHandlerSelectors.basePackage("com.gfk.business.user")
//                        ))
//                // 扫描指定包中的swagger注解
//                //.apis(RequestHandlerSelectors.basePackage("com.ruoyi.project.tool.swagger"))
//                // 扫描所有 .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .securitySchemes(Collections.singletonList(securityScheme()));
//    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(ApplicationInfoProperties.getApiTitle())
                .description(ApplicationInfoProperties.getDescription())
                .contact(new Contact(ApplicationInfoProperties.getAuthor(), null, ApplicationInfoProperties.getContact()))
                .version(ApplicationInfoProperties.getVersion())
                .build();
    }


    /***
     * oauth2配置
     * 需要增加swagger授权回调地址
     * http://localhost:8888/webjars/springfox-swagger-ui/o2c.html
     * @return
     */
    @Bean
    SecurityScheme securityScheme() {
        return new ApiKey(JWTConstants.X_ACCESS_TOKEN, JWTConstants.X_ACCESS_TOKEN, "header");
    }

}
