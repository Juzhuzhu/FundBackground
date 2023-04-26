package com.fund.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 时间转换工具类
 * <p>
 * Create at 2023/02/25 22:59
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/25
 * @since 1.0.0
 */
public class DateUtils {
    /**
     * 东八区ZoneOffset常量
     */
    public static final ZoneOffset EIGHTH_TIME_ZONE = ZoneOffset.ofHours(8);

    private DateUtils() {
    }

    /**
     * 转移时区时间
     *
     * @param dateTime 当前时间
     * @param fromZone 当前时区
     * @param toZone   目标时区
     * @return LocalDateTime目标时区对象
     */
    public static LocalDateTime toZone(LocalDateTime dateTime, ZoneId fromZone, ZoneId toZone) {
        ZonedDateTime zonedDateTime = dateTime.atZone(fromZone);
        ZonedDateTime result = zonedDateTime.withZoneSameInstant(toZone);
        return result.toLocalDateTime();
    }

    /**
     * Date转换时区
     *
     * @param date     当前时间
     * @param fromZone 当前时区
     * @param toZone   目标时区
     * @return Date
     */
    public static Date dateToZone(Date date, ZoneId fromZone, ZoneId toZone) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime targetZoneTime = toZone(localDateTime, fromZone, toZone);
        return Date.from(targetZoneTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
