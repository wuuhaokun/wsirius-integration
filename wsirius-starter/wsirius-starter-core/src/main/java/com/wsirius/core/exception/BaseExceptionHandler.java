package com.wsirius.core.exception;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.wsirius.core.base.Result;
import com.wsirius.core.constant.BaseResultEnums;
import com.wsirius.core.helper.LanguageHelper;
import com.wsirius.core.message.CoreMessageSource;
import com.wsirius.core.util.Results;


/**
 * 程序异常处理器
 */
@ControllerAdvice
public class BaseExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    /**
     * 拦截 {@link MessageException} 异常信息，直接返回消息
     *
     * @param ex MessageException
     */
    @ExceptionHandler(MessageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result process(MessageException ex) {
        LOGGER.debug(ex.getMessage(), ex);
        return Results.failure(ex.getCode(), ex.getMessage());
    }

    /**
     * 拦截 {@link IllegalArgumentException} 异常信息，返回 “数据校验不通过” 信息
     *
     * @param ex IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result process(IllegalArgumentException ex) {
        LOGGER.debug(ex.getMessage(), ex);
        try {
            String message = messageSource.getMessage(ex.getMessage(), null, LanguageHelper.locale());
            return Results.failure(ex.getMessage(), message);
        } catch (NoSuchMessageException e) {
            return Results.failure(BaseResultEnums.DATA_INVALID.code(), CoreMessageSource.getMessage(ex.getMessage()));
        }
    }

    /**
     * 拦截 {@link UpdateFailedException} 异常信息，返回 “记录不存在或版本不一致” 信息
     *
     * @param ex UpdateFailedException
     */
    @ExceptionHandler(UpdateFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result process(UpdateFailedException ex) {
        LOGGER.debug(ex.getMessage(), ex);
        return Results.failure(BaseResultEnums.UPDATE_FAILED.code(),
                CoreMessageSource.getMessage(BaseResultEnums.UPDATE_FAILED.code()));
    }

    /**
     * 拦截 {@link NotLoginException} 异常信息，返回 “请登录后再进行操作” 信息
     *
     * @param ex NotLoginException
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result process(NotLoginException ex) {
        LOGGER.debug(ex.getMessage(), ex);
        return Results.failure(BaseResultEnums.NOT_LOGIN.code(),
                CoreMessageSource.getMessage(BaseResultEnums.NOT_LOGIN.code()));
    }

    /**
     * 拦截 {@link CommonException} 异常信息
     *
     * @param ex CommonException
     */
    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result process(CommonException ex) {
        LOGGER.info(ex.getMessage(), ex);
        try {
            String message = messageSource.getMessage(ex.getCode(), ex.getParameters(), ex.getCode(), LanguageHelper.locale());
            return Results.failure(ex.getCode(), message);
        } catch (NoSuchMessageException e) {
            return Results.failure(ex.getCode(), ex.getCode());
        }
    }

    /**
     * 拦截 {@link RuntimeException} 异常信息，返回 “程序出现错误，请联系管理员” 信息
     *
     * @param ex 异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result process(RuntimeException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return Results.failure(BaseResultEnums.ERROR.code(), CoreMessageSource.getMessage(BaseResultEnums.ERROR.code()));
    }

    /**
     * 拦截 {@link Exception} 异常信息，返回 “程序出现错误，请联系管理员” 信息
     *
     * @param ex 异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result process(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return Results.failure(BaseResultEnums.ERROR.code(), CoreMessageSource.getMessage(BaseResultEnums.ERROR.code()));
    }

    /**
     * 拦截 {@link SQLException} 异常信息，返回 “数据操作错误，请联系管理员” 信息
     *
     * @param ex 异常
     */
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result process(SQLException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return Results.failure(BaseResultEnums.ERROR.code(), CoreMessageSource.getMessage(BaseResultEnums.ERROR.code()));
    }

    /**
     * 处理 NoHandlerFoundException 异常. <br/>
     * 需配置 [spring.mvc.throw-exception-if-no-handler-found=true]
     * 需配置 [spring.resources.add-mappings=false]
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleNotFoundException(NoHandlerFoundException ex){
        LOGGER.info(ex.getMessage(), ex);
        return Results.failure(BaseResultEnums.NOT_FOUND.code(), CoreMessageSource.getMessage(BaseResultEnums.NOT_FOUND.code()));
    }

}
