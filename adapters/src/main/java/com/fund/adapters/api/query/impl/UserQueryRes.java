package com.fund.adapters.api.query.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fund.adapters.api.query.UserQueryRestApi;
import com.fund.enumeration.UserStateEnum;
import com.fund.infras.dao.entity.qry.UserQry;
import com.fund.infras.dao.entity.resp.UserResp;
import com.fund.infras.dao.mapper.UserMapper;
import com.fund.infras.dao.model.FundUserPO;
import com.fund.utils.DateUtils;
import com.fund.utils.Result;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.ZoneOffset;

/**
 * 用户查询实现类
 * <p>
 * Create at 2023/03/28 23:29
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/28
 * @since 1.0.0
 */
@Controller
public class UserQueryRes implements UserQueryRestApi {
    private static final Mapper MAPPER = Mapper.INSTANCE;
    private final UserMapper userMapper;

    public UserQueryRes(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Result<IPage<UserResp>> getUserList(@RequestBody UserQry userQry) {
        Page<FundUserPO> page = new Page<>(userQry.getPageNumber(), userQry.getPageSize());
        IPage<FundUserPO> userPoPage = userMapper.selectUserByPage(page, userQry);

        IPage<UserResp> respPage = userPoPage.convert(userPo -> {
            UserResp userResp = MAPPER.toResp(userPo);
            //设置状态
            userResp.setUserStateEnum(userPo.getState() == 0 ? UserStateEnum.NORMAL : UserStateEnum.FORBIDDEN);
            //从UTC -> 东八区
            userResp.setUtcCreate(DateUtils.toZone(userResp.getUtcCreate(), ZoneOffset.UTC, DateUtils.EIGHTH_TIME_ZONE));
            return userResp;
        });
        return Result.ok(respPage);
    }

    @org.mapstruct.Mapper
    interface Mapper {
        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        /**
         * FundUserPO -》 UserResp
         *
         * @param po FundUserPO
         * @return UserResp
         */
        @Mapping(target = "userStateEnum", ignore = true)
        UserResp toResp(FundUserPO po);
    }
}
