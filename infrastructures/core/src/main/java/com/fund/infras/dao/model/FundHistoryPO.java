package com.fund.infras.dao.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(title = "主键id")
    private Integer id;

    @Schema(title = "关联基金id")
    private Integer fundId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Schema(title = "基金日期")
    private Date fundDate;

    @Schema(title = "单位净值")
    private BigDecimal fundNav;

    @Schema(title = "累计净值")
    private BigDecimal fundAccnav;

    @Schema(title = "日增长率")
    private String fundDgr;

    @Schema(title = "申购状态")
    private String subsribeState;

    @Schema(title = "赎回状态")
    private String redeemState;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime utcCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime utcUpdated;

    @TableLogic("0")
    @TableField(fill = FieldFill.UPDATE)
    private Long utcDeleted;
}
