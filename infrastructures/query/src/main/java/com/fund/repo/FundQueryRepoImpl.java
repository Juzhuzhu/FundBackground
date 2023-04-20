package com.fund.repo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fund.entity.qry.FundHistoryQry;
import com.fund.entity.qry.FundListQry;
import com.fund.entity.resp.*;
import com.fund.exception.BizException;
import com.fund.infras.dao.model.*;
import com.fund.infras.dao.service.FundHistoryPersist;
import com.fund.infras.dao.service.FundPersist;
import com.fund.infras.dao.service.FundTransactionRecordPersist;
import com.fund.infras.dao.service.FundUserBalancePersist;
import com.fund.utils.DateUtils;
import com.fund.utils.JwtUtils;
import com.fund.utils.PageRequest;
import com.fund.utils.RequestDynamicTableNameHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static com.fund.enumeration.CodeEnum.*;
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

    private final FundUserBalancePersist fundUserBalancePersist;

    private final UserQueryRepoImpl userQueryRepo;

    private final FundTransactionRecordPersist fundTransactionRecordPersist;

    private static final HashMap<String, FundEchartsResp> FUND_ECHARTS_RESP_HASH_MAP = Maps.newHashMap();

    public FundQueryRepoImpl(FundPersist fundPersist,
                             FundHistoryPersist fundHistoryPersist,
                             FundUserBalancePersist fundUserBalancePersist,
                             UserQueryRepoImpl userQueryRepo,
                             FundTransactionRecordPersist fundTransactionRecordPersist) {
        this.fundPersist = fundPersist;
        this.fundHistoryPersist = fundHistoryPersist;
        this.fundUserBalancePersist = fundUserBalancePersist;
        this.userQueryRepo = userQueryRepo;
        this.fundTransactionRecordPersist = fundTransactionRecordPersist;
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
            //动态组装表名
            RequestDynamicTableNameHelper.setRequestData(TABLE_NAME_PREFIX + fundHistoryQry.getFundCode());
        }
        //组装分页条件
        Page<FundHistoryPO> page = new Page<>(fundHistoryQry.getPageNumber(), fundHistoryQry.getPageSize());
        IPage<FundHistoryPO> poPage = fundHistoryPersist.page(page);
        //转换resp对象
        return poPage.convert(MAPPER::toHisResp);
    }

    /**
     * 获取基金历史净值与日期，给echars提供数据
     *
     * @param fundCode 基金代码
     * @return FundEchartsResp
     */
    public FundEchartsResp fundEcharts(String fundCode) {
        //校验传入是否为空
        if (StringUtils.isBlank(fundCode)) {
            throw new BizException(FUND_CODE_NULL.getMessage(), FUND_CODE_NULL.getCode());
        }
        //从缓存中获取，没有则执行下一步
        if (FUND_ECHARTS_RESP_HASH_MAP.containsKey(fundCode)) {
            return FUND_ECHARTS_RESP_HASH_MAP.get(fundCode);
        }
        //校验是否一致表名
        String tableName = TABLE_NAME_PREFIX + fundCode;
        if (RequestDynamicTableNameHelper.getRequestData() != null && !RequestDynamicTableNameHelper.getRequestData().equals(tableName)) {
            throw new BizException(FUND_CODE_DIFF.getMessage(), FUND_CODE_DIFF.getCode());
        }
        //请求参数存取（表名）
        RequestDynamicTableNameHelper.setRequestData(tableName);
        BaseMapper<FundHistoryPO> baseMapper = fundHistoryPersist.getBaseMapper();
        //查询
        List<FundHistoryPO> poList = baseMapper.selectList(null);
        //倒序
        Collections.reverse(poList);
        FundEchartsResp resp = new FundEchartsResp();
        resp.setXData(new ArrayList<>(200));
        resp.setYData(new ArrayList<>(200));
        //向x，y轴添加数据
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        poList.forEach(po -> {
            resp.getXData().add(formatter.format(po.getFundDate()));
            resp.getYData().add(po.getFundNav());
        });
        resp.setDataCount(poList.size());
        //校验x轴数据是否与y轴数据一致
        if (resp.getXData().size() != resp.getYData().size()) {
            throw new BizException(DATA_SIZE_DIFF.getMessage(), DATA_SIZE_DIFF.getCode());
        }
        //加入本地缓存中
        FUND_ECHARTS_RESP_HASH_MAP.put(fundCode, resp);
        return resp;
    }

    /**
     * 用户查询自己持有基金
     *
     * @param token       令牌
     * @param pageRequest 分页条件
     * @return IPage<FundOwnResp>
     */
    public IPage<FundOwnResp> fundOwnSearch(String token, PageRequest pageRequest) {
        JwtUtils.checkToken(token);
        //根据token获取当前用户id
        FundUserPO userByToken = userQueryRepo.getUserByToken(token);
        //根据用户id 和 soldStatus==0为条件分页查询
        Page<FundUserBalancePO> page = new Page<>(pageRequest.getPageNumber(), pageRequest.getPageSize());
        LambdaQueryWrapper<FundUserBalancePO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FundUserBalancePO::getUserId, userByToken.getId())
                .eq(FundUserBalancePO::getSoldStatus, 0);
        IPage<FundUserBalancePO> fundUserBalancePoPage = fundUserBalancePersist.page(page, wrapper);
        //转换对象
        return fundUserBalancePoPage.convert(po -> {
            FundOwnResp resp = MAPPER.toOwnResp(po);
            FundPO fundPo = fundPersist.getById(po.getFundId());
            resp.setFundCode(fundPo.getFundCode());
            resp.setFundName(fundPo.getFundName());
            return resp;
        });
    }

    /**
     * 用户分页查询个人的交易记录
     *
     * @param token       令牌
     * @param pageRequest 分页
     * @return IPage<FundTransactionRecordResp>
     */
    public IPage<FundTransactionRecordResp> transactionRecordSearch(String token, PageRequest pageRequest) {
        JwtUtils.checkToken(token);
        //根据token获取当前用户id
        FundUserPO userByToken = userQueryRepo.getUserByToken(token);
        //组装查询条件
        Page<FundTransactionRecordPO> page = new Page<>(pageRequest.getPageNumber(), pageRequest.getPageSize());
        LambdaQueryWrapper<FundTransactionRecordPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(userByToken.getId() != null, FundTransactionRecordPO::getUserId, userByToken.getId())
                .orderByDesc(SuperModel::getUtcCreate);
        IPage<FundTransactionRecordPO> poIpage = fundTransactionRecordPersist.page(page, wrapper);
        return poIpage.convert(po -> {
            FundPO fundPo = fundPersist.getById(po.getFundId());
            FundTransactionRecordResp resp = MAPPER.toRecordResp(po);
            resp.setFundCode(fundPo.getFundCode());
            resp.setFundName(fundPo.getFundName());
            resp.setTradeType(po.getTradeType() == 0 ? "购入" : "赎回");
            LocalDateTime transactionTime = DateUtils.toZone(po.getUtcCreate(), ZoneOffset.UTC, DateUtils.EIGHTH_TIME_ZONE);
            resp.setTransactionTime(transactionTime);
            return resp;
        });

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

        /**
         * FundUserBalancePO -》 FundOwnResp
         *
         * @param po FundUserBalancePO
         * @return FundOwnResp
         */
        @Mapping(target = "fundName", ignore = true)
        @Mapping(target = "fundCode", ignore = true)
        FundOwnResp toOwnResp(FundUserBalancePO po);

        /**
         * FundTransactionRecordPO -》 FundTransactionRecordResp
         *
         * @param po FundTransactionRecordPO
         * @return FundTransactionRecordResp
         */
        @Mapping(target = "transactionTime", ignore = true)
        @Mapping(target = "fundName", ignore = true)
        @Mapping(target = "fundCode", ignore = true)
        @Mapping(target = "tradeType", ignore = true)
        FundTransactionRecordResp toRecordResp(FundTransactionRecordPO po);
    }
}
