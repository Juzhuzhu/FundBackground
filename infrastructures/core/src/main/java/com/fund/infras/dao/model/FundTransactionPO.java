package com.fund.infras.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 基金交易表po
 * <p>
 * Create at 2023/04/05 23:13
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/05
 * @since 1.0.0
 */
@TableName(FundTransactionPO.TABLE_NAME)
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class FundTransactionPO extends SuperModel<FundTransactionPO> {
    public static final String TABLE_NAME = "fund_transaction";
    /**
     * 用户id
     */
    private String userId;

    /**
     * 基金id
     */
    private String fundId;

    /**
     * 交易类型，0=购入，1=赎回
     */
    private Integer tradeType;

    /**
     * 交易金额
     */
    private BigDecimal transactionAmount;
}
