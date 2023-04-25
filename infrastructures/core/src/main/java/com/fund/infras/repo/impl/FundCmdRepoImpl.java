package com.fund.infras.repo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fund.dto.cmd.FundPurchaseCmd;
import com.fund.exception.BizException;
import com.fund.gateway.FundCmdRepo;
import com.fund.infras.dao.model.*;
import com.fund.infras.dao.service.*;
import com.fund.service.FundCmdService;
import com.fund.utils.IdGenerator;
import com.fund.utils.RequestDynamicTableNameHelper;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.fund.enumeration.CodeEnum.*;
import static com.fund.enumeration.CustomerServiceRestConst.TABLE_NAME_PREFIX;

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

    private static final Mapper MAPPER = Mapper.INSTANCE;

    IdGenerator idGenerator = IdGenerator.getInstance();
    private final UserPersist userPersist;

    private final FundTransactionRecordPersist fundTransactionRecordPersist;

    private final FundUserBalancePersist fundUserBalancePersist;

    private final FundPersist fundPersist;

    private final FundHistoryPersist fundHistoryPersist;

    public FundCmdRepoImpl(UserPersist userPersist,
                           FundTransactionRecordPersist fundTransactionRecordPersist,
                           FundUserBalancePersist fundUserBalancePersist,
                           FundPersist fundPersist,
                           FundHistoryPersist fundHistoryPersist) {
        this.userPersist = userPersist;
        this.fundTransactionRecordPersist = fundTransactionRecordPersist;
        this.fundUserBalancePersist = fundUserBalancePersist;
        this.fundPersist = fundPersist;
        this.fundHistoryPersist = fundHistoryPersist;
    }

    @Override
    public FundCmdService.UserInfo getUserInfo(String token) {
        //组装查询条件
        LambdaQueryWrapper<FundUserPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StringUtils.isNotBlank(token), FundUserPO::getToken, token);
        FundUserPO po = userPersist.getOne(wrapper);
        return MAPPER.toUserInfo(po);
    }

    @Override
    public void updateUserAmount(String userId, BigDecimal userAmount) {
        FundUserPO po = new FundUserPO();
        po.setId(userId);
        po.setAmount(userAmount);
        if (Boolean.FALSE.equals(userPersist.updateById(po))) {
            throw new BizException(UPDATE_AMOUNT_ERROR.getMessage(), UPDATE_AMOUNT_ERROR.getCode());
        }
    }

    @Override
    public void saveTransactionRecord(String userId, FundPurchaseCmd cmd) {
        FundTransactionRecordPO po = new FundTransactionRecordPO();
        po.setId(String.valueOf(idGenerator.nextId()));
        po.setUserId(userId);
        po.setFundId(cmd.getFundId());
        po.setFundDate(cmd.getFundDate());
        po.setTradeType(0);
        po.setTransactionAmount(cmd.getPurchaseAmount());
        if (Boolean.FALSE.equals(fundTransactionRecordPersist.save(po))) {
            throw new BizException(SAVE_RECORD_ERROR.getMessage(), SAVE_RECORD_ERROR.getCode());
        }
    }

    @Override
    public void saveUserBalance(String userId, FundPurchaseCmd cmd) {
        FundUserBalancePO po = MAPPER.toBalancePo(cmd);
        po.setId(String.valueOf(idGenerator.nextId()));
        po.setUserId(userId);
        //刚买基金余额跟购入金额一致
        po.setBalance(cmd.getPurchaseAmount());
        po.setSoldStatus(0);
        //刚买最后计算收益的日期与当前基金日期一致
        po.setLatestDate(po.getFundDate());
        if (Boolean.FALSE.equals(fundUserBalancePersist.save(po))) {
            throw new BizException(SAVE_BALANCE_ERROR.getMessage(), SAVE_BALANCE_ERROR.getCode());
        }
    }

    @Override
    public FundCmdService.EarningsInfo getUserEarnings(String id) {
        //获取基金id与收益
        FundUserBalancePO po = fundUserBalancePersist.getById(id);
        FundUserBalancePO updatePo = new FundUserBalancePO();
        updatePo.setId(id);
        updatePo.setSoldStatus(1);
        if (Boolean.FALSE.equals(fundUserBalancePersist.updateById(updatePo))) {
            throw new BizException(UPDATE_BALANCE_ERROR.getMessage(), UPDATE_BALANCE_ERROR.getCode());
        }
        return MAPPER.toEarningsInfo(po);
    }

    @Override
    public void saveTransactionRecordForSale(String userId, Integer fundId, BigDecimal balance) {
        FundTransactionRecordPO po = new FundTransactionRecordPO();
        po.setId(String.valueOf(idGenerator.nextId()));
        po.setUserId(userId);
        po.setFundId(fundId);
        po.setFundDate(new Date());
        po.setTradeType(1);
        po.setTransactionAmount(balance);
        if (Boolean.FALSE.equals(fundTransactionRecordPersist.save(po))) {
            throw new BizException(SAVE_RECORD_ERROR.getMessage(), SAVE_RECORD_ERROR.getCode());
        }
    }

    @Override
    public List<FundCmdService.UserBalanceInfo> getUserBalanceInfoList() {
        LambdaQueryWrapper<FundUserBalancePO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FundUserBalancePO::getSoldStatus, 0);
        List<FundUserBalancePO> poList = fundUserBalancePersist.list(wrapper);
        return poList.stream().map(MAPPER::toBalanceInfo).toList();
    }

    @Override
    public FundCmdService.FundInfo getFundInfo(Integer fundId) {
        Preconditions.checkNotNull(fundId, "传入基金主键id为空");
        FundPO fundPo = fundPersist.getById(fundId);
        return MAPPER.toFundInfo(fundPo);
    }

    @Override
    public List<FundCmdService.FundHisInfo> getFundNavList(String fundCode, Date latestDate) {
        Preconditions.checkNotNull(latestDate, "查询传入的用户持有的基金日期为空");
        if (StringUtils.isNotBlank(fundCode)) {
            RequestDynamicTableNameHelper.setRequestData(TABLE_NAME_PREFIX + fundCode);
        }
        LambdaQueryWrapper<FundHistoryPO> wrapper = Wrappers.lambdaQuery();
        wrapper.gt(latestDate != null, FundHistoryPO::getFundDate, latestDate)
                .orderByAsc(FundHistoryPO::getFundDate);
        List<FundHistoryPO> poList = fundHistoryPersist.list(wrapper);
        List<FundCmdService.FundHisInfo> fundHisInfoList = poList.stream().map(MAPPER::toFundHisInfo).collect(Collectors.toList());
        fundHisInfoList.forEach(fundHisInfo -> {
            RequestDynamicTableNameHelper.setRequestData(TABLE_NAME_PREFIX + fundCode);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fundHisInfo.getFundDate());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date yesterdayDate = calendar.getTime();
            LambdaQueryWrapper<FundHistoryPO> yesterdayWrapper = Wrappers.lambdaQuery();
            yesterdayWrapper.eq(FundHistoryPO::getFundDate, yesterdayDate);
            FundHistoryPO po = fundHistoryPersist.getOne(yesterdayWrapper);
            fundHisInfo.setYesterdayFundNav(po.getFundNav());
        });
        return fundHisInfoList;
    }

    @Override
    public BigDecimal getBalanceById(String id) {
        Preconditions.checkArgument(StringUtils.isNotBlank(id), "传入id为空");
        FundUserBalancePO po = fundUserBalancePersist.getById(id);
        return po.getBalance();
    }

    @Override
    public Boolean updateUserBalance(String id, BigDecimal resultBalance, Date fundDate) {
        FundUserBalancePO po = new FundUserBalancePO();
        po.setId(id);
        po.setBalance(resultBalance);
        po.setLatestDate(fundDate);
        return fundUserBalancePersist.updateById(po);
    }

    @org.mapstruct.Mapper
    interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        /**
         * 查询出的FundUserPO-》FundCmdService.UserInfo
         *
         * @param po FundUserPO
         * @return FundCmdService.UserInfo
         */
        @Mapping(target = "userId", source = "id")
        FundCmdService.UserInfo toUserInfo(FundUserPO po);

        /**
         * FundPurchaseCmd -》 FundUserBalancePO
         *
         * @param cmd FundPurchaseCmd
         * @return FundUserBalancePO
         */
        @Mapping(target = "utcUpdated", ignore = true)
        @Mapping(target = "utcDeleted", ignore = true)
        @Mapping(target = "utcCreate", ignore = true)
        @Mapping(target = "userId", ignore = true)
        @Mapping(target = "soldStatus", ignore = true)
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "balance", ignore = true)
        FundUserBalancePO toBalancePo(FundPurchaseCmd cmd);

        /**
         * FundUserBalancePO -》FundCmdService.EarningsInfo
         *
         * @param po FundUserBalancePO
         * @return FundCmdService.EarningsInfo
         */
        FundCmdService.EarningsInfo toEarningsInfo(FundUserBalancePO po);

        /**
         * FundUserBalancePO -》 FundCmdService.UserBalanceInfo
         *
         * @param po FundUserBalancePO
         * @return FundCmdService.UserBalanceInfo
         */
        FundCmdService.UserBalanceInfo toBalanceInfo(FundUserBalancePO po);

        /**
         * FundPO -> FundCmdService.FundInfo
         *
         * @param po FundPO
         * @return FundCmdService.FundInfo
         */
        FundCmdService.FundInfo toFundInfo(FundPO po);

        /**
         * FundHistoryPO -> FundCmdService.FundHisInfo
         *
         * @param po FundHistoryPO
         * @return FundCmdService.FundHisInfo
         */
        @Mapping(target = "yesterdayFundNav", ignore = true)
        FundCmdService.FundHisInfo toFundHisInfo(FundHistoryPO po);
    }
}
