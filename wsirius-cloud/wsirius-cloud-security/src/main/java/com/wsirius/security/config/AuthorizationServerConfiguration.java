package com.wsirius.security.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.wsirius.security.core.CustomUserDetailsService;
import com.wsirius.security.oauth.CustomClientDetailsService;

/**
 * 认证服务器配置
 *
 * @author bojiangzhou 2018/11/02
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final String FIELD_ACCESS_TOKEN = "oauth2:access_token:";

    private AuthenticationManager authenticationManager;
    private CustomClientDetailsService clientDetailsService;
    private CustomUserDetailsService userDetailsService;
    private DataSource dataSource;
    private RedisConnectionFactory redisConnectionFactory;

    public AuthorizationServerConfiguration(AuthenticationConfiguration authenticationConfiguration,
                                            CustomClientDetailsService clientDetailsService,
                                            CustomUserDetailsService userDetailsService,
                                            DataSource dataSource,
                                            RedisConnectionFactory redisConnectionFactory) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.clientDetailsService = clientDetailsService;
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource))
                .tokenStore(tokenStore())
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager)
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Bean
    @ConditionalOnMissingBean(RedisTokenStore.class)
    public RedisTokenStore tokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix(FIELD_ACCESS_TOKEN);
        return redisTokenStore;
    }

}
