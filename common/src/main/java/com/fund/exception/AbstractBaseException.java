package com.fund.exception;

import com.fund.utils.ErrorInfo;
import lombok.Data;

/**
 * 自定义异常抽象类
 * <p>
 * Create at 2023/02/26 00:56
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/26
 * @since 1.0.0
 */
@Data
public abstract class AbstractBaseException extends RuntimeException{
    private ErrorInfo errorInfo;
    private Integer code;
    public AbstractBaseException(String msg){
        super(msg);
    }

    public AbstractBaseException(String msg, Integer code){
        super(msg);
        this.code = code;
    }

    public AbstractBaseException(String msg, Throwable cause){
        super(msg, cause);
    }

    public void setErrorInfo(ErrorInfo errorInfo){
        this.errorInfo = errorInfo;
    }
}
