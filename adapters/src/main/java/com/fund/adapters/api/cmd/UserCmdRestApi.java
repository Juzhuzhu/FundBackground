package com.fund.adapters.api.cmd;

import com.fund.CustomerServiceRestConst;
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

    @ResponseBody
    @RequestMapping(USER_REGISTER)
    public String Hello();
}
