package com.fund.adapters.api.cmd;

import com.fund.enumeration.CustomerServiceRestConst;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 基金管理Cmd操作
 * <p>
 * Create at 2023/03/30 23:11
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/30
 * @since 1.0.0
 */
@Tag(name = CustomerServiceRestConst.API_TAG_USER_CMD)
public interface FundCmdRestApi {
    String FUND_ADD = CustomerServiceRestConst.FUND_ROOT + "/add";
    String FUND_UPDATE = CustomerServiceRestConst.FUND_ROOT + "/update";
    String FUND_DELETE = CustomerServiceRestConst.FUND_ROOT + "/delete";
}
