package com.fund.utils;

import java.security.MessageDigest;

/**
 * 密码加密工具类
 * <p>
 * Create at 2023/02/25 17:45
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/25
 * @since 1.0.0
 */
public class PasswordUtils {

    private PasswordUtils() {
    }

    public static String getMD5(String source) {
        StringBuffer sb = new StringBuffer(32);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(source.getBytes("utf-8"));
            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
            }
        } catch (Exception e) {
            return null;
        }
        return sb.toString();
    }
}
