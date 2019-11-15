package com.wsirius.core.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = SunnyProperties.PREFIX)
public class SunnyProperties {

    public static final String PREFIX = "sunny";


}
