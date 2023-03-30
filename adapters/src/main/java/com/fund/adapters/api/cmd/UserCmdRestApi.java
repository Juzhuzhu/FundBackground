package com.fund.adapters.api.cmd;

import com.fund.dto.cmd.UserLoginCmd;
import com.fund.dto.cmd.UserUpdateCmd;
import com.fund.enumeration.CustomerServiceRestConst;
import com.fund.dto.cmd.UserRegisterCmd;
import com.fund.utils.NoDataResult;
import com.fund.utils.Result;
import com.fund.vo.UserInfoResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 用户登录、注册命令
 * 管理员管理用户命令
 * <p>
 * Create at 2023/02/20 17:46
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/20
 * @since 1.0.0
 */
@Tag(name = CustomerServiceRestConst.API_TAG_USER_CMD)
public interface UserCmdRestApi {
    String USER_REGISTER = CustomerServiceRestConst.USER_ROOT + "/register";
    String USER_LOGIN = CustomerServiceRestConst.USER_ROOT + "/login";
    String USER_DELETE = CustomerServiceRestConst.USER_ROOT + "/delete";
    String USER_UPDATE = CustomerServiceRestConst.USER_ROOT + "/update";
    String USER_ADD = CustomerServiceRestConst.USER_ROOT + "/add";

    /**
     * 用户注册
     *
     * @param userRegisterCmd 传入用户名，账号，密码
     * @return NoDataResult
     */
    @Operation(summary = "用户注册")
    @ResponseBody
    @PostMapping(USER_REGISTER)
    NoDataResult userRegister(@Valid @RequestBody UserRegisterCmd userRegisterCmd);

    /**
     * 用户登录
     *
     * @param userLoginCmd UserLoginCmd
     * @return Result<UserInfoResp>
     */
    @Operation(summary = "用户登录", security = {@SecurityRequirement(name = "Authorization")})
    @ResponseBody
    @PostMapping(USER_LOGIN)
    Result<UserInfoResp> userLogin(@Valid @RequestBody UserLoginCmd userLoginCmd);

    /**
     * 根据id删除对应用户
     *
     * @param id 用户id
     * @return NoDataResult
     */
    @Parameter(name = "token", in = ParameterIn.HEADER, required = true, description = "认证token", example = CustomerServiceRestConst.EXAMPLE_TOKEN)
    @Operation(summary = "根据id删除用户")
    @ResponseBody
    @PostMapping(USER_DELETE)
    NoDataResult deleteUserById(@RequestParam("id") String id);

    /**
     * 根据id修改用户信息
     *
     * @param userUpdateCmd UserUpdateCmd
     * @return NoDataResult
     */
    @Parameter(name = "token", in = ParameterIn.HEADER, required = true, description = "认证token", example = CustomerServiceRestConst.EXAMPLE_TOKEN)
    @Operation(summary = "根据id修改用户")
    @ResponseBody
    @PostMapping(USER_UPDATE)
    NoDataResult updateUserById(@RequestBody UserUpdateCmd userUpdateCmd);

    /**
     * 手动添加新用户
     *
     * @param userRegisterCmd UserLoginCmd
     * @return NoDataResult
     */
    @Parameter(name = "token", in = ParameterIn.HEADER, required = true, description = "认证token", example = CustomerServiceRestConst.EXAMPLE_TOKEN)
    @Operation(summary = "手动增加新用户", description = "默认密码123456")
    @ResponseBody
    @PostMapping(USER_ADD)
    NoDataResult addUser(@Valid @RequestBody UserRegisterCmd userRegisterCmd);
}
