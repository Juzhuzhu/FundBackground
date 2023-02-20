package com.fund.adapters.api.cmd.impl;

import com.fund.adapters.api.cmd.UserCmdRestApi;
import org.springframework.stereotype.Controller;

/**
 * 内容
 * <p>
 * Create at 2023/02/20 22:28
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/20
 * @since 1.0.0
 */
@Controller()
public class UserCmdRes implements UserCmdRestApi {
    @Override
    public String Hello() {
        return "hello";
    }

}
