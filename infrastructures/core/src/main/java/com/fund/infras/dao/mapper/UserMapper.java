package com.fund.infras.dao.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fund.infras.dao.model.FundUserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 分页查询用户列表
     *
     * @param page page
     * @param queryWrapper queryWrapper
     * @return IPage<FundUserPO>
     */
    IPage<FundUserPO> selectUserByPage(Page<FundUserPO> page, @Param(Constants.WRAPPER) Wrapper<FundUserPO> queryWrapper);
}
