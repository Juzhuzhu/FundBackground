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
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import static com.fund.enumeration.CodeEnum.UPDATE_BALANCE_ERROR;
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
        // 罗康明 TODO: 2023/4/27 校验该用户购买的基金是否已经持有，若已持有则增加余额
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
        //根据用户id，基金id，未售出为条件判断该用户目前是否持有该基金，持有该基金则进行更新，未持有则新增持有
        repo.saveOrUpdateUserBalance(userInfo.getUserId(), cmd);
    }

    /**
     * 用户售出对应持有基金
     *
     * @param token 用户令牌
     * @param id    主键id
     */
    @Transactional(rollbackFor = Exception.class)
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
        //用户持有该基金的余额
        BigDecimal balance = earningsInfo.getBalance();
        //计算手续费
        BigDecimal fundFee = balance.multiply(BigDecimal.valueOf(0.0015)).setScale(2, RoundingMode.HALF_UP);
        //计算用户余额+最终收益更新用户信息
        //最终收益=余额-手续费
        BigDecimal resultEarnings = balance.subtract(fundFee);
        BigDecimal userAmount = userInfo.getAmount().add(resultEarnings);
        repo.updateUserAmount(userInfo.getUserId(), userAmount);
        //生成一条售出基金记录
        repo.saveTransactionRecordForSale(userInfo.getUserId(), earningsInfo.getFundId(), balance);
    }

    /**
     * 计算每个用户的收益
     */
    @Transactional(rollbackFor = Exception.class)
    public void calculateEarnings() {
        //查询fund_user_balance表，获取所有未售出的集合（包含主键id，用户id，基金id，基金日期，最后计算收益的日期，当前余额）
        log.warn("———————————————————————获取所有用户持有的基金列表——————————————————————");
        List<UserBalanceInfo> userBalanceInfoList = repo.getUserBalanceInfoList();
        //遍历集合，计算收益
        userBalanceInfoList.forEach(userBalanceInfo -> {
            final BigDecimal[] balance = {userBalanceInfo.getBalance()};
            log.warn("当前计算的用户持有主键id为：{}，对应基金id为：{}", userBalanceInfo.getId(), userBalanceInfo.getFundId());
            //根据当前基金id到fund_list表查询出当前基金的信息（fund_code,fund_date）
            FundInfo fundInfo = repo.getFundInfo(userBalanceInfo.getFundId());
            //判断用户持有的基金最后计算收益的日期与fund_list当前的基金日期是否一致，一致则return跳出本次循环开始下一个计算，否则继续进行下一步
            if (userBalanceInfo.getLatestDate().equals(fundInfo.getFundDate())) {
                log.warn("当前用户持有收益已是最新值无需更新，主键id：{}，用户id：{}，基金id：{}", userBalanceInfo.getId(), userBalanceInfo.getUserId(), userBalanceInfo.getFundId());
                return;
            }
            //根据fund_code拼接动态表名，以大于用户持有的基金日期为条件查询出该基金的信息list->(fund_date,fund_nav,yesterdayFundNav)-以fund_date升序排序
            log.warn("前往对应的基金历史净值表查询信息，基金代码：{}", fundInfo.getFundCode());
            List<FundHisInfo> fundHisInfoList = repo.getFundNavList(fundInfo.getFundCode(), userBalanceInfo.getLatestDate());
            log.warn("基金历史净值：{}", fundHisInfoList);
            //遍历list，计算用户持有该基金的余额
            fundHisInfoList.forEach(fundHisInfo -> {
                BigDecimal fundNav = fundHisInfo.getFundNav();
                BigDecimal yesterdayFundNav = fundHisInfo.getYesterdayFundNav();
                //收益计算公式：每日收益率  =  （今日基金净值  -  昨日基金净值）/ 昨日基金净值
                //当日收益率
                BigDecimal yieldRate = fundNav.subtract(yesterdayFundNav).divide(yesterdayFundNav, 4, RoundingMode.HALF_UP);
                //计算最新余额 = 当前余额*收益率 + 当前余额
                BigDecimal earning = balance[0].multiply(yieldRate).setScale(2, RoundingMode.HALF_UP);
                balance[0] = balance[0].add(earning);
            });
            //计算完毕，更新fund_user_balance，以主键id为条件，更新当前余额，更新最后计算收益的日期为当前基金最新的基金日期
            log.warn("更新一次用户持有收益主键id：{}，用户id：{}，基金id：{}，计算基金日期：{}，最新余额为：{}", userBalanceInfo.getId(), userBalanceInfo.getUserId(), userBalanceInfo.getFundId(), fundInfo.getFundDate(), balance[0]);
            if (!repo.updateUserBalance(userBalanceInfo.getId(), balance[0], fundInfo.getFundDate())) {
                throw new BizException(UPDATE_BALANCE_ERROR.getMessage(), UPDATE_BALANCE_ERROR.getCode());
            }
        });
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

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class UserBalanceInfo {
        /**
         * 主键id
         */
        private String id;
        /**
         * 用户id
         */
        private String userId;

        /**
         * 基金id
         */
        private Integer fundId;

        /**
         * 基金日期
         */
        private Date fundDate;

        /**
         * 最后计算收益的日期
         */
        private Date latestDate;
        /**
         * 当前余额
         */
        private BigDecimal balance;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class FundInfo {
        /**
         * 基金代码
         */
        private String fundCode;
        /**
         * 基金日期
         */
        private Date fundDate;
    }

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class FundHisInfo {
        /**
         * 单位净值
         */
        private BigDecimal fundNav;
        /**
         * 昨日单位净值
         */
        private BigDecimal yesterdayFundNav;
        /**
         * 基金日期
         */
        private Date fundDate;
    }

}
