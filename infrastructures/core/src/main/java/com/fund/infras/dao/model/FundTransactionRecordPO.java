package com.fund.infras.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 基金交易记录表po
 * <p>
 * Create at 2023/04/05 23:13
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/05
 * @since 1.0.0
 */
@TableName(FundTransactionRecordPO.TABLE_NAME)
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class FundTransactionRecordPO extends SuperModel<FundTransactionRecordPO> {
    public static final String TABLE_NAME = "fund_transaction_record";
    /**
     * 用户id
     */
    private String userId;

    /**
     * 基金id
     */
    private Integer fundId;

    /**
     * 基金日期
     */
    private Date fundDate;

    /**
     * 交易类型，0=购入，1=赎回
     */
    private Integer tradeType;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;
}
