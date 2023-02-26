package com.fund.enumeration;

import lombok.Getter;

/**
 * 状态码枚举
 * <p>
 * Create at 2023/02/26 01:36
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/26
 * @since 1.0.0
 */
@Getter
public enum CodeEnum {
    //注册功能失败
    SAVE_USER_ERROR(5000, "用户注册失败"),
    ;

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 响应消息
     */
    private final String message;

    CodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
