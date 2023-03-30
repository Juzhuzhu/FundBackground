package com.fund.dto.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 接收前端传入修改的用户参数
 * <p>
 * Create at 2023/03/29 23:50
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/29
 * @since 1.0.0
 */
@Schema(title = "修改的用户参数",description = "修改后的参数")
@Getter
@Setter
@ToString
public class UserUpdateCmd {
    @Schema(title = "主键id")
    private String id;

    @Schema(title = "用户名")
    private String name;

    @Schema(title = "电话号码")
    private String phoneNumber;
}
