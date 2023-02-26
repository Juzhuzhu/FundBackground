package com.fund.configuration;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mp配置类
 * <p>
 * Create at 2023/02/25 14:50
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/25
 * @since 1.0.0
 */
@Slf4j
@Configuration
@MapperScan("com.fund.infras.dao.mapper")
public class MybatisPlusConfig {

}
