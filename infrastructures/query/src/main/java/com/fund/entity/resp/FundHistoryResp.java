package com.fund.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 基金历史净值
 * <p>
 * Create at 2023/03/08 23:11
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
@Schema(title = "基金历史净值-响应结果", name = "FundHistoryResp")
@Setter
@Getter
@ToString
public class FundHistoryResp {

    @Schema(title = "主键id")
    private Integer id;

/*    @Schema(title = "关联基金id")
    private Integer fundId;*/

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

}
