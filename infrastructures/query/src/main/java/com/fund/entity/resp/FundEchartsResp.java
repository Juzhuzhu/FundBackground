package com.fund.entity.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 内容
 * <p>
 * Create at 2023/03/16 11:26
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/16
 * @since 1.0.0
 */
@Schema(title = "图表响应结果", name = "FundEchartsResp")
@Setter
@Getter
@ToString
public class FundEchartsResp implements Serializable {
    @Schema(title = "x轴数据集合，基金日期")
    private List<String> xData;

    @Schema(title = "y轴数据集合，单位净值")
    private List<BigDecimal> yData;

    @Schema(title = "数据总量")
    private Integer dataCount;
}
