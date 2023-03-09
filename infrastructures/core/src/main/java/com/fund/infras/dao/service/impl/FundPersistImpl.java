package com.fund.infras.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fund.infras.dao.mapper.FundMapper;
import com.fund.infras.dao.model.FundPO;
import com.fund.infras.dao.service.FundPersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 内容
 * <p>
 * Create at 2023/03/08 00:41
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
@Slf4j
@Component
public class FundPersistImpl extends ServiceImpl<FundMapper, FundPO> implements FundPersist {
}
