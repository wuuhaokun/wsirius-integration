package com.wsirius.rbac.security.config;

//import com.wsirius.rbac.security.common.Status;
import com.wsirius.core.base.WStatus;
import com.wsirius.rbac.security.util.ResponseUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * <p>
 * Security 结果处理配置
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: Security 结果处理配置
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 17:31
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
public class SecurityHandlerConfig {

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> ResponseUtil.renderJson(response, WStatus.ACCESS_DENIED, null);
    }

}
