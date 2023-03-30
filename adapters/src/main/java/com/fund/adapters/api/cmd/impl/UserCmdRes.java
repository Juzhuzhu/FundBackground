package com.fund.adapters.api.cmd.impl;

import com.fund.adapters.api.cmd.UserCmdRestApi;
import com.fund.dto.cmd.UserLoginCmd;
import com.fund.dto.cmd.UserRegisterCmd;
import com.fund.dto.cmd.UserUpdateCmd;
import com.fund.service.UserCmdService;
import com.fund.utils.NoDataResult;
import com.fund.utils.Result;
import com.fund.vo.UserInfoResp;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户注册登录命令
 * <p>
 * Create at 2023/02/20 22:28
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/20
 * @since 1.0.0
 */
@Slf4j
@Controller
public class UserCmdRes implements UserCmdRestApi {

    private final UserCmdService userCmdService;

    public UserCmdRes(UserCmdService userCmdService) {
        this.userCmdService = userCmdService;
    }

    @Override
    public NoDataResult userRegister(@Valid @RequestBody UserRegisterCmd userRegisterCmd) {
        userCmdService.userRegister(userRegisterCmd);
        return NoDataResult.success();
    }

    @Override
    public Result<UserInfoResp> userLogin(@Valid @RequestBody UserLoginCmd userLoginCmd) {
        UserInfoResp userInfoResp = userCmdService.userLogin(userLoginCmd);
        return Result.ok(userInfoResp);
    }

    @Override
    public NoDataResult deleteUserById(@RequestParam("id") String id) {
        userCmdService.deleteUserById(id);
        return NoDataResult.success();
    }

    @Override
    public NoDataResult updateUserById(@RequestBody UserUpdateCmd userUpdateCmd) {
        userCmdService.updateUserById(userUpdateCmd);
        return NoDataResult.success();
    }

    @Override
    public NoDataResult addUser(@Valid @RequestBody UserRegisterCmd userRegisterCmd) {
        userCmdService.userRegister(userRegisterCmd);
        return NoDataResult.success();
    }
}
