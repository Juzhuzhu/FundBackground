package com.fund.infras.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fund.infras.dao.model.FundTransactionPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 内容
 * <p>
 * Create at 2023/04/05 23:30
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/05
 * @since 1.0.0
 */
@Mapper
public interface FundTransactionMapper extends BaseMapper<FundTransactionPO> {
}
