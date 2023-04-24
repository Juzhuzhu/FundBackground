package com.fund.infras.repo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fund.dto.cmd.FundPurchaseCmd;
import com.fund.exception.BizException;
import com.fund.gateway.FundCmdRepo;
import com.fund.infras.dao.model.FundTransactionRecordPO;
import com.fund.infras.dao.model.FundUserBalancePO;
import com.fund.infras.dao.model.FundUserPO;
import com.fund.infras.dao.service.FundTransactionRecordPersist;
import com.fund.infras.dao.service.FundUserBalancePersist;
import com.fund.infras.dao.service.UserPersist;
import com.fund.service.FundCmdService;
import com.fund.utils.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

import static com.fund.enumeration.CodeEnum.*;

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

    public FundCmdRepoImpl(UserPersist userPersist,
                           FundTransactionRecordPersist fundTransactionRecordPersist,
                           FundUserBalancePersist fundUserBalancePersist) {
        this.userPersist = userPersist;
        this.fundTransactionRecordPersist = fundTransactionRecordPersist;
        this.fundUserBalancePersist = fundUserBalancePersist;
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
    }
}
