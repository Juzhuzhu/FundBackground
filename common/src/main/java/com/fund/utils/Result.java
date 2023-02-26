package com.fund.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

/**
 * 接口公共返回对象
 * <p>
 * Create at 2023/02/20 21:50
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/20
 * @since 1.0.0
 */
@Data
public class Result<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 请求成功标识
     */
    private boolean success = true;
    /**
     * 响应码
     */
    private Integer code = OK_200;
    /**
     * 响应消息
     */
    private String message = OK_MSG;
    /**
     * 响应对象
     */
    private T data;
    /**
     * 响应列表
     */
    private Collection<T> dataList;
    /**
     * 响应扩展
     */
    private Serializable extend;
    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 默认响应码：200成功，500失败，401无权限
     */
    public static final int OK_200 = 200;
    public static final int ERROR_500 = 500;
    public static final int NO_AUTH_401 = 401;

    /**
     * 默认响应消息
     */
    public static final String OK_MSG = "成功";
    public static final String ERROR_MSG = "失败";
    public static final String NO_AUTH_MSG = "无权限";

    public static <T extends Serializable> Result<T> ok() {
        return Result.ok(OK_MSG);
    }

    public static <T extends Serializable> Result<T> ok(String message) {
        return Result.ok(null, null, null, message);
    }

    public static <T extends Serializable> Result<T> ok(T data) {
        return Result.ok(data, OK_MSG);
    }

    public static <T extends Serializable> Result<T> error(T data) {
        return Result.error(data, ERROR_MSG);
    }

    public static <T extends Serializable> Result<T> error(T data, String message) {
        return Result.error(data, null, null, message);
    }

    public static <T extends Serializable> Result<T> ok(T data, String message) {
        return Result.ok(data, null, null, message);
    }

    public static <T extends Serializable> Result<T> ok(Collection<T> dataList) {
        return Result.ok(dataList, OK_MSG);
    }

    public static <T extends Serializable> Result<T> ok(Collection<T> dataList, String message) {
        return Result.ok(null, dataList, null, message);
    }

    public static <T extends Serializable> Result<T> ok(T data, Serializable extend) {
        return Result.ok(data, null, extend, OK_MSG);
    }

    public static <T extends Serializable> Result<T> ok(Collection<T> dataList, Serializable extend) {
        return Result.ok(null, dataList, extend, OK_MSG);
    }

    public static <T extends Serializable> Result<T> error() {
        return Result.error(ERROR_MSG);
    }

    public static <T extends Serializable> Result<T> error(String message) {
        return Result.error(ERROR_500, message);
    }

    public static <T extends Serializable> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        result.success = false;
        return result;
    }

    /**
     * 无权访问
     */
    public static <T extends Serializable> Result<T> noAuth() {
        return Result.error(NO_AUTH_401, NO_AUTH_MSG);
    }

    private static <T extends Serializable> Result<T> ok(T data, Collection<T> dataList,
                                                         Serializable extend, String message) {
        Result<T> result = new Result<>();
        result.code = OK_200;
        result.data = data;
        result.dataList = dataList;
        result.extend = extend;
        result.message = message;
        return result;
    }

    private static <T extends Serializable> Result<T> error(T data, Collection<T> dataList,
                                                            Serializable extend, String message) {
        Result<T> result = new Result<>();
        result.code = ERROR_500;
        result.data = data;
        result.dataList = dataList;
        result.extend = extend;
        result.message = message;
        return result;
    }

}
