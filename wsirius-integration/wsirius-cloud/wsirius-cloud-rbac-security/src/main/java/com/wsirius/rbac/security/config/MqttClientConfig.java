package com.wsirius.rbac.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * JWT 配置
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: JWT 配置
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 13:42
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@ConfigurationProperties(prefix = "mqttclient.config")
@Data
public class MqttClientConfig {

    private String url = "m15.cloudmqtt.com";

    private long  port = 14375L;

    private String username = "wbpwjaso";

    private String password = "eO-kjpnhyvrI";


}
