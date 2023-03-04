package com.fund.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 内容
 * <p>
 * Create at 2023/03/04 15:50
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/04
 * @since 1.0.0
 */
@Schema(title = "用户信息响应")
@Setter
@Getter
@ToString
public class UserInfoResp implements Serializable {
    @Schema(title = "用户id")
    private String id;

    @Schema(title = "用户名")
    private String name;

    @Schema(title = "用户头像")
    private String headImgUrl;

    @Schema(title = "令牌")
    private String token;
}