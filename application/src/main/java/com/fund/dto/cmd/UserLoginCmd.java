package com.fund.dto.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登录
 * <p>
 * Create at 2023/03/04 15:33
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/04
 * @since 1.0.0
 */
@Schema(title = "用户登录参数",description = "用户参数")
@Getter
@Setter
@ToString
public class UserLoginCmd {
    @Schema(title = "手机号")
    @NotBlank(message = "账号不能为空")
    private String phoneNumber;

    @Schema(title = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
