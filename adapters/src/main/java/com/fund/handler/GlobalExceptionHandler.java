package com.fund.handler;

import com.fund.exception.AbstractBaseException;
import com.fund.utils.ErrorInfo;
import com.fund.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

import static com.fund.enumeration.CodeEnum.SAVE_USER_ERROR;

/**
 * 统一异常拦截
 * <p>
 * Create at 2023/02/26 00:45
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/26
 * @since 1.0.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截非自定义异常
     * @param request HttpServletRequest
     * @param e Exception
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, Exception e) {
        /*ErrorInfo errorInfo = new ErrorInfo(LocalDateTime.now().toString(),
                e.getMessage(),
                request.getRequestURL().toString());*/
        log.warn("异常：" + e.getClass().getName() + "，异常信息：【" + e.getMessage() + "】");
        e.printStackTrace();
//        return Result.error(errorInfo);
        return Result.error(SAVE_USER_ERROR.getCode(), e.getMessage());
    }

    /**
     * 拦截自定义异常
     *
     * @param request HttpServletRequest
     * @param e       AbstractBaseException自定义异常抽象类
     * @return Result
     */
    @ExceptionHandler(AbstractBaseException.class)
    @ResponseBody
    public Result error(HttpServletRequest request, AbstractBaseException e) {
        ErrorInfo errorInfo = new ErrorInfo(LocalDateTime.now().toString(),
                e.getCode(),
                e.getMessage(),
                request.getRequestURL().toString());
        log.warn("自定义错误，错误码为：【" + e.getCode() + "】，错误信息：【" + e.getMessage() + "】");
        e.setErrorInfo(errorInfo);
        e.printStackTrace();
//        return Result.error(errorInfo);
        return Result.error(e.getCode(), e.getMessage());
    }

}
