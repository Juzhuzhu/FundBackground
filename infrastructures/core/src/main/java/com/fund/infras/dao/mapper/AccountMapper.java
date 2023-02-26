package com.fund.infras.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fund.infras.dao.model.FundAccountPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 内容
 * <p>
 * Create at 2023/02/26 20:44
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/26
 * @since 1.0.0
 */
@Mapper
public interface AccountMapper extends BaseMapper<FundAccountPO> {
}
