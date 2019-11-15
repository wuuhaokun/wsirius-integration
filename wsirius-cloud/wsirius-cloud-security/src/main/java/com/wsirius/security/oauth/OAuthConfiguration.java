package com.wsirius.security.oauth;

import com.wsirius.security.domain.service.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * OAuth 配置
 *
 * @author bojiangzhou 2018/11/03
 */
@Configuration
public class OAuthConfiguration {

    @Bean
    public OAuthProperties oAuthProperties() {
        return new OAuthProperties();
    }

    @Bean
    public CustomClientDetailsService clientDetailsService (ClientService clientService) {
        return new CustomClientDetailsService(clientService, oAuthProperties());
    }

}
