package com.wsirius.captcha.message;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.wsirius.core.helper.LanguageHelper;

/**
 * default MessageSource for sunny-starter-captcha.
 *
 * @author bojiangzhou 2018/09/11
 */
public class CaptchaMessageSource {

    private CaptchaMessageSource() {}

    private static final MessageSource messageSource;

    static {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setBasename("com.wsirius.captcha.messages");
        bundleMessageSource.setDefaultEncoding("UTF-8");
        bundleMessageSource.setUseCodeAsDefaultMessage(true);
        messageSource = bundleMessageSource;
    }

    /**
     * @param code code
     * @return message
     */
    public static String getMessage(String code) {
        return messageSource.getMessage(code, null, code, LanguageHelper.locale());
    }

    /**
     * @param code code
     * @param args args
     * @return message
     */
    public static String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, code, LanguageHelper.locale());
    }
}
