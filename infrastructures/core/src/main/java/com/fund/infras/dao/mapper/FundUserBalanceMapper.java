package com.fund.infras.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fund.infras.dao.entity.resp.FundUserBalanceResp;
import com.fund.infras.dao.model.FundUserBalancePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 内容
 * <p>
 * Create at 2023/04/09 21:29
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/09
 * @since 1.0.0
 */
@Mapper
public interface FundUserBalanceMapper extends BaseMapper<FundUserBalancePO> {

    IPage<FundUserBalanceResp> selectUserBalanceByPage(Page<FundUserBalanceResp> page);
}
