package com.fund.infras.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 账号表
 * <p>
 * Create at 2023/02/24 23:12
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/24
 * @since 1.0.0
 */
@TableName(FundAccountPO.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class FundAccountPO extends SuperModel<FundAccountPO> {

    public static final String TABLE_NAME = "fund_account";
    /**
     * 关联的fund_user的主键id
     */
    private String userId;

    /**
     * 登录账号，每个账号用加号分隔
     */
    private String openCode;

    /**
     * 账号类别：0=普通账号，1=管理员账号
     */
    private Integer category;

}
