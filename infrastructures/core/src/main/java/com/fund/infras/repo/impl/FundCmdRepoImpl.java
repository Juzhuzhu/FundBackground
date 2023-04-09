package com.fund.infras.repo.impl;

import com.fund.dto.cmd.FundPurchaseCmd;
import com.fund.gateway.FundCmdRepo;
import com.fund.service.FundCmdService;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 内容
 * <p>
 * Create at 2023/04/09 22:04
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/09
 * @since 1.0.0
 */
@Repository
public class FundCmdRepoImpl implements FundCmdRepo {
    @Override
    public FundCmdService.UserInfo getUserInfo(String token) {
        return null;
    }

    @Override
    public void updateUserAmount(String userId, BigDecimal userAmount) {

    }

    @Override
    public void saveTransactionRecord(String userId, FundPurchaseCmd cmd) {

    }

    @Override
    public void saveUserBalance(String userId, FundPurchaseCmd cmd) {

    }
}
