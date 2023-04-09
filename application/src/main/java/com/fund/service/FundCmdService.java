package com.fund.service;

import com.fund.dto.cmd.FundPurchaseCmd;
import com.fund.exception.BizException;
import com.fund.gateway.FundCmdRepo;
import com.fund.utils.JwtUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

import static com.fund.enumeration.CodeEnum.USER_AMOUNT_LACK;

/**
 * 基金cmd业务逻辑层
 * <p>
 * Create at 2023/04/09 21:33
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/09
 * @since 1.0.0
 */
@Validated
@Slf4j
@Service
public class FundCmdService {

    private final FundCmdRepo repo;

    public FundCmdService(FundCmdRepo repo) {
        this.repo = repo;
    }

    /**
     * 对应用户购买基金
     *
     * @param token 用户令牌
     * @param cmd   FundPurchaseCmd
     */
    @Transactional(rollbackFor = Exception.class)
    public void purchase(String token, FundPurchaseCmd cmd) {
        //校验token
        JwtUtils.checkToken(token);
        //获取用户信息，用户id，余额
        UserInfo userInfo = repo.getUserInfo(token);
        //比较余额是否小于购买基金金额-小于直接抛出异常结束
        if (cmd.getPurchaseAmount().compareTo(userInfo.getAmount()) < 0) {
            throw new BizException(USER_AMOUNT_LACK.getMessage(), USER_AMOUNT_LACK.getCode());
        }
        //计算用户余额
        BigDecimal userAmount = userInfo.getAmount().subtract(cmd.getPurchaseAmount());
        repo.updateUserAmount(userInfo.getUserId(), userAmount);
        //保存交易记录，及目前用户基金持有
        repo.saveTransactionRecord(userInfo.getUserId(), cmd);
        //增加用户基金持有
        repo.saveUserBalance(userInfo.getUserId(), cmd);
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class UserInfo {
        /**
         * 用户id
         */
        private String userId;
        /**
         * 用户余额
         */
        private BigDecimal amount;
    }

}
