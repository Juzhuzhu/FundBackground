package com.fund.gateway;

import com.fund.dto.cmd.UserUpdateCmd;
import com.fund.service.UserCmdService;
import com.fund.vo.UserInfoResp;

import java.math.BigDecimal;

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

    /**
     * 获取用户信息
     *
     * @param phoneNumber   phoneNumber
     * @param passwordByMd5 password
     * @return UserInfoResp
     */
    UserInfoResp getUserByArgs(String phoneNumber, String passwordByMd5);

    /**
     * 获取用户状态
     *
     * @param id 用户id
     * @return Boolean
     */
    boolean getUserStatusById(String id);

    /**
     * 根据id保存token
     *
     * @param id    用户id
     * @param token 令牌
     */
    void saveUserById(String id, String token);


    /**
     * 根据user_id为条件删除account
     *
     * @param id user_id
     */
    void delectAccountByUserId(String id);

    /**
     * 根据主键id删除user
     *
     * @param id 主键id
     */
    void deleteUserById(String id);

    /**
     * 根据主键id修改用户信息
     *
     * @param userUpdateCmd UserUpdateCmd
     */
    Boolean updateUserById(UserUpdateCmd userUpdateCmd);

    /**
     * 用户充值
     *
     * @param token 令牌
     * @param rechargeNum 充值金额
     */
    void updateUserForAmount(String token, BigDecimal rechargeNum);
}
