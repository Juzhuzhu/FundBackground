package com.fund.infras.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fund.infras.dao.mapper.FundTransactionRecordMapper;
import com.fund.infras.dao.model.FundTransactionRecordPO;
import com.fund.infras.dao.service.FundTransactionRecordPersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 内容
 * <p>
 * Create at 2023/04/05 23:36
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/05
 * @since 1.0.0
 */
@Slf4j
@Component
public class FundTransactionRecordPersistImpl extends ServiceImpl<FundTransactionRecordMapper, FundTransactionRecordPO> implements FundTransactionRecordPersist {
}
