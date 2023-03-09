package com.fund.utils;

/**
 * 请求参数动态表名传递辅助类
 * <p>
 * Create at 2023/03/08 21:47
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/08
 * @since 1.0.0
 */
public class RequestDynamicTableNameHelper {

    /**
     * 请求参数存取（表名）。请求参数自定义，官方Demo定义为ThreadLocal<Map<String, Object>>
     */
    private static final ThreadLocal<String> REQUEST_DATA = new ThreadLocal<>();

    private RequestDynamicTableNameHelper() {
    }

    /**
     * 设置请求参数
     *
     * @param requestData
     *            请求参数-表名
     */
    public static void setRequestData(String requestData) {
        REQUEST_DATA.set(requestData);
    }

    /**
     * 获取请求参数
     *
     * @return 请求参数-表名
     */
    public static String getRequestData() {
        return REQUEST_DATA.get();
    }

    /**
     * 移除获取请求参数（表名）
     */
    public static void remove() {
        REQUEST_DATA.remove();
    }

}
