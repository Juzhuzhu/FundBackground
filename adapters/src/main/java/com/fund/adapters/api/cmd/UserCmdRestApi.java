package com.fund.adapters.api.cmd;

import com.fund.CustomerServiceRestConst;
import com.fund.dto.cmd.UserRegisterCmd;
import com.fund.utils.NoDataResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
public interface UserCmdRestApi {
    String USER_REGISTER = CustomerServiceRestConst.USER_ROOT + "/register";

    /**
     * 用户注册
     *
     * @param userRegisterCmd 传入用户名，账号，密码
     * @return NoDataResult
     */
    @ResponseBody
    @RequestMapping(USER_REGISTER)
    NoDataResult userRegister(@Valid @RequestBody UserRegisterCmd userRegisterCmd);
}
