package com.fund.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 后端返回到前端的错误信息
 * <p>
 * Create at 2023/02/26 00:25
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/26
 * @since 1.0.0
 */
@Data
public class ErrorInfo implements Serializable {
    /**
     * 日期
     */
    private String datetime;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String message;
    /**
     * 请求链接
     */
    private String url;

    public ErrorInfo(String datetime, String message, String url) {
        this.datetime = datetime;
        this.message = message;
        this.url = url;
    }

    public ErrorInfo(String datetime, Integer code, String message, String url) {
        this.datetime = datetime;
        this.code = code;
        this.message = message;
        this.url = url;
    }
}
