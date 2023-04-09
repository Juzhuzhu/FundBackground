package com.fund.infras.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fund.infras.dao.mapper.FundUserBalanceMapper;
import com.fund.infras.dao.model.FundUserBalancePO;
import com.fund.infras.dao.service.FundUserBalancePersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 内容
 * <p>
 * Create at 2023/04/09 21:30
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/09
 * @since 1.0.0
 */
@Slf4j
@Component
public class FundUserBalancePersistImpl extends ServiceImpl<FundUserBalanceMapper, FundUserBalancePO>
        implements FundUserBalancePersist {
}
