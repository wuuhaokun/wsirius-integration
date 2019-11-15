package com.wsirius.security.core;

import javax.servlet.http.HttpServletRequest;

import com.wsirius.captcha.CaptchaResult;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * 封装验证码
 *
 * @author bojiangzhou 2018/09/18
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    public static final String FIELD_CACHE_CAPTCHA = "cacheCaptcha";

    private String inputCaptcha;
    private String cacheCaptcha;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        cacheCaptcha = (String) request.getAttribute(FIELD_CACHE_CAPTCHA);
        inputCaptcha = request.getParameter(CaptchaResult.FIELD_CAPTCHA);
    }

    public String getInputCaptcha() {
        return inputCaptcha;
    }

    public String getCacheCaptcha() {
        return cacheCaptcha;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }

        CustomWebAuthenticationDetails that = (CustomWebAuthenticationDetails) object;

        return inputCaptcha != null ? inputCaptcha.equals(that.inputCaptcha) : that.inputCaptcha == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (inputCaptcha != null ? inputCaptcha.hashCode() : 0);
        return result;
    }
}
