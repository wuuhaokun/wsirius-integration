package com.wsirius.security.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * URI 参数替换工具
 *
 * @author bojiangzhou 2018/10/16
 */
public class UriParamsReplacer {

    /**
     * 将 uri 中的参数替换掉，参数需要用 {paramName} 做占位符
     * 
     * @param uri uri
     * @param params 参数
     * @return 替换后的uri
     */
    public static String replaceUri(String uri, Map<String, String> params) {
        if (StringUtils.containsAny(uri, "{", "}")) {
            String[] uriArgNames = StringUtils.substringsBetween(uri, "{", "}");
            for (String uriArgName : uriArgNames) {
                uri = uri.replace("{" + uriArgName + "}", params.get(uriArgName));
            }
        }
        return uri;
    }

}
