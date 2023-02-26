package com.fund.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.fund.utils.DateUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 拦截sql语句进行自动填充字段
 * <p>
 * Create at 2023/02/25 22:06
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/25
 * @since 1.0.0
 */
@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时自动填充字段
     *
     * @param metaObject MetaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //插入utc时间
        LocalDateTime utcNow = DateUtils.toZone(LocalDateTime.now(), DateUtils.EIGHTH_TIME_ZONE, ZoneOffset.UTC);
        this.setFieldValByName("utcCreate", utcNow, metaObject);
        this.setFieldValByName("utcUpdated", utcNow, metaObject);
    }

    /**
     * 更新时自动填充字段
     *
     * @param metaObject MetaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //插入utc时间
        LocalDateTime utcNow = DateUtils.toZone(LocalDateTime.now(), DateUtils.EIGHTH_TIME_ZONE, ZoneOffset.UTC);
        this.setFieldValByName("utcUpdated", utcNow, metaObject);
    }
}
