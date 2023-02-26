package com.fund.gateway;

import com.fund.service.UserCmdService;

/**
 * 用户注册登录repo
 * <p>
 * Create at 2023/02/25 01:24
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/25
 * @since 1.0.0
 */
public interface UserCmdRepo {
    /**
     * 保存用户注册
     *
     * @param userInfo UserInfo
     * @return 返回boolean表示是否成功
     */
    boolean saveUserRegister(UserCmdService.UserInfo userInfo);

    /**
     * 根据电话号码查询该用户是否注册
     *
     * @param phoneNumber 电话号码
     * @return true or false
     */
    boolean getUserByNum(String phoneNumber);

    /**
     * 保存账号
     *
     * @param accountInfo AccountInfo
     * @return boolean
     */
    boolean saveAccount(UserCmdService.AccountInfo accountInfo);
}
