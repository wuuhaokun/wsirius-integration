package com.wsirius.core.util;

import com.wsirius.core.base.Result;
import com.wsirius.core.base.WBaseException;

/**
 * Result生成工具类
 *
 * @author bojiangzhou 2017-12-28
 */
public class Results {

    protected Results() {}

    public static Result newResult() {
        return new Result();

    }

    public static Result newResult(boolean success) {
        return new Result(success);
    }

    //
    // 业务调用成功
    // ----------------------------------------------------------------------------------------------------
    public static Result success() {
        return new Result();
    }

    public static Result success(String msg) {
        return new Result(true, null, msg);
    }

    public static Result success(String code, String msg) {
        return new Result(true, code, msg);
    }

    public static Result successWithData(Object data) {
        return new Result(true, null, null, data);
    }

    public static Result successWithData(Object data, String msg) {
        return new Result(true, null, msg, data);
    }

    public static Result successWithData(Object data, String code, String msg) {
        return new Result(true, code, msg, data);
    }

    //
    // 业务调用失败
    // ----------------------------------------------------------------------------------------------------
    public static Result failure() {
        return new Result(false);
    }

    public static Result failure(String msg) {
        return new Result(false, null, msg);
    }

    public static Result failure(String code, String msg) {
        return new Result(false, code, msg);
    }

    public static Result failureWithData(Object data) {
        return new Result(false, null, null, data);
    }

    public static Result failureWithData(Object data, String msg) {
        return new Result(false, null, msg, data);
    }

    public static Result failureWithData(Object data, String code, String msg) {
        return new Result(false, code, msg, data);
    }

    /**
     * 构造一个异常的API返回
     *
     * @param <T> {@link BaseException} 的子类
     * @param t   异常
     * @return ApiResponse
     */
    public static <T extends WBaseException> Result ofException(WBaseException t) {
        //return of(t.getCode(), t.getMessage(), t.getData());
        return new Result(false, t.getCode().toString(), t.getMessage(), t.getData());

    }

}
