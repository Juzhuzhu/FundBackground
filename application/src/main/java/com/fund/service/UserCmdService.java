package com.fund.service;

import com.fund.dto.cmd.UserRegisterCmd;
import com.fund.exception.BizException;
import com.fund.gateway.UserCmdRepo;
import com.fund.utils.IdGenerator;
import com.fund.utils.PasswordUtils;
import com.google.common.base.Preconditions;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


import java.io.Serializable;

import static com.fund.enumeration.CodeEnum.SAVE_USER_ERROR;

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

    @Transactional(rollbackFor = Exception.class)
    public void userRegister(@Valid UserRegisterCmd cmd) {
        Preconditions.checkNotNull(cmd, "注册传入参数为空");
        //校验是否已存在用户
        Preconditions.checkArgument(userCmdRepo.getUserByNum(cmd.getPhoneNumber()), "该手机号码已注册！");

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
