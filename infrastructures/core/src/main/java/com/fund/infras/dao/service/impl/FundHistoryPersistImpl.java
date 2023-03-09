package com.fund.infras.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fund.infras.dao.mapper.FundHistoryMapper;
import com.fund.infras.dao.model.FundHistoryPO;
import com.fund.infras.dao.service.FundHistoryPersist;
import org.springframework.stereotype.Component;

/**
 * 内容
 * <p>
 * Create at 2023/03/08 23:38
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
@Component
public class FundHistoryPersistImpl extends ServiceImpl<FundHistoryMapper, FundHistoryPO> implements FundHistoryPersist {
}
