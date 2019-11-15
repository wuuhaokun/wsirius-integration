package com.wsirius.swagger.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.spring4all.swagger.EnableSwagger2Doc;

/**
 * @version 1.0
 * @author bojiangzhou 2018-02-19
 */
@Configuration
@EnableSwagger2Doc
@PropertySource(value = "classpath:application-swagger.properties")
public class SwaggerConfiguration implements WebMvcConfigurer {
    /**
     * 注入swagger资源文件
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}

