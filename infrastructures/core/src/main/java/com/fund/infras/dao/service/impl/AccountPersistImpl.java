package com.fund.infras.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fund.infras.dao.mapper.AccountMapper;
import com.fund.infras.dao.model.FundAccountPO;
import com.fund.infras.dao.service.AccountPersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 内容
 * <p>
 * Create at 2023/02/26 20:46
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/26
 * @since 1.0.0
 */
@Slf4j
@Component
public class AccountPersistImpl extends ServiceImpl<AccountMapper, FundAccountPO> implements AccountPersist {
}
