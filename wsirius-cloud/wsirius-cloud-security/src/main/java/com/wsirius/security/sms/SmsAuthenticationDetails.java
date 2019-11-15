package com.wsirius.security.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.wsirius.captcha.CaptchaResult;

/**
 * 封装短信验证码
 *
 * @author bojiangzhou 2018/09/23
 */
public class SmsAuthenticationDetails extends WebAuthenticationDetails {

    private String inputCaptcha;
    private String captchaKey;

    public SmsAuthenticationDetails(HttpServletRequest request) {
        super(request);
        captchaKey = request.getParameter(CaptchaResult.FIELD_CAPTCHA_KEY);
        inputCaptcha = request.getParameter(CaptchaResult.FIELD_CAPTCHA);
    }

    public String getInputCaptcha() {
        return inputCaptcha;
    }

    public String getCaptchaKey() {
        return captchaKey;
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

        SmsAuthenticationDetails that = (SmsAuthenticationDetails) object;

        return inputCaptcha != null ? inputCaptcha.equals(that.inputCaptcha) : that.inputCaptcha == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (inputCaptcha != null ? inputCaptcha.hashCode() : 0);
        return result;
    }
}
