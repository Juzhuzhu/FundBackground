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
    USER_TOKEN_NONE(5004, "TOKEN失效"),
    PHONE_NUM_EXIST(5005, "该手机号码已注册！"),
    FUND_CODE_NULL(5006, "基金代码为空"),
    FUND_CODE_DIFF(5007, "同一个页面请求的基金代码不一致"),
    DATA_SIZE_DIFF(5008, "X轴和Y轴数据量不一致"),
    UPDATE_USER_ERROR(5009, "用户更新失败"),
    USER_AMOUNT_LACK(5010, "用户余额不足"),
    UPDATE_AMOUNT_ERROR(5011, "更新余额失败"),
    SAVE_RECORD_ERROR(5012, "保存交易记录失败"),
    SAVE_BALANCE_ERROR(5013, "保存用户持有基金失败"),
    UPDATE_BALANCE_ERROR(5014, "更新用户持有基金失败");

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
