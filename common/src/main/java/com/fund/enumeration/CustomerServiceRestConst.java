package com.fund.enumeration;

/**
 * rest常量
 * <p>
 * Create at 2023/02/20 21:22
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/20
 * @since 1.0.0
 */
public final class CustomerServiceRestConst {
    private CustomerServiceRestConst() {
        throw new IllegalStateException();
    }

    public static final String DEFAULT_HEAD_IMG_URL = "https://lkm191.oss-cn-guangzhou.aliyuncs.com/Screenshot_20200827_231650.jpg";
    public static final String ABOUT_CMD = "/指令相关";
    public static final String ABOUT_QUERY = "/查询相关";

    /**
     * 用户登录注册模块
     */
    public static final String USER_ROOT = "/user";
    public static final String API_TAG_USER = "用户系统";

    public static final String API_TAG_USER_CMD = API_TAG_USER + ABOUT_CMD;


}
