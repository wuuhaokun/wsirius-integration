package com.wsirius.core.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author bojiangzhou 2018-02-19
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@PropertySource(value = "classpath:application-core.properties")
@EnableConfigurationProperties(SunnyProperties.class)
public class SunnyAutoConfiguration {

    /**
     * messageSource
     */
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }



}
