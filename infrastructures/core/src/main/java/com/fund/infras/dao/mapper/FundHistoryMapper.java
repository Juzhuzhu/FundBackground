package com.fund.infras.dao.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fund.infras.dao.model.FundHistoryPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 基金历史净值需要动态表名拦截
 * <p>
 * Create at 2023/03/08 23:33
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
@Mapper
@InterceptorIgnore(dynamicTableName = "false")
public interface FundHistoryMapper extends BaseMapper<FundHistoryPO> {
}
