package com.fund.infras.dao.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fund.infras.dao.mapper.UserMapper;
import com.fund.infras.dao.model.FundUserPO;
import com.fund.infras.dao.service.UserPersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 内容
 * <p>
 * Create at 2023/02/25 01:17
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/25
 * @since 1.0.0
 */
@Slf4j
@Component
public class UserPersistImpl extends ServiceImpl<UserMapper, FundUserPO> implements UserPersist {
}
