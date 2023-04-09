package com.fund.infras.dao.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 基金实体类
 * <p>
 * Create at 2023/03/08 00:35
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
@TableName(FundPO.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode
public class FundPO {
    public static final String TABLE_NAME = "fund_list";

    /**
     * 基金id-主键
     */
    private Integer id;

    /**
     * 基金代码
     */
    private String fundCode;

    /**
     * 基金名称
     */
    private String fundName;

    /**
     * 单位净值
     */
    private BigDecimal fundNav;

    /**
     * 累计净值
     */
    private BigDecimal fundAccnav;

    /**
     * 基金日期
     */
    private Date fundDate;

    /**
     * 日增长率
     */
    private String fundDgr;

    /**
     * 申购状态
     */
    private String subsribeState;

    /**
     * 赎回状态
     */
    private String redeemState;

    /**
     * 基金手续费
     */
    private String fundFee;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime utcCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime utcUpdated;

    @TableLogic("0")
    @TableField(fill = FieldFill.UPDATE)
    private Long utcDeleted;
}
