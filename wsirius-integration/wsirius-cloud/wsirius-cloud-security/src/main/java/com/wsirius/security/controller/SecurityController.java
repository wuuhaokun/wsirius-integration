package com.wsirius.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wsirius.captcha.CaptchaImageHelper;
import com.wsirius.captcha.CaptchaMessageHelper;
import com.wsirius.captcha.CaptchaResult;
import com.wsirius.core.base.Result;
import com.wsirius.core.message.MessageAccessor;
import com.wsirius.core.userdetails.CustomUserDetails;
import com.wsirius.core.userdetails.DetailsHelper;
import com.wsirius.core.util.Results;
import com.wsirius.security.constant.SecurityConstants;
import com.wsirius.security.domain.entity.User;
import com.wsirius.security.domain.service.ConfigService;
import com.wsirius.security.domain.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author bojiangzhou 2018/03/28
 */
@Controller
public class SecurityController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

    private static final String LOGIN_PAGE = "login";

    private static final String INDEX_PAGE = "index";

    private static final String FIELD_ERROR_MSG = "errorMsg";
    private static final String FIELD_ENABLE_CAPTCHA = "enableCaptcha";

    @Autowired
    private CaptchaImageHelper captchaImageHelper;
    @Autowired
    private CaptchaMessageHelper captchaMessageHelper;
    @Autowired
    private UserService userService;
    @Autowired
    private ConfigService configService;

    @RequestMapping(value = {"/index", "/"})
    public String index() {
        return INDEX_PAGE;
    }

    @GetMapping("/login")
    public String login(HttpSession session, Model model, String username, String type) {
        // 错误信息
        String errorMsg = (String) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (StringUtils.isNotBlank(errorMsg)) {
            model.addAttribute(FIELD_ERROR_MSG, errorMsg);
        }
        // 用户名
        if (StringUtils.isNotBlank(username)) {
            model.addAttribute(User.FIELD_USERNAME, username);
            User user = userService.getUserByUsername(username);
            if (user != null && configService.isEnableCaptcha(user.getPasswordErrorTime())) {
                model.addAttribute(FIELD_ENABLE_CAPTCHA, true);
            }
        }
        // 登录类型
        type = StringUtils.defaultIfBlank(type, "account");
        model.addAttribute("type", type);

        return LOGIN_PAGE;
    }

    @GetMapping("/public/captcha.jpg")
    public void captcha(HttpServletResponse response) {
        captchaImageHelper.generateAndWriteCaptchaImage(response, SecurityConstants.SECURITY_KEY);
    }

    @GetMapping("/public/mobile/captcha")
    @ResponseBody
    public Result mobileCaptcha(@RequestParam String mobile) {
        User params = new User();
        params.setMobile(mobile);
        if (userService.count(params) != 1) {
            return Results.failure(MessageAccessor.getMessage("user.error.login.mobile.not-register"));
        }
        CaptchaResult captchaResult = captchaMessageHelper.generateMobileCaptcha(mobile, SecurityConstants.SECURITY_KEY);
        if (captchaResult.isSuccess()) {
            // 模拟发送验证码
            LOGGER.info("【Sunny】 您的短信验证码是 {}。若非本人发送，请忽略此短信。", captchaResult.getCaptcha());
            captchaResult.clearCaptcha();
            return Results.successWithData(captchaResult);
        }

        return Results.failure(captchaResult.getMessage());
    }

    /**
     * 调换到注册或绑定三方账号页面
     */
    @GetMapping("/signup")
    public String signup() {

        return "register-or-bind";
    }

    /**
     * 绑定三方账号
     */
    @PostMapping("/bind")
    public String bind(String username, String password, Model model, HttpServletRequest request) {
        try {
            userService.bindProvider(username, password, request);
        } catch (Exception e) {
            model.addAttribute(FIELD_ERROR_MSG, MessageAccessor.getMessage(e.getMessage()));
        }
        return INDEX_PAGE;
    }

    /**
     * 调换到注册页面
     */
    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @GetMapping("/user/self")
    @ResponseBody
    public Result self() {
        CustomUserDetails details = DetailsHelper.getUserDetails();

        return Results.successWithData(details);
    }

}
