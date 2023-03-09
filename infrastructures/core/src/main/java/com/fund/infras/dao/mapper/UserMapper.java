package com.fund.infras.dao.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fund.infras.dao.model.FundUserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * fund_user的持久层
 * <p>
 * Create at 2023/02/25 01:13
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/25
 * @since 1.0.0
 */
@Mapper
@InterceptorIgnore(dynamicTableName = "true")
public interface UserMapper extends BaseMapper<FundUserPO> {
}
