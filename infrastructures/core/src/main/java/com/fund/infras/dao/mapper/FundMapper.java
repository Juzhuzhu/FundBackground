package com.fund.infras.dao.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fund.infras.dao.model.FundPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 内容
 * <p>
 * Create at 2023/03/08 00:40
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
@Mapper
@InterceptorIgnore(dynamicTableName = "true")
public interface FundMapper extends BaseMapper<FundPO> {
}
