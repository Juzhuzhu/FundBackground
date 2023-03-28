package com.fund.adapters.api.query.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fund.adapters.api.query.FundQueryRestApi;
import com.fund.entity.qry.FundHistoryQry;
import com.fund.entity.qry.FundListQry;
import com.fund.entity.resp.FundEchartsResp;
import com.fund.entity.resp.FundHistoryResp;
import com.fund.entity.resp.FundResp;
import com.fund.repo.FundQueryRepoImpl;
import com.fund.utils.Result;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * api实现
 * <p>
 * Create at 2023/03/08 00:27
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
@Controller
public class FundQueryRes implements FundQueryRestApi {

    private final FundQueryRepoImpl repo;


    public FundQueryRes(FundQueryRepoImpl repo) {
        this.repo = repo;
    }

    @Override
    public Result<IPage<FundResp>> fundListSearch(@RequestBody FundListQry fundListQry) {
        IPage<FundResp> resp = repo.fundList(fundListQry);
        return Result.ok(resp);
    }

    @Override
    public Result<IPage<FundHistoryResp>> fundHistorySearch(@Valid @RequestBody FundHistoryQry fundHistoryQry) {
        IPage<FundHistoryResp> resp = repo.fundHistoryList(fundHistoryQry);
        return Result.ok(resp);
    }

    @Override
    public Result<FundEchartsResp> fundEcharts(@RequestParam("fundCode") String fundCode) {
        FundEchartsResp resp = repo.fundEcharts(fundCode);
        return Result.ok(resp);
    }
}
