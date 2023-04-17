package com.fund.entity.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户持有基金实体类
 * <p>
 * Create at 2023/04/17 16:20
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/17
 * @since 1.0.0
 */
@Schema(title = "用户持有基金-响应结果", name = "FundOwnResp")
@Setter
@Getter
@ToString
public class FundOwnResp {
    @Schema(title = "主键id")
    private String id;

    /**
     * 基金id
     */
    @Schema(title = "主键id")
    private Integer fundId;

    /**
     * 基金代码
     */
    private String fundCode;

    /**
     * 基金代码
     */
    private String fundName;

    /**
     * 基金日期
     */
    @Schema(title = "基金日期")
    private Date fundDate;

    /**
     * 买入金额
     */
    @Schema(title = "买入金额")
    private BigDecimal purchaseAmount;

    /**
     * 当前余额
     */
    @Schema(title = "当前余额")
    private BigDecimal balance;

}
