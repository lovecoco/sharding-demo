package com.qq.shardingsphere.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
@Profile(value = {"dev","prod"})
public class SwaggerFox3Config {

    @Value("${spring.profiles.active}")
    private String env;

    @Bean
    public Docket getDocket() {

        List<RequestParameter> globalRequestParameters = new ArrayList<>();
        globalRequestParameters.add(
                new RequestParameterBuilder().name("token").required(false).description("登录返回").in(ParameterType.HEADER).build());
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfo("万拼接口文档", env + "环境", "v1", null, null, null, null, new ArrayList<>()))
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qq.shardingsphere.controller"))
                .build()
                .globalRequestParameters(globalRequestParameters);
    }



}
