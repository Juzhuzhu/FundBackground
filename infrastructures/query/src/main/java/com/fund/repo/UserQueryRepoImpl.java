package com.fund.repo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fund.infras.dao.model.FundUserPO;
import com.fund.infras.dao.service.UserPersist;
import com.fund.utils.JwtUtils;
import org.springframework.stereotype.Repository;

/**
 * 用户查询仓储层
 * <p>
 * Create at 2023/03/28 23:35
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/28
 * @since 1.0.0
 */
@Repository
public class UserQueryRepoImpl {

    private final UserPersist userPersist;

    public UserQueryRepoImpl(UserPersist userPersist) {
        this.userPersist = userPersist;
    }

    /**
     * 根据token获取对应用户
     *
     * @param token 令牌
     * @return 用户信息
     */
    public FundUserPO getUserByToken(String token) {
        JwtUtils.checkToken(token);
        LambdaQueryWrapper<FundUserPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FundUserPO::getToken, token);
        return userPersist.getOne(wrapper);
    }
}
