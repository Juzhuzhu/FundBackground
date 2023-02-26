package com.fund.infras.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户表
 * <p>
 * Create at 2023/02/24 23:12
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/24
 * @since 1.0.0
 */
@TableName(FundUserPO.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class FundUserPO extends SuperModel<FundUserPO> {

    public static final String TABLE_NAME = "fund_user";
    /**
     * 用户状态：0=正常，1=禁用
     */
    private int state;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户头像图片地址
     */
    private String headImgUrl;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户密码
     */
    private String password;

    /**
     * token，登录时动态生成
     */
    private String token;
}
