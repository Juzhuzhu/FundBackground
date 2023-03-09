package com.fund.adapters.api.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fund.entity.qry.FundHistoryQry;
import com.fund.entity.qry.FundListQry;
import com.fund.entity.resp.FundHistoryResp;
import com.fund.entity.resp.FundResp;
import com.fund.enumeration.CustomerServiceRestConst;
import com.fund.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
}
