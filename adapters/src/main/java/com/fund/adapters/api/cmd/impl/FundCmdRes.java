package com.fund.adapters.api.cmd.impl;

import com.fund.adapters.api.cmd.FundCmdRestApi;
import com.fund.dto.cmd.FundPurchaseCmd;
import com.fund.service.FundCmdService;
import com.fund.utils.NoDataResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

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

    private final FundCmdService fundCmdService;

    public FundCmdRes(FundCmdService fundCmdService) {
        this.fundCmdService = fundCmdService;
    }

    @Override
    public NoDataResult fundPurchase(HttpServletRequest request, @Valid @RequestBody FundPurchaseCmd cmd) {
        System.out.println("用户token是：：：" + request.getHeader("token"));
        //对应用户购买基金
        fundCmdService.purchase(request.getHeader("token"), cmd);
        return NoDataResult.success();
    }
}
