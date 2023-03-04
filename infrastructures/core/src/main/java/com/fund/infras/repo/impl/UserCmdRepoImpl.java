package com.fund.infras.repo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fund.gateway.UserCmdRepo;
import com.fund.infras.dao.model.FundAccountPO;
import com.fund.infras.dao.model.FundUserPO;
import com.fund.infras.dao.service.AccountPersist;
import com.fund.infras.dao.service.UserPersist;
import com.fund.service.UserCmdService;
import com.fund.vo.UserInfoResp;
import com.google.common.base.Preconditions;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

/**
 * 持久层
 * <p>
 * Create at 2023/02/25 01:28
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/25
 * @since 1.0.0
 */
@Repository
public class UserCmdRepoImpl implements UserCmdRepo {
    private final UserPersist userPersist;

    private final AccountPersist accountPersist;
    private static final Mapper MAPPER = Mapper.INSTANCE;

    public UserCmdRepoImpl(UserPersist userPersist, AccountPersist accountPersist) {
        this.userPersist = userPersist;
        this.accountPersist = accountPersist;
    }

    @Override
    public boolean saveUserRegister(UserCmdService.UserInfo userInfo) {
        Preconditions.checkNotNull(userInfo, "UserCmdService应用层传入UserInfo对象为空,注册失败！");
        return userPersist.save(MAPPER.toUserPo(userInfo));
    }

    @Override
    public boolean getUserByNum(String phoneNumber) {
        Preconditions.checkNotNull(phoneNumber, "传入电话号码为空！");
        LambdaQueryWrapper<FundUserPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FundUserPO::getPhoneNumber, phoneNumber);
        BaseMapper<FundUserPO> baseMapper = userPersist.getBaseMapper();
        int count = baseMapper.selectCount(wrapper).intValue();
        return count == 0;
    }

    @Override
    public boolean saveAccount(UserCmdService.AccountInfo accountInfo) {
        Preconditions.checkNotNull(accountInfo, "UserCmdService应用层传入AccountInfo对象为空,注册失败！");
        return accountPersist.save(MAPPER.toAccPo(accountInfo));
    }

    @Override
    public boolean getUserStatusById(String id) {
        Preconditions.checkNotNull(id, "用户id为空");
        FundUserPO poById = userPersist.getById(id);
        int state = poById.getState();
        return state == 0;
    }

    @Override
    public UserInfoResp getUserByArgs(String phoneNumber, String passwordByMd5) {
        LambdaQueryWrapper<FundUserPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(phoneNumber != null, FundUserPO::getPhoneNumber, phoneNumber)
                .eq(passwordByMd5 != null, FundUserPO::getPassword, passwordByMd5);
        FundUserPO po = userPersist.getOne(wrapper);

        return MAPPER.toResp(po);
    }


    @Override
    public void saveUserById(String id, String token) {
        FundUserPO fundUserPo = new FundUserPO();
        fundUserPo.setId(id);
        fundUserPo.setToken(token);
        userPersist.updateById(fundUserPo);
    }


    @org.mapstruct.Mapper
    interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        /**
         * 将UserInfo转换成PO
         *
         * @param userInfo UserInfo
         * @return FundUserPO
         */
        @Mapping(target = "utcUpdated", ignore = true)
        @Mapping(target = "utcDeleted", ignore = true)
        @Mapping(target = "utcCreate", ignore = true)
        @Mapping(target = "token", ignore = true)
        @Mapping(target = "headImgUrl", ignore = true)
        FundUserPO toUserPo(UserCmdService.UserInfo userInfo);

        /**
         * AccountInfo->FundAccountPO
         *
         * @param accountInfo AccountInfo
         * @return FundAccountPO
         */
        @Mapping(target = "utcUpdated", ignore = true)
        @Mapping(target = "utcDeleted", ignore = true)
        @Mapping(target = "utcCreate", ignore = true)
        FundAccountPO toAccPo(UserCmdService.AccountInfo accountInfo);

        /**
         * 将FundUserPO转换成UserInfoResp
         *
         * @param po FundUserPO
         * @return UserInfoResp
         */
        UserInfoResp toResp(FundUserPO po);
    }
}
