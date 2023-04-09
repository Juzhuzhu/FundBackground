package com.fund.infras.dao.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 内容
 * <p>
 * Create at 2023/03/08 23:31
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode
public class FundHistoryPO {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 基金日期
     */
    private Date fundDate;

    /**
     * 单位净值
     */
    private BigDecimal fundNav;

    /**
     * 累计净值
     */
    private BigDecimal fundAccnav;

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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime utcCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime utcUpdated;

    @TableLogic("0")
    @TableField(fill = FieldFill.UPDATE)
    private Long utcDeleted;
}
