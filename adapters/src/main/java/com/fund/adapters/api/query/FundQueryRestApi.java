package com.fund.adapters.api.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fund.entity.qry.FundHistoryQry;
import com.fund.entity.qry.FundListQry;
import com.fund.entity.resp.*;
import com.fund.enumeration.CustomerServiceRestConst;
import com.fund.utils.PageRequest;
import com.fund.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 基金查询api
 * <p>
 * Create at 2023/03/07 23:10
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/07
 * @since 1.0.0
 */
@Tag(name = CustomerServiceRestConst.API_TAG_FUND_QUERY)
public interface FundQueryRestApi {

    String FUND_LIST = CustomerServiceRestConst.FUND_ROOT + "/list";

    String FUND_HISTORY = CustomerServiceRestConst.FUND_ROOT + "/history";

    String FUND_ECHARTS = CustomerServiceRestConst.FUND_ROOT + "/echarts";

    String FUND_OWN = CustomerServiceRestConst.FUND_ROOT + "/own";

    String FUND_TRANSACTION_RECORD = CustomerServiceRestConst.FUND_ROOT + "/transaction/record";

    /**
     * 分页查询基金列表
     *
     * @param fundListQry FundListQry
     * @return Result<IPage < FundResp>>
     */
    @Operation(summary = "分页查询基金列表")
    @ResponseBody
    @PostMapping(FUND_LIST)
    Result<IPage<FundResp>> fundListSearch(@RequestBody FundListQry fundListQry);

    /**
     * 查询某一基金的历史净值
     *
     * @param fundHistoryQry FundHistoryQry
     * @return Result<IPage < FundHistoryResp>>
     */
    @Operation(summary = "分页查询某一基金历史净值")
    @ResponseBody
    @PostMapping(FUND_HISTORY)
    Result<IPage<FundHistoryResp>> fundHistorySearch(@Valid @RequestBody FundHistoryQry fundHistoryQry);

    /**
     * 查询该基金所有净值Echarts
     *
     * @param fundCode 基金代码
     * @return Result<FundEchartsResp>
     */
    @Operation(summary = "查询该基金所有净值")
    @ResponseBody
    @PostMapping(FUND_ECHARTS)
    Result<FundEchartsResp> fundEcharts(@RequestParam("fundCode") String fundCode);


    /**
     * 用户查询自己持有的基金
     *
     * @param request     HttpServletRequest
     * @param pageRequest PageRequest
     * @return Result<IPage < FundOwnResp>>
     */
    @Parameter(name = "token", in = ParameterIn.HEADER, required = true, description = "认证token", example = CustomerServiceRestConst.EXAMPLE_TOKEN)
    @Operation(summary = "分页查询用户持有基金")
    @ResponseBody
    @PostMapping(FUND_OWN)
    Result<FundOwnResultResp> fundOwnSearch(HttpServletRequest request, @RequestBody PageRequest pageRequest);

    /**
     * 用户查询自己的交易记录
     *
     * @param request     HttpServletRequest
     * @param pageRequest PageRequest
     * @return Result<IPage < FundOwnResp>>
     */
    @Parameter(name = "token", in = ParameterIn.HEADER, required = true, description = "认证token", example = CustomerServiceRestConst.EXAMPLE_TOKEN)
    @Operation(summary = "用户查询自己的交易记录")
    @ResponseBody
    @PostMapping(FUND_TRANSACTION_RECORD)
    Result<IPage<FundTransactionRecordResp>> transactionRecordSearch(HttpServletRequest request, @RequestBody PageRequest pageRequest);
}
