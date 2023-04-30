package com.fund.infras.dao.entity.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fund.enumeration.UserStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户列表响应类
 * <p>
 * Create at 2023/03/27 22:25
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/27
 * @since 1.0.0
 */
@Schema(title = "用户列表-响应结果", name = "UserResp")
@Setter
@Getter
@ToString
public class UserResp implements Serializable {
    @Schema(title = "用户id")
    private String id;

    @Schema(title = "用户状态")
    private UserStateEnum userStateEnum;

    @Schema(title = "用户名")
    private String name;

    @Schema(title = "头像图片地址")
    private String headImgUrl;

    @Schema(title = "电话号码")
    private String phoneNumber;

    @Schema(title = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime utcCreate;
}
