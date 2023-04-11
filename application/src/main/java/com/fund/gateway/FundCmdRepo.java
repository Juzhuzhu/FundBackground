package com.fund.gateway;

import com.fund.dto.cmd.FundPurchaseCmd;
import com.fund.service.FundCmdService;

import java.math.BigDecimal;

/**
 * 基金Repo
 * <p>
 * Create at 2023/04/09 21:43
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/09
 * @since 1.0.0
 */
public interface FundCmdRepo {
    /**
     * 根据token获取对象信息
     *
     * @param token 用户令牌
     * @return UserInfo
     */
    FundCmdService.UserInfo getUserInfo(String token);

    /**
     * 更新用户信息，购买基金要余额扣款
     *
     * @param userId 用户id
     * @param userAmount 剩余的钱
     */
    void updateUserAmount(String userId, BigDecimal userAmount);

    /**
     * 生成交易记录
     *
     * @param userId 用户id
     * @param cmd FundPurchaseCmd
     */
    void saveTransactionRecord(String userId, FundPurchaseCmd cmd);

    /**
     * 增加用户基金持有
     *
     * @param userId 用户id
     * @param cmd FundPurchaseCmd
     */
    void saveUserBalance(String userId, FundPurchaseCmd cmd);


    /**
     * 根据基金获取基金收益，并更新售出状态
     *
     * @param id 持有基金id
     * @return 收益与基金id
     */
    FundCmdService.EarningsInfo getUserEarnings(String id);

    /**
     * 保存一条售出基金记录
     *
     * @param userId 用户id
     * @param fundId 基金id
     * @param balance 售出金额
     */
    void saveTransactionRecordForSale(String userId, Integer fundId, BigDecimal balance);
}
