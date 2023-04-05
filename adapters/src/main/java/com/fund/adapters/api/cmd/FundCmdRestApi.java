package com.fund.adapters.api.cmd;

import com.fund.enumeration.CustomerServiceRestConst;
import com.fund.utils.NoDataResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 基金Cmd操作
 * <p>
 * Create at 2023/03/30 23:11
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/30
 * @since 1.0.0
 */
@Tag(name = CustomerServiceRestConst.API_TAG_FUND_CMD)
public interface FundCmdRestApi {
    String FUND_ADD = CustomerServiceRestConst.FUND_ROOT + "/add";
    String FUND_UPDATE = CustomerServiceRestConst.FUND_ROOT + "/update";
    String FUND_DELETE = CustomerServiceRestConst.FUND_ROOT + "/delete";
    String FUND_TRANSACTION = CustomerServiceRestConst.FUND_ROOT + "/transaction";

    /**
     * 基金交易模块
     *
     * @param request HttpServletRequest
     * @return NoDataResult
     */
    @Parameter(name = "token", in = ParameterIn.HEADER, required = true, description = "认证token", example = CustomerServiceRestConst.EXAMPLE_TOKEN)
    @Operation(summary = "用户基金交易")
    @ResponseBody
    @PostMapping(FUND_TRANSACTION)
    NoDataResult fundTransaction(HttpServletRequest request);
}
