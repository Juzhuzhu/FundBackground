package com.fund.dto.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 内容
 * <p>
 * Create at 2023/04/09 17:45
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/09
 * @since 1.0.0
 */
@Schema(title = "用户购买基金参数")
@Getter
@Setter
@ToString
public class FundPurchaseCmd {

    @Schema(title = "基金id")
    @NotNull(message = "基金id不能为空")
    private Integer fundId;

    @Schema(title = "基金日期")
    @NotNull(message = "基金日期不能为空")
    private Date fundDate;

    @Schema(title = "购入金额")
    @NotNull(message = "买入金额不能为空")
    private BigDecimal purchaseAmount;

}
