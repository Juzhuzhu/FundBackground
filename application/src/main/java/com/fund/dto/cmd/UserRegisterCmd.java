package com.fund.dto.cmd;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 用户注册提交参数
 * <p>
 * Create at 2023/02/25 00:18
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/25
 * @since 1.0.0
 */
@Schema(title = "用户注册参数", description = "用户参数")
@Getter
@Setter
@ToString
public class UserRegisterCmd {
    @NotBlank(message = "传入的用户名不允许为空")
    @Schema(title = "用户名")
    private String name;

    @NotBlank(message = "传入的手机号码不许为空")
    @Schema(title = "手机号码")
    private String phoneNumber;

    @NotBlank(message = "传入的密码不允许为空")
    @Schema(title = "密码")
    private String password;
}
