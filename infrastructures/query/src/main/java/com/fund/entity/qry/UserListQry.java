package com.fund.entity.qry;

import com.fund.utils.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户列表查询条件
 * <p>
 * Create at 2023/03/27 22:23
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/27
 * @since 1.0.0
 */
@Schema(title = "用户列表-查询条件", name = "UserListQry")
@Setter
@Getter
@ToString
public class UserListQry extends PageRequest {
}
