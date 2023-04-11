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
import org.apache.commons.lang3.StringUtils;
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
        if (userInfo.getAmount().compareTo(cmd.getPurchaseAmount()) < 0) {
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

    /**
     * 用户售出对应持有基金
     *
     * @param token 用户令牌
     * @param id    主键id
     */
    public void sale(String token, String id) {
        //校验token
        JwtUtils.checkToken(token);
        //获取用户信息，用户id，余额
        UserInfo userInfo = repo.getUserInfo(token);
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("传入持有基金id为空");
        }
        //根据持有基金获取收益与对应基金id并更新售出状态为已售出
        EarningsInfo earningsInfo = repo.getUserEarnings(id);
        //计算用户余额+收益更新用户信息
        BigDecimal userAmount = userInfo.getAmount().add(earningsInfo.getBalance());
        repo.updateUserAmount(userInfo.getUserId(), userAmount);
        //生成一条售出基金记录
        repo.saveTransactionRecordForSale(userInfo.getUserId(), earningsInfo.getFundId(), earningsInfo.getBalance());
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

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class EarningsInfo {
        /**
         * 基金id
         */
        private Integer fundId;
        /**
         * 持有基金目前余额
         */
        private BigDecimal balance;
    }

}
