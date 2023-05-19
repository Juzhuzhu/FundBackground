package com.fund.adapters.api.query.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fund.adapters.api.query.FundQueryRestApi;
import com.fund.entity.qry.FundHistoryQry;
import com.fund.entity.qry.FundListQry;
import com.fund.entity.resp.*;
import com.fund.infras.dao.entity.resp.FundUserBalanceResp;
import com.fund.infras.dao.mapper.FundUserBalanceMapper;
import com.fund.repo.FundQueryRepoImpl;
import com.fund.utils.PageRequest;
import com.fund.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

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

    private final FundUserBalanceMapper fundUserBalanceMapper;


    public FundQueryRes(FundQueryRepoImpl repo,
                        FundUserBalanceMapper fundUserBalanceMapper) {
        this.repo = repo;
        this.fundUserBalanceMapper = fundUserBalanceMapper;
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

    @Override
    public Result<FundOwnResultResp> fundOwnSearch(HttpServletRequest request, @RequestBody PageRequest pageRequest) {
        String token = request.getHeader("token");
        IPage<FundOwnResp> resp = repo.fundOwnSearch(token, pageRequest);
        List<FundOwnResp> list = resp.getRecords();
        //持有数
        int ownNum = list.size();
        //买入总额
        BigDecimal purchaseAmountSum = list.stream()
                .map(FundOwnResp::getPurchaseAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //余额总额
        BigDecimal balanceSum = list.stream()
                .map(FundOwnResp::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        String ratio = purchaseAmountSum + "/" + balanceSum;
        //利润
        BigDecimal profit = balanceSum.subtract(purchaseAmountSum);
        //响应对象组装
        FundOwnResultResp fundOwnResultResp = new FundOwnResultResp();
        fundOwnResultResp.setFundOwnRespIpage(resp);
        fundOwnResultResp.setOwnNum(ownNum);
        fundOwnResultResp.setRatio(ratio);
        fundOwnResultResp.setProfit(profit);
        return Result.ok(fundOwnResultResp);
    }

    @Override
    public Result<IPage<FundTransactionRecordResp>> transactionRecordSearch(HttpServletRequest request, PageRequest pageRequest) {
        String token = request.getHeader("token");
        IPage<FundTransactionRecordResp> resp = repo.transactionRecordSearch(token, pageRequest);
        return Result.ok(resp);
    }

    @Override
    public Result<IPage<FundUserBalanceResp>> fundUserBalanceSearch(@RequestBody PageRequest pageRequest) {
        Page<FundUserBalanceResp> page = new Page<>(pageRequest.getPageNumber(), pageRequest.getPageSize());
        IPage<FundUserBalanceResp> resp = fundUserBalanceMapper.selectUserBalanceByPage(page);
        return Result.ok(resp);
    }
}
