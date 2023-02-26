package com.fund;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 * <p>
 * Create at 2023/02/20 17:46
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/20
 * @since 1.0.0
 */
@SpringBootApplication
@EnableTransactionManagement
public class AdaptersApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdaptersApplication.class, args);
    }

}
