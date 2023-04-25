package com.fund.gateway;

import com.fund.dto.cmd.FundPurchaseCmd;
import com.fund.service.FundCmdService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
     * @param userId     用户id
     * @param userAmount 剩余的钱
     */
    void updateUserAmount(String userId, BigDecimal userAmount);

    /**
     * 生成交易记录
     *
     * @param userId 用户id
     * @param cmd    FundPurchaseCmd
     */
    void saveTransactionRecord(String userId, FundPurchaseCmd cmd);

    /**
     * 增加用户基金持有
     *
     * @param userId 用户id
     * @param cmd    FundPurchaseCmd
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
     * @param userId  用户id
     * @param fundId  基金id
     * @param balance 售出金额
     */
    void saveTransactionRecordForSale(String userId, Integer fundId, BigDecimal balance);

    /**
     * 查询出所有用户持有的基金信息
     *
     * @return List<FundCmdService.UserBalanceInfo>
     */
    List<FundCmdService.UserBalanceInfo> getUserBalanceInfoList();

    /**
     * 根据当前基金id到fund_list表查询出当前基金的信息（fund_code,fund_nav,fund_date）
     *
     * @param fundId 基金id
     * @return FundCmdService.FundInfo
     */
    FundCmdService.FundInfo getFundInfo(Integer fundId);

    /**
     * 根据fund_code拼接动态表名，以大于用户持有的基金日期为条件查询出该基金的信息list->(fund_date,fund_nav,yesterdayFundNav)-以fund_date升序排序
     *
     * @param fundCode 基金代码
     * @param latestDate 用户持有对应基金最后计算收益的日期
     * @return List<FundCmdService.FundHisInfo>
     */
    List<FundCmdService.FundHisInfo> getFundNavList(String fundCode, Date latestDate);

    /**
     * 获取当前余额
     *
     * @param id balance表主键id
     * @return 余额
     */
    BigDecimal getBalanceById(String id);

    /**
     * 更新余额
     *
     * @param id            主键id
     * @param resultBalance 余额
     * @param fundDate      基金日期
     * @return 更新结果true or false
     */
    Boolean updateUserBalance(String id, BigDecimal resultBalance, Date fundDate);
}
