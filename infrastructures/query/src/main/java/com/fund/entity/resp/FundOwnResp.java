package com.fund.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Schema(title = "基金id")
    private Integer fundId;
    @Schema(title = "基金代码")
    private String fundCode;
    @Schema(title = "基金名称")
    private String fundName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Schema(title = "基金日期")
    private Date fundDate;
    @Schema(title = "买入金额")
    private BigDecimal purchaseAmount;
    @Schema(title = "当前余额")
    private BigDecimal balance;

}
