package com.fund.exception;


/**
 * 业务异常
 * <p>
 * Create at 2023/02/26 01:50
 *
 * @author 罗康明
 * @version 1.0.0, 2023/02/26
 * @since 1.0.0
 */

public class BizException extends AbstractBaseException {

    public BizException(String msg) {
        super(msg);
    }

    public BizException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BizException(String msg, Integer code) {
        super(msg, code);
    }

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    /*public BizException(String message, Integer code) {
        super(message);
        this.code = code;
    }*/

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    /*public BizException(CodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }*/
}
