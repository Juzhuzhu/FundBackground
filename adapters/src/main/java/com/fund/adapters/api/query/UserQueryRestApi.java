package com.fund.adapters.api.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fund.infras.dao.entity.qry.UserQry;
import com.fund.infras.dao.entity.resp.UserResp;
import com.fund.enumeration.CustomerServiceRestConst;
import com.fund.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 管理员-用户管理模块query
 * <p>
 * Create at 2023/03/27 22:18
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/27
 * @since 1.0.0
 */
@Tag(name = CustomerServiceRestConst.API_TAG_USER_QUERY)
public interface UserQueryRestApi {
    String USER_LIST = CustomerServiceRestConst.USER_ROOT + "/list";

    /**
     * 分页查询用户列表
     *
     * @param userQry UserListQry
     * @return Result<IPage < UserListResp>>
     */
    @Parameter(name = "token", in = ParameterIn.HEADER, required = true, description = "认证token", example = CustomerServiceRestConst.EXAMPLE_TOKEN)
    @Operation(summary = "分页查询用户列表")
    @ResponseBody
    @PostMapping(USER_LIST)
    Result<IPage<UserResp>> getUserList(@RequestBody UserQry userQry);
}
