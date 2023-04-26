package com.fund.infras.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户持有基金po
 * <p>
 * Create at 2023/04/09 18:25
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/09
 * @since 1.0.0
 */
@TableName(FundUserBalancePO.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class FundUserBalancePO extends SuperModel<FundUserBalancePO>{

    public static final String TABLE_NAME = "fund_user_balance";

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
     * 最后计算收益的日期
     */
    private Date latestDate;

    /**
     * 买入金额
     */
    private BigDecimal purchaseAmount;

    /**
     * 当前余额
     */
    private BigDecimal balance;

    /**
     * 售出状态，0=未售出，1=已售出
     */
    private Integer soldStatus;
}
