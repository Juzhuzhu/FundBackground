package com.fund.entity.qry;

import com.fund.utils.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 基金列表-查询条件
 * <p>
 * Create at 2023/03/08 16:27
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
@Schema(title = "基金列表-查询条件", name = "FundListQry")
@Setter
@Getter
@ToString
public class FundListQry extends PageRequest {
    @Schema(title = "基金代码")
    private String fundCode;

    @Schema(title = "基金名称")
    private String fundName;
}
