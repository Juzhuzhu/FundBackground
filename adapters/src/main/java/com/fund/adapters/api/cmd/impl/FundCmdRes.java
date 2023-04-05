package com.fund.adapters.api.cmd.impl;

import com.fund.adapters.api.cmd.FundCmdRestApi;
import com.fund.utils.NoDataResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 * 内容
 * <p>
 * Create at 2023/04/06 00:00
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/06
 * @since 1.0.0
 */
@Slf4j
@Controller
public class FundCmdRes implements FundCmdRestApi {

    @Override
    public NoDataResult fundTransaction(HttpServletRequest request) {
        System.out.println("用户token是：：：" + request.getHeader("token"));
        return NoDataResult.success();
    }
}
