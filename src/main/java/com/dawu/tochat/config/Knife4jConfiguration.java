package com.dawu.tochat.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 创建于   : guohaotian
 * 创建时间 : 2021/11/30 0030 17:15
 * 详细描述 :
 */
//@EnableSwagger2WebMvc
@Configuration
@EnableKnife4j
@ConfigurationProperties(prefix = "swagger")
@Data
public class Knife4jConfiguration {

    public Boolean enabled;

    public String pathMapping;

    public String title;

    public String description;

    public String version;


    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .apiInfo(new ApiInfoBuilder()
                        .title(title)
                        .description(description)
                        //.termsOfServiceUrl("http://www.xx.com/")
                        //.contact(new Contact("aa", "bb", "cc"))
                        .version(version)
                        .build())
                //分组名称
                //.groupName("3.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .apis(RequestHandlerSelectors.basePackage("com.ocsmarter.cneutral.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

}
