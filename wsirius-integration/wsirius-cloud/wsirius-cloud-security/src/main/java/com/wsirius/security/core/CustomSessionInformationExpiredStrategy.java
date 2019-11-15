package com.wsirius.security.core;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 并发登陆导致 Session 失效的处理策略类
 *
 * @author bojiangzhou 2018/10/30
 */
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomSessionInformationExpiredStrategy.class);

    @Override
//    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
//        HttpServletRequest request = event.getRequest();
//        HttpServletResponse response = event.getResponse();
//        LOGGER.warn("==> 并发登陆：当前账号在[{}]上登陆", request.getRequestURI());
//    }
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();
        LOGGER.warn("==> 并发登陆：当前账号在[{}]上登陆", request.getRequestURI());
    }
}
