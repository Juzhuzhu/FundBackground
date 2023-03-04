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
    //Enum
    SAVE_USER_ERROR(5000, "用户注册失败"),
    USER_LOGIN_ERROR(5001, "账号或密码错误"),
    USER_STATUS_ERROR(5002, "该用户封禁中"),
    USER_TOKEN_CONFLICT(5003, "TOKEN冲突！有两个token一致"),
    USER_TOKEN_NONE(5004,"TOKEN失效");

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
