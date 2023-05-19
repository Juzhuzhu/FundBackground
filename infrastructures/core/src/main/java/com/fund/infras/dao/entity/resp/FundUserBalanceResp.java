package com.fund.infras.dao.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 管理员界面查看所有用户当前持有基金余额结果
 * <p>
 * Create at 2023/05/19 15:18
 *
 * @author 罗康明
 * @version 1.0.0, 2023/05/19
 * @since 1.0.0
 */
@Schema(title = "管理员界面查看所有用户当前持有基金余额结果", name = "FundOwnResultResp")
@Setter
@Getter
@ToString
public class FundUserBalanceResp implements Serializable {
    @Schema(title = "主键id")
    private String id;
    @Schema(title = "用户名")
    private String name;
    @Schema(title = "基金代码")
    private String fundCode;
    @Schema(title = "基金名称")
    private String fundName;
    @Schema(title = "基金日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fundDate;
    @Schema(title = "最后计算日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date latestDate;
    @Schema(title = "买入金额")
    private BigDecimal purchaseAmount;
    @Schema(title = "当前余额")
    private BigDecimal balance;
}
