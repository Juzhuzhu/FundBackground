package com.fund.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fund.exception.BizException;
import com.fund.infras.dao.mapper.UserMapper;
import com.fund.infras.dao.model.FundUserPO;
import com.fund.utils.JwtUtils;
import com.google.common.base.Preconditions;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

import static com.fund.enumeration.CodeEnum.USER_TOKEN_CONFLICT;
import static com.fund.enumeration.CodeEnum.USER_TOKEN_NONE;

/**
 * 拦截器校验token
 * <p>
 * Create at 2023/03/04 20:26
 *
 * @author 罗康明
 * @version 1.0.0, 2023/03/04
 * @since 1.0.0
 */
@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    private final UserMapper userMapper;

    public TokenInterceptor(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Headers", "*");
        // 如果是OPTIONS则结束请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return false;
        }
        //Token拦截
        log.info(">>>TokenInterceptor>>>>>>>在请求处理之前进行调用（Controller方法调用之前)");
        log.info("请求路径：====》" + request.getRequestURL());
        //获取请求头TOKEN
        String token = request.getHeader("token");
        log.info("token : [ {} ]", token);
        Preconditions.checkNotNull(token, "令牌为空，请先登录");
        //校验token
        LambdaQueryWrapper<FundUserPO> wrapper = Wrappers.lambdaQuery(FundUserPO.class);
        wrapper.eq(FundUserPO::getToken, token);
        List<FundUserPO> list = userMapper.selectList(wrapper);
        //多个用户相同令牌
        if (list.size() > 1) {
            throw new BizException(USER_TOKEN_CONFLICT.getMessage(), USER_TOKEN_CONFLICT.getCode());
        }
        //令牌不存在或失效
        if (list.isEmpty() || !JwtUtils.checkToken(token)) {
            throw new BizException(USER_TOKEN_NONE.getMessage(), USER_TOKEN_NONE.getCode());
        }


        // 只有返回true才会继续向下执行，返回false取消当前请求
        return true;
    }
}
