package com.fund.entity.qry;

import com.fund.utils.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 基金历史净值-查询条件
 * <p>
 * Create at 2023/03/08 22:19
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
@Schema(title = "基金历史净值-查询条件", name = "FundHistoryQry")
@Setter
@Getter
@ToString
public class FundHistoryQry extends PageRequest {
    @NotBlank(message = "基金代码不能为空")
    @Schema(title = "基金代码")
    private String fundCode;
}
