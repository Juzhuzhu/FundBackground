package com.fund.repo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fund.entity.qry.FundHistoryQry;
import com.fund.entity.qry.FundListQry;
import com.fund.entity.resp.FundHistoryResp;
import com.fund.entity.resp.FundResp;
import com.fund.infras.dao.model.FundHistoryPO;
import com.fund.infras.dao.model.FundPO;
import com.fund.infras.dao.service.FundHistoryPersist;
import com.fund.infras.dao.service.FundPersist;
import com.fund.utils.RequestDynamicTableNameHelper;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import static com.fund.enumeration.CustomerServiceRestConst.TABLE_NAME_PREFIX;

/**
 * 内容
 * <p>
 * Create at 2023/03/08 00:33
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
@Repository
public class FundQueryRepoImpl {

    private static final Mapper MAPPER = Mapper.INSTANCE;

    private final FundPersist fundPersist;

    private final FundHistoryPersist fundHistoryPersist;

    public FundQueryRepoImpl(FundPersist fundPersist, FundHistoryPersist fundHistoryPersist) {
        this.fundPersist = fundPersist;
        this.fundHistoryPersist = fundHistoryPersist;
    }

    /**
     * 条件分页查询基金列表
     *
     * @param fundListQry FundListQry
     * @return IPage<FundResp>
     */
    public IPage<FundResp> fundList(FundListQry fundListQry) {
        Preconditions.checkNotNull(fundListQry, "分页查询基金列表条件不许为空，至少传入页码和Size");
        //组装查询条件
        LambdaQueryWrapper<FundPO> wrapper = Wrappers.lambdaQuery();
        wrapper.like(fundListQry.getFundCode() != null, FundPO::getFundCode, fundListQry.getFundCode())
                .like(fundListQry.getFundName() != null, FundPO::getFundName, fundListQry.getFundName());
        //组装查询页码条件
        Page<FundPO> page = new Page<>(fundListQry.getPageNumber(), fundListQry.getPageSize());
        IPage<FundPO> fundPoPage = fundPersist.page(page, wrapper);
        return fundPoPage.convert(MAPPER::toFundResp);
    }

    /**
     * 分页查询基金历史净值
     *
     * @param fundHistoryQry FundHistoryQry
     * @return IPage<FundHistoryResp>
     */
    public IPage<FundHistoryResp> fundHistoryList(FundHistoryQry fundHistoryQry) {
        Preconditions.checkNotNull(fundHistoryQry, "分页查询基金历史净值条件不允许为空！");
        if (StringUtils.isNotBlank(fundHistoryQry.getFundCode())) {
            RequestDynamicTableNameHelper.setRequestData(TABLE_NAME_PREFIX + fundHistoryQry.getFundCode());
        }
        //组装分页条件
        Page<FundHistoryPO> page = new Page<>(fundHistoryQry.getPageNumber(), fundHistoryQry.getPageSize());
        IPage<FundHistoryPO> poPage = fundHistoryPersist.page(page);
        //转换resp对象
        return poPage.convert(MAPPER::toHisResp);
    }

    @org.mapstruct.Mapper
    interface Mapper {

        Mapper INSTANCE = Mappers.getMapper(Mapper.class);

        /**
         * 将FundPO-》FundResp
         *
         * @param po FundPO
         * @return FundResp
         */
        FundResp toFundResp(FundPO po);

        /**
         * FundHistoryPO -》 FundHistoryResp
         *
         * @param po FundHistoryPO
         * @return FundHistoryResp
         */
        FundHistoryResp toHisResp(FundHistoryPO po);
    }
}
