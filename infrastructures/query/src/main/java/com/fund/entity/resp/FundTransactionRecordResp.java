package com.fund.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 内容
 * <p>
 * Create at 2023/04/20 23:21
 *
 * @author 罗康明
 * @version 1.0.0, 2023/04/20
 * @since 1.0.0
 */
@Schema(title = "基金交易记录列表-响应结果", name = "FundResp")
@Setter
@Getter
@ToString
public class FundTransactionRecordResp {
    @Schema(title = "主键id")
    private String id;

    @Schema(title = "用户id")
    private String userId;

    @Schema(title = "基金id")
    private Integer fundId;

    @Schema(title = "基金代码")
    private String fundCode;

    @Schema(title = "基金名称")
    private String fundName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Schema(title = "基金日期")
    private Date fundDate;

    @Schema(title = "交易类型，0=购入，1=赎回")
    private String tradeType;

    @Schema(title = "交易金额")
    private BigDecimal transactionAmount;

    @Schema(title = "交易时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime transactionTime;
}
