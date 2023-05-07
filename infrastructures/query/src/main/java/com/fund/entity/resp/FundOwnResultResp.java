package com.fund.entity.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户持有基金页面结果
 * <p>
 * Create at 2023/05/07 20:27
 *
 * @author 罗康明
 * @version 1.0.0, 2023/05/07
 * @since 1.0.0
 */
@Schema(title = "用户持有基金页面结果", name = "FundOwnResultResp")
@Setter
@Getter
@ToString
public class FundOwnResultResp implements Serializable {
    @Schema(title = "持有列表")
    private IPage<FundOwnResp> fundOwnRespIpage;
    @Schema(title = "持有数")
    private Integer ownNum;
    @Schema(title = "基金持仓比例")
    private String ratio;
    @Schema(title = "利润")
    private BigDecimal profit;
}
