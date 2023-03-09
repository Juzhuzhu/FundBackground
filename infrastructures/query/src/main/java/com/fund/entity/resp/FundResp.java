package com.fund.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 基金列表-响应结果
 * <p>
 * Create at 2023/03/08 16:28
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
@Schema(title = "基金列表-响应结果", name = "FundResp")
@Setter
@Getter
@ToString
public class FundResp implements Serializable {
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

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
}
