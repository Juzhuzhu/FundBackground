package com.fund.adapters.api.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fund.entity.qry.UserListQry;
import com.fund.entity.resp.UserListResp;
import com.fund.enumeration.CustomerServiceRestConst;
import com.fund.utils.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户查询api
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
     * @param userListQry UserListQry
     * @return Result<IPage<UserListResp>>
     */
    Result<IPage<UserListResp>> getUserList(@RequestBody UserListQry userListQry);
}
