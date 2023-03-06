package com.fund.adapters.api.cmd;

import com.fund.dto.cmd.UserLoginCmd;
import com.fund.enumeration.CustomerServiceRestConst;
import com.fund.dto.cmd.UserRegisterCmd;
import com.fund.utils.NoDataResult;
import com.fund.utils.Result;
import com.fund.vo.UserInfoResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 用户登录、注册命令
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
//    @Parameter(name = "token", in = ParameterIn.HEADER, required = true, description = "认证token", example = "Bearer ") 测试swagger测试时传入header带token
    @ResponseBody
    @PostMapping(USER_LOGIN)
    Result<UserInfoResp> userLogin(@Valid @RequestBody UserLoginCmd userLoginCmd);


}
