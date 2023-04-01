package com.fund.service;

import com.fund.dto.cmd.UserLoginCmd;
import com.fund.dto.cmd.UserRegisterCmd;
import com.fund.dto.cmd.UserUpdateCmd;
import com.fund.enumeration.CustomerServiceRestConst;
import com.fund.exception.BizException;
import com.fund.gateway.UserCmdRepo;
import com.fund.utils.IdGenerator;
import com.fund.utils.JwtUtils;
import com.fund.utils.PasswordUtils;
import com.fund.vo.UserInfoResp;
import com.google.common.base.Preconditions;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


import java.io.Serializable;
import java.util.Objects;

import static com.fund.enumeration.CodeEnum.*;

/**
 * 用户登录注册业务逻辑层
 * <p>
 * Create at 2023/02/25 00:34
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/25
 * @since 1.0.0
 */
@Slf4j
@Validated
@Service
public class UserCmdService {

    private static final Mapper MAPPER = Mapper.INSTANCE;

    private final UserCmdRepo userCmdRepo;

    IdGenerator idGenerator = IdGenerator.getInstance();

    public UserCmdService(UserCmdRepo userCmdRepo) {
        this.userCmdRepo = userCmdRepo;
    }

    /**
     * 用户注册应用层
     *
     * @param cmd UserRegisterCmd
     */
    @Transactional(rollbackFor = Exception.class)
    public void userRegister(@Valid UserRegisterCmd cmd) {
        Preconditions.checkNotNull(cmd, "注册传入参数为空");
        if (StringUtils.isBlank(cmd.getPassword())) {
            //手动新增用户未输入密码时，初始密码123456
            String defaultPassword = "123456";
            cmd.setPassword(defaultPassword);
        }
        //校验密码和确认密码是否一致
        Preconditions.checkArgument(Objects.equals(cmd.getPassword(), cmd.getConfirmPassword()), "密码和确认密码不一致");
        //校验是否已存在用户
        if (!userCmdRepo.getUserByNum(cmd.getPhoneNumber())) {
            throw new BizException(PHONE_NUM_EXIST.getMessage(), PHONE_NUM_EXIST.getCode());
        }

        //转换成UserInfo对象
        UserInfo userInfo = MAPPER.toInfo(cmd);
        String userId = String.valueOf(idGenerator.nextId());
        userInfo.setId(userId);
        userInfo.setState(0);
        //密码加密
        String password = cmd.getPassword();
        userInfo.setPassword(PasswordUtils.getMD5(password));
        //填充账号对象
        String accountId = String.valueOf(idGenerator.nextId());
        AccountInfo accountInfo = new AccountInfo()
                .setId(accountId)
                .setUserId(userId)
                .setOpenCode(cmd.getPhoneNumber() + "+")
                .setCategory(0);
        //保存到账号表
        if (!userCmdRepo.saveAccount(accountInfo)) {
            throw new BizException(SAVE_USER_ERROR.getMessage(), SAVE_USER_ERROR.getCode());
        }
        //保存到用户表
        if (!userCmdRepo.saveUserRegister(userInfo)) {
            throw new BizException(SAVE_USER_ERROR.getMessage(), SAVE_USER_ERROR.getCode());
        }
    }

    /**
     * 用户登录应用层
     *
     * @param userLoginCmd UserLoginCmd
     * @return UserInfoResp
     */
    @Transactional(rollbackFor = Exception.class)
    public UserInfoResp userLogin(@Valid UserLoginCmd userLoginCmd) {
        Preconditions.checkNotNull(userLoginCmd, "登录信息为空！");
        //校验账号密码是否一致
        String passwordByMd5 = PasswordUtils.getMD5(userLoginCmd.getPassword());
        UserInfoResp resp = userCmdRepo.getUserByArgs(userLoginCmd.getPhoneNumber(), passwordByMd5);
        //校验该用户是否为正常状态
        if (!userCmdRepo.getUserStatusById(resp.getId())) {
            throw new BizException(USER_STATUS_ERROR.getMessage(), USER_STATUS_ERROR.getCode());
        }
        Preconditions.checkNotNull(resp, "账号或密码错误！");
        //生成token
        String token = JwtUtils.createToken(resp.getId(), resp.getName());
        resp.setToken(token);
        //如果没有头像则设置默认头像
        if (resp.getHeadImgUrl() == null) {
            resp.setHeadImgUrl(CustomerServiceRestConst.DEFAULT_HEAD_IMG_URL);
        }
        //保存token
        userCmdRepo.saveUserById(resp.getId(), token);
        //包装vo对象返回
        return resp;
    }

    /**
     * 根据id删除用户业务层
     *
     * @param id 用户id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserById(String id) {
        Preconditions.checkNotNull(id, "删除用户传入id为空");
        //根据user_id为条件删除account
        userCmdRepo.delectAccountByUserId(id);
        //再根据id主键删除user
        userCmdRepo.deleteUserById(id);
    }

    /**
     * 根据id修改用户信息
     *
     * @param userUpdateCmd UserUpdateCmd
     */
    public void updateUserById(UserUpdateCmd userUpdateCmd) {
        Preconditions.checkNotNull(userUpdateCmd.getId(), "传入id为空");
        if (Boolean.FALSE.equals(userCmdRepo.updateUserById(userUpdateCmd))) {
            throw new BizException(UPDATE_USER_ERROR.getMessage(), UPDATE_USER_ERROR.getCode());
        }
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class UserInfo implements Serializable {
        /**
         * 主键id
         */
        @NotBlank(message = "传入的id不能为空")
        private String id;

        /**
         * 用户状态
         */
        @NotNull(message = "传入的用户状态不能为空")
        private Integer state;

        /**
         * 用户名
         */
        @NotBlank(message = "传入的用户名不能为空")
        private String name;

        /**
         * 电话号码
         */
        @NotBlank(message = "传入的电话号码不能为空")
        private String phoneNumber;

        /**
         * 密码
         */
        @NotBlank(message = "传入的密码不能为空")
        private String password;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @Accessors(chain = true)
    public static class AccountInfo implements Serializable {
        /**
         * 账号主键id
         */
        @NotBlank(message = "账号id不能为空")
        private String id;
        /**
         * 外键-用户id
         */
        @NotBlank(message = "用户id不能为空")
        private String userId;
        /**
         * 登录账号，如手机号，邮箱，每个账号用加号分隔
         */
        @NotBlank(message = "登录账号不能为空")
        private String openCode;
        /**
         * 账号类别：0=普通账号，1=管理员账号
         */
        @NotNull(message = "账号类别不能为空")
        private Integer category;
    }

    @org.mapstruct.Mapper
    interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        /**
         * 传入注册参数对象转换成info
         *
         * @param cmd UserRegisterCmd
         * @return UserInfo
         */
        @Mapping(target = "password", ignore = true)
        @Mapping(target = "state", ignore = true)
        @Mapping(target = "id", ignore = true)
        UserInfo toInfo(UserRegisterCmd cmd);

    }
}
