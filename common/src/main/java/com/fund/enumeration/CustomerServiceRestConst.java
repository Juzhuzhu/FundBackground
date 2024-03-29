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
    //17677487445的token
    public static final String EXAMPLE_TOKEN = "eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJScgv1c9ENDXYNUtJRykwBClgYWZqYGFoampuYGVsYGRkbmwFl8hJzU4Fyz1bOeN6zCoiUagH1Lv2ZQAAAAA.S6NYPvitMnOlPFwSfNkMYRm0cOnIyCYyD38jfCeOD8vxlcScCclRDO0K92do6UyzzI3CaMLKTAikxEksezPLFQ";

    public static final String DEFAULT_HEAD_IMG_URL = "https://lkm191.oss-cn-guangzhou.aliyuncs.com/Screenshot_20200827_231650.jpg";
    public static final String ABOUT_CMD = "/指令相关";
    public static final String ABOUT_QUERY = "/查询相关";

    /**
     * 基金历史净值表名前缀
     */
    public static final String TABLE_NAME_PREFIX = "fund_";


    /**
     * 用户cmd模块
     */
    public static final String USER_ROOT = "/user";
    public static final String API_TAG_USER = "用户系统";
    public static final String API_TAG_USER_CMD = API_TAG_USER + ABOUT_CMD;

    /**
     * 用户查询模块
     */
    public static final String API_TAG_USER_QUERY = API_TAG_USER + ABOUT_QUERY;
    /**
     * 基金查询模块
     */
    public static final String FUND_ROOT = "/fund";

    public static final String API_TAG_FUND = "基金系统";

    public static final String API_TAG_FUND_QUERY = API_TAG_FUND + ABOUT_QUERY;

    /**
     * 基金Cmd模块
     */
    public static final String API_TAG_FUND_CMD = API_TAG_FUND + ABOUT_CMD;


}
