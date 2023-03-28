package com.fund.enumeration;

import lombok.Getter;

/**
 * 内容
 * <p>
 * Create at 2023/03/27 22:29
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/27
 * @since 1.0.0
 */
@Getter
public enum UserStateEnum {

    //状态
    NORMAL("正常用户"),
    FORBIDDEN("禁用用户");
    /**
     * 用户状态
     */
    private final String userState;

    UserStateEnum(String userState) {
        this.userState = userState;
    }

}
