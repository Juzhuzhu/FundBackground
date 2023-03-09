package com.fund.infras.dao.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "基金id-主键")
    private Integer id;

    @Schema(title = "基金代码")
    private String fundCode;

    @Schema(title = "基金名称")
    private String fundName;

    @Schema(title = "单位净值")
    private BigDecimal fundNav;

    @Schema(title = "累计净值")
    private BigDecimal fundAccnav;

    @Schema(title = "基金日期")
    private Date fundDate;

    @Schema(title = "日增长率")
    private String fundDgr;

    @Schema(title = "申购状态")
    private String subsribeState;

    @Schema(title = "赎回状态")
    private String redeemState;

    @Schema(title = "基金手续费")
    private String fundFee;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime utcCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime utcUpdated;

    @TableLogic("0")
    @TableField(fill = FieldFill.UPDATE)
    private Long utcDeleted;
}
