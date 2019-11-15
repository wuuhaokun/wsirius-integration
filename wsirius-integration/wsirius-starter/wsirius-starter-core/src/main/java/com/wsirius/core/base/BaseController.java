package com.wsirius.core.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * 基础控制器
 *
 * @author bojiangzhou 2017-12-29
 */
public class BaseController {

    /**
     * 获取校验的错误信息
     * 
     * @param errors 包含错误的对象
     *
     * @return 默认的错误信息. 没有的话返回错误编码
     */
    protected String getErrorMessage(Errors errors) {
        String errorMessage = null;
        for (ObjectError error : errors.getAllErrors()) {
            if (StringUtils.isNotBlank(error.getDefaultMessage())) {
                errorMessage = ((FieldError) error).getField()+error.getDefaultMessage();
                break;
            } else {
                errorMessage = error.getCode();
            }
        }
        return errorMessage;
    }

}
