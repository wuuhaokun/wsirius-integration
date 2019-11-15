package com.wsirius.rbac.security.exception.handler;

import cn.hutool.json.JSONUtil;
//import com.wsirius.rbac.security.common.ApiResponse;
import com.wsirius.core.base.Result;
import com.wsirius.core.base.WStatus;
import com.wsirius.core.util.Results;
//import com.wsirius.rbac.security.common.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * <p>
 * 全局统一异常处理
 * </p>
 *
 * @package: com.xkcoding.rbac.security.exception.handler
 * @description: 全局统一异常处理
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 17:00
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handlerException(Exception e) {
        if (e instanceof NoHandlerFoundException) {
            log.error("【全局异常拦截】NoHandlerFoundException: 请求方法 {}, 请求路径 {}", ((NoHandlerFoundException) e).getRequestURL(), ((NoHandlerFoundException) e).getHttpMethod());
            //return ApiResponse.ofStatus(Status.REQUEST_NOT_FOUND);
            return Results.failure(WStatus.REQUEST_NOT_FOUND.toString(),"");
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            log.error("【全局异常拦截】HttpRequestMethodNotSupportedException: 当前请求方式 {}, 支持请求方式 {}", ((HttpRequestMethodNotSupportedException) e).getMethod(), JSONUtil.toJsonStr(((HttpRequestMethodNotSupportedException) e).getSupportedHttpMethods()));
            //return ApiResponse.ofStatus(Status.HTTP_BAD_METHOD);
            return Results.failure(WStatus.HTTP_BAD_METHOD.toString(),"");
        } else if (e instanceof MethodArgumentNotValidException) {
            log.error("【全局异常拦截】MethodArgumentNotValidException", e);
//            return ApiResponse.of(Status.BAD_REQUEST.getCode(), ((MethodArgumentNotValidException) e).getBindingResult()
//                    .getAllErrors()
//                    .get(0)
//                    .getDefaultMessage(), null);
            return Results.failure(WStatus.BAD_REQUEST.toString(),"");
        }
//        else if (e instanceof ConstraintViolationException) {
//            log.error("【全局异常拦截】ConstraintViolationException", e);
//            return ApiResponse.of(Status.BAD_REQUEST.getCode(), CollUtil.getFirst(((ConstraintViolationException) e).getConstraintViolations())
//                    .getMessage(), null);
//        } else if (e instanceof MethodArgumentTypeMismatchException) {
//            log.error("【全局异常拦截】MethodArgumentTypeMismatchException: 参数名 {}, 异常信息 {}", ((MethodArgumentTypeMismatchException) e).getName(), ((MethodArgumentTypeMismatchException) e).getMessage());
//            return ApiResponse.ofStatus(Status.PARAM_NOT_MATCH);
//        } else if (e instanceof HttpMessageNotReadableException) {
//            log.error("【全局异常拦截】HttpMessageNotReadableException: 错误信息 {}", ((HttpMessageNotReadableException) e).getMessage());
//            return ApiResponse.ofStatus(Status.PARAM_NOT_NULL);
//        } else if (e instanceof BadCredentialsException) {
//            log.error("【全局异常拦截】BadCredentialsException: 错误信息 {}", e.getMessage());
//            return ApiResponse.ofStatus(Status.USERNAME_PASSWORD_ERROR);
//        } else if (e instanceof DisabledException) {
//            log.error("【全局异常拦截】BadCredentialsException: 错误信息 {}", e.getMessage());
//            return ApiResponse.ofStatus(Status.USER_DISABLED);
//        } else if (e instanceof BaseException) {
//            log.error("【全局异常拦截】DataManagerException: 状态码 {}, 异常信息 {}", ((BaseException) e).getCode(), e.getMessage());
//            return ApiResponse.ofException((BaseException) e);
//        }

        log.error("【全局异常拦截】: 异常信息 {} ", e.getMessage());
        //return ApiResponse.ofStatus(Status.ERROR);
        return Results.failure(WStatus.ERROR.toString(),e.getMessage());
    }
}
