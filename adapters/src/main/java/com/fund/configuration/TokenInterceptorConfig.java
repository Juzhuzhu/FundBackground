package com.fund.configuration;

import com.fund.handler.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.fund.adapters.api.cmd.UserCmdRestApi.USER_LOGIN;
import static com.fund.adapters.api.cmd.UserCmdRestApi.USER_REGISTER;
import static com.fund.adapters.api.query.FundQueryRestApi.*;

/**
 * token拦截器配置类
 * <p>
 * Create at 2023/03/04 20:51
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/04
 * @since 1.0.0
 */
@Configuration
public class TokenInterceptorConfig implements WebMvcConfigurer {
    private final TokenInterceptor tokenInterceptor;

    public TokenInterceptorConfig(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    private static final String[] SWAGGER_RESOURCE = {"/swagger-resources/**", "/webjars/**", "/v3/**", "/swagger-ui.html/**", "/swagger-ui/**", "/mgr", "/mgr/**", "/h5", "/h5/**"};

    /**
     * 允许跨域
     *
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").
                allowCredentials(true).
                allowedHeaders("*").
                allowedOriginPatterns("*").
                allowedMethods("*");
    }

    /**
     * 配置token拦截器
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        //添加拦截器
        registry.addInterceptor(tokenInterceptor)
                //拦截所有请求
                .addPathPatterns("/**")
                //对应的不拦截的请求
                .excludePathPatterns(SWAGGER_RESOURCE)
                .excludePathPatterns(
                        USER_LOGIN,
                        USER_REGISTER,
                        FUND_LIST,
                        FUND_HISTORY,
                        FUND_ECHARTS
                );
    }
}
